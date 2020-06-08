package com.ctrlvideo.ivsdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Author by Jaiky, Date on 2020/4/8.
 */
public class IVView extends RelativeLayout {

    protected String TAG = "IVSDKView";

    protected WebView webView;
    private MyChromeClient chromeClient = null;

    //事件监听器
    private IVViewListener listener = null;

    //项目地址
    private String mPid;
    //播放配置的url
    private String config_url = "";

    //当前视图状态
    private String nowViewStatus = ViewState.STATE_LOADING;

    private GestureView gestureView;

    //是否纯净模式
    private boolean isPureMode = false;

    public IVView(Context context) {
        super(context);
        initView(context);
    }

    public IVView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public IVView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        if (isInEditMode()){
            return;
        }
        RelativeLayout inflate = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.view_webview, this, true);
        webView = findViewById(R.id.webvContainer);

        //初始化手势识别视图
        gestureView = new GestureView(context);
        inflate.addView(gestureView);
        gestureView.setResultCallback(new GestureView.ResultCallback() {
            @Override
            public void onResultCall(String result) {
                Log.d(TAG, "gestureResult " + result);
                //如果识别成功了key，+1
                if (recognKeys.contains(result)) {
                    recognSuccNum++;
                }

                //连续识别成功5次，算识别成功
                if (recognSuccNum >= 5){
                    sendGestureResult(result);
                }
            }
        });
    };


    public void initIVView(String pid, @Nullable String config_url, @NonNull IVViewListener ivViewListener, @NonNull Activity mContext) {
        this.mPid = pid;
        this.config_url = config_url == null ? "" : config_url;
        this.listener = ivViewListener;
        initWebView();
        gestureView.initView(mContext);
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        //webview 通讯参考 https://docs.qq.com/doc/DTUpZcXlobWZVQm1G
        webView.addJavascriptInterface(this, "IvSDKAndroid");

        chromeClient = new MyChromeClient();
        webView.setWebChromeClient(chromeClient);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);

        webView.setBackgroundColor(0);
        webView.getBackground().setAlpha(0);

        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);

//        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.loadUrl("http://192.168.3.156:3102/");
//        webView.loadUrl("https://ivetest.ctrlvideo.com/jssdk/native.html");
        webView.loadUrl("https://ivetest.ctrlvideo.com/jssdk/native020.html");

//        webView.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
////                Log.d(TAG, "onTouchEvent ");
//                switch (event.getAction()) {
//
//                    case MotionEvent.ACTION_DOWN:
//                        //do something......
//                        Log.d(TAG, "ACTION_DOWN ");
//                        break;
//
//                    case MotionEvent.ACTION_MOVE:
//
//                        //do something......
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        //do something......
//
//                        break;
//                }
//                return false;
//            }
//        });

    }


    /**
     * 当activity恢复时
     */
    public void onResume() {

    }

    /**
     * 当activity暂停时
     */
    public void onPause() {

    }


    public boolean isPureMode() {
        return isPureMode;
    }

    /**
     * 打开纯净模式
     * @param isOpen 是否打开
     */
    public void setPureMode(boolean isOpen) {
        isPureMode = isOpen;
        if (isOpen) {
            this.setVisibility(View.GONE);
            //事件内打断事件
            interruptEvent();
        }
        else
            this.setVisibility(View.VISIBLE);
    }


    private long nowEventPrepareTime = -1;

    /**
     * 执行action行为
     */
    public void performAction(String action) {
        //打断事件行为，并重新进入起始点
        if (action.equals(IVEvent.EventAction.INTERRUPT_EVENT)){
            interruptEvent();
            if (nowEventPrepareTime >= 0) {
                //向前1秒
                long seekTime = nowEventPrepareTime - 1000;
                if (seekTime < 0)
                    seekTime = 0;
                listener.seekToTime(seekTime);
                nowEventPrepareTime = -1;
            }
        }
    }


    private void interruptEvent() {
        if (nowEventPrepareTime >= 0) {
            if (gestureView.isCameraStart())
                gestureView.close();
            stopSpeechRecord();
        }
    }


    private void stopSpeechRecord() {
        webView.evaluateJavascript("javascript:stopSpeechRecord()", null);
    }

    //SDK初始化
    private void evalJSNativeSDKInit(String pid, String channel, String config_url) {
        Log.d(TAG, "evalJS NativeSDKInit ");
        webView.evaluateJavascript("javascript:nativeSDKInit('" + pid + "', '" + channel +"', '" + config_url +"')", null);
    }


    //调用JS开始暂停播放 status "onplay" 播放，"onpause" 暂停
    public void onPlayerStateChanged(String status) {
        if (nowViewStatus.equals(ViewState.STATE_READIED)){
            Log.d(TAG, "evalJS OnPlayerStateChanged " + status);
            webView.evaluateJavascript("javascript:onSDKPlayerStateChanged('" + status + "')", null);
        }
    }


    //手势识别结果上报
    public void sendGestureResult(String key) {
        Log.d(TAG, "evalJS gestureResultSend " + key);
        recognKeys = "";
        recognSuccNum = 0;
        gestureView.close();
        webView.evaluateJavascript("javascript:gestureResultSend('success', '" + key + "')", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String str) {

            }
        });
    }


    //需要识别的key和识别成功次数
    private String recognKeys = "";
    private int recognSuccNum = 0;

    /**
     * 手势状态改变
     * @param state 状态
     */
    @JavascriptInterface
    public void onGestureChanged(String state, String time, String keys) {
        Log.i(TAG, "onGestureChanged " + state + "  time " + time + "  keys " + keys);
        long mmTime = IVUtils.getMMTime(time);

        this.post(new Runnable() {
            @Override
            public void run() {
                listener.onEventStateChanged(IVEvent.EVENT_GESTURE, state, mmTime);

                if (state.equals("prepare")) {
                    nowEventPrepareTime = mmTime;
                    if (!gestureView.isCameraStart())
                        gestureView.start();
                }
                else if (state.equals("start")) {
                    recognKeys = keys;
                    recognSuccNum = 0;
                    gestureView.setVisible(true);
                }
                else {
                    nowEventPrepareTime = -1;
                    recognKeys = "";
                    if (gestureView.isCameraStart())
                        gestureView.close();
                }
            }
        });
    }

    /**
     * 录音状态改变
     * @param state 状态
     */
    @JavascriptInterface
    public void onRecordChanged(String state, String time) {
        Log.d(TAG, "onRecordChanged " + state + "  time " + time);
        long mmTime = IVUtils.getMMTime(time);


        this.post(new Runnable() {
            @Override
            public void run() {
                listener.onEventStateChanged(IVEvent.EVENT_SPEECHRECOGN, state, mmTime);

                if (state.equals("prepare")) {
                    nowEventPrepareTime = mmTime;
                }
                else if (state.equals("start")) {
                }
                else {
                    nowEventPrepareTime = -1;
                }
            }
        });
    }


    /**
     * web网页状态改变
     * @param state 状态，"onReadied" 当ui逻辑初始化完成
     */
    @JavascriptInterface
    public void webPageStateChanged(String state) {
        Log.d(TAG, "webPageStateChanged " + state);
        if (state.equals("onReadied")){
            nowViewStatus = ViewState.STATE_READIED;
            this.post(new Runnable() {
                @Override
                public void run() {
                    listener.onIVViewStateChanged(nowViewStatus);
                }
            });
        }
    }

    /**
     * 获取播放时间
     * @return 当前播放时间，单位：秒 [如有小数带小数]
     */
    @JavascriptInterface
    public String getCurrentTime() {
        //纯净模式返回
        if (isPureMode)
            return 0 + "";

        float time = ((float)listener.getPlayerCurrentTime() / 1000);
//        Log.d(TAG, "getCurrentTime " + time);
        return time + "";
    }

    /**
     * 设置播放时间
     * @param time 传过来的时间，单位：秒 [带小数的字符串]
     */
    @JavascriptInterface
    public void setCurrentTime(String time) {
        Log.d(TAG, "setCurrentTime " + time);

        long mmTime = IVUtils.getMMTime(time);
        this.post(new Runnable() {
            @Override
            public void run() {
                listener.seekToTime(mmTime);
            }
        });
    }

    /**
     * 执行播放/暂停
     * @param state 是否播放，如播放，传“play”，暂停传“pause”
     */
    @JavascriptInterface
    public void playPauseVideo(final String state) {
        Log.d(TAG, "playPauseVideo " + state);

        this.post(new Runnable() {
            @Override
            public void run() {
                if (state.equals("play")) {
                    listener.ctrlPlayer("play");
                }
                else if (state.equals("pause")) {
                    listener.ctrlPlayer("pause");
                }
            }
        });
    }


    /**
     * 当view点击空白处时 [如点击webview中控件将阻止向上冒泡]
     * @param info 点击区域信息
     */
    @JavascriptInterface
    public void ivViewOnClick(final String info) {
        Log.d(TAG, "ivViewOnClick " + info);

        this.post(new Runnable() {
            @Override
            public void run() {
                listener.onIVViewClick(info);
            }
        });
    }


    //webview chromeclient
    public class MyChromeClient extends WebChromeClient {


        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
        }

        @Override
        public void onHideCustomView() {
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
        }

        @Override
        public void onPermissionRequest(PermissionRequest request) {
            Log.d(TAG, "onPermissionRequest ");
            request.grant(request.getResources());
        }
    }



    class MyWebViewClient extends WebViewClient {

        // 重写父类方法，让新打开的网页在当前的WebView中显示
        //当返回true时，你点任何链接都是失效的，需要你自己跳转。return false时webview会自己跳转。
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //页面加载完成后，初始化sdk
            evalJSNativeSDKInit(mPid, "xiaoqie", config_url);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }


}
