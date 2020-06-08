// Copyright 2019 The MediaPipe Authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.ctrlvideo.ivsdk;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ctrlvideo.ctrlpipe.components.ExternalTextureConverter;
import com.ctrlvideo.ctrlpipe.components.FrameProcessor;
import com.ctrlvideo.ctrlpipe.components.PermissionHelper;
import com.ctrlvideo.ctrlpipe.formats.proto.LandmarkProto;
import com.ctrlvideo.ctrlpipe.formats.proto.LandmarkProto.NormalizedLandmark;
import com.ctrlvideo.ctrlpipe.formats.proto.LandmarkProto.NormalizedLandmarkList;
import com.google.mediapipe.framework.AndroidAssetUtil;
import com.google.mediapipe.framework.PacketGetter;
import com.ctrlvideo.ctrlpipe.glutil.EglManager;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;


/**
 * Main activity of MediaPipe example apps.
 */
public class GestureView extends RelativeLayout {
    private static final String TAG = "IVGestureView";

//    private static final String BINARY_GRAPH_NAME = "ctrlvideo_htmg.binarypb";
    private static final String BINARY_GRAPH_NAME = "handtrackinggpu.binarypb";
    private static final String INPUT_VIDEO_STREAM_NAME = "input_video";
    private static final String OUTPUT_VIDEO_STREAM_NAME = "output_video";
    private static final String OUTPUT_LANDMARKS_STREAM_NAME = "hand_landmarks";
    private static final CameraHelper.CameraFacing CAMERA_FACING = CameraHelper.CameraFacing.FRONT;

    // Flips the camera-preview frames vertically before sending them into FrameProcessor to be
    // processed in a MediaPipe graph, and flips the processed frames back when they are displayed.
    // This is needed because OpenGL represents images assuming the image origin is at the bottom-left
    // corner, whereas MediaPipe in general assumes the image origin is at top-left.
    private static final boolean FLIP_FRAMES_VERTICALLY = true;

    static {
        // Load all native libraries needed by the app.
        System.loadLibrary("ctrlpipe");
        System.loadLibrary("opencv_java3");
    }

    // {@link SurfaceTexture} where the camera-preview frames can be accessed.
    private SurfaceTexture previewFrameTexture;
    // {@link SurfaceView} that displays the camera-preview frames processed by a MediaPipe graph.
    private TextureView previewDisplayView;

    // Creates and manages an {@link EGLContext}.
    private EglManager eglManager;
    // Sends camera-preview frames into a MediaPipe graph for processing, and displays the processed
    // frames onto a {@link Surface}.
    private FrameProcessor processor;
    // Converts the GL_TEXTURE_EXTERNAL_OES texture from Android camera into a regular texture to be
    // consumed by {@link FrameProcessor} and the underlying MediaPipe graph.
    private ExternalTextureConverter converter;

    // Handles camera access via the {@link CameraX} Jetpack support library.
    private CameraXPreviewHelper cameraHelper;

    private Activity mContext;

    //视图尺寸
    private Size viewSize;

    public GestureView(Context context) {
        super(context);
    }

    public GestureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void initView(Activity context) {
        this.mContext = context;
        if (isInEditMode()) {
            return;
        }
        LayoutInflater.from(context).inflate(R.layout.view_camera, this, true);

        previewDisplayView = new TextureView(context);
        setupPreviewDisplayView();

        // Initialize asset manager so that MediaPipe native libraries can access the app assets, e.g.,
        // binary graphs.
        AndroidAssetUtil.initializeNativeAssetManager(context);

        eglManager = new EglManager(null);
        processor = new FrameProcessor(context, eglManager.getNativeContext(), BINARY_GRAPH_NAME, INPUT_VIDEO_STREAM_NAME, OUTPUT_VIDEO_STREAM_NAME);
        processor.getVideoSurfaceOutput().setFlipY(FLIP_FRAMES_VERTICALLY);

        processor.addPacketCallback(
                OUTPUT_LANDMARKS_STREAM_NAME,
                (packet) -> {
                    byte[] landmarksRaw = PacketGetter.getProtoBytes(packet);
                    try {
                        NormalizedLandmarkList landmarks = NormalizedLandmarkList.parseFrom(landmarksRaw);
                        if (landmarks == null) {
                            Log.d(TAG, "[TS:" + packet.getTimestamp() + "] No hand landmarks.");
                            return;
                        }

                        String result = handGestureRecognition(landmarks.getLandmarkList()).name();

//                        Log.d(TAG, "RESULT:----------  " + result);

                        //非unknown下发
                        if (mResultCallback != null && !result.equals("UNKNOWN")) {
                            this.post(() -> mResultCallback.onResultCall(result));
//                            Log.d(TAG, getLandmarksDebugString(landmarks));
                        }
                    } catch (InvalidProtocolBufferException e) {
                        Log.e(TAG, "Couldn't Exception received - " + e);
                        return;
                    }
                });
    }


    public boolean isCameraStart() {
        return cameraHelper != null;
    };


    protected void start() {
        Log.i(TAG, "converter start()" );
        converter = new ExternalTextureConverter(eglManager.getContext());
        converter.setFlipY(FLIP_FRAMES_VERTICALLY);
        converter.setConsumer(processor);
        if (PermissionHelper.cameraPermissionsGranted(mContext) && viewSize != null) {
            startCamera();
        }
    }

    protected void close() {
        Log.i(TAG, "converter close()" );
        previewDisplayView.setVisibility(View.GONE);
        converter.close();
        stopCamera();
    }

    public void setVisible(boolean visible) {
        if (visible)
            previewDisplayView.setVisibility(View.VISIBLE);
        else
            previewDisplayView.setVisibility(View.GONE);
    }


    private void setupPreviewDisplayView() {
        ViewGroup viewGroup = findViewById(R.id.preview_display_layout);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewGroup.addView(previewDisplayView, 0,  params);

        previewDisplayView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {

            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Log.w(TAG, "onSurfaceTexture Available - " + width + "  " + height);

                previewDisplayView.setVisibility(View.INVISIBLE);

                viewSize = new Size(width, height);
                Surface mSurface = new Surface(surface);
                processor.getVideoSurfaceOutput().setSurface(mSurface);
            }

            //surface 大小发生变化
            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                Log.w(TAG, "onSurfaceTexture SizeChanged - " + width + "  " + height);
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                Log.w(TAG, "onSurfaceTexture Destroyed - ");
                processor.getVideoSurfaceOutput().setSurface(null);
                stopCamera();
                return false;
            }

            //SurfaceTexture 更新
            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//                Log.w(TAG, "onSurfaceTexture Updated - ");
            }
        });
    }

    private void startCamera() {
        cameraHelper = new CameraXPreviewHelper();
        cameraHelper.setOnCameraStartedListener(
                (surfaceTexture, frameSize) -> {
                    Log.w(TAG, "CameraStartedListener Callback");
                    previewFrameTexture = surfaceTexture;

//                    Size displaySize = viewSize;
                    Size displaySize = new Size(1280, 1280);
//                    Size displaySize = cameraHelper.computeDisplaySizeFromViewSize(viewSize);
                    Log.w(TAG, "display size "+ displaySize);
                    converter.setSurfaceTextureAndAttachToGLContext(previewFrameTexture, displaySize.getWidth(), displaySize.getHeight());
                    setRotation(frameSize);
                });
        cameraHelper.startCamera(mContext, CAMERA_FACING, /*surfaceTexture=*/ null);
    }

    private void stopCamera() {
        if (cameraHelper != null) {
            cameraHelper.stopCamera();
            cameraHelper = null;
        }
    }

    void setRotation(Size frameSize) {
        DisplayMetrics metrics= new DisplayMetrics();
        previewDisplayView.getDisplay().getRealMetrics(metrics);
        int previewWidth = metrics.widthPixels;
        int previewHeight = metrics.heightPixels;
        float centerX = (float)previewDisplayView.getWidth() / 2;
        float centerY = (float)previewDisplayView.getHeight() / 2;

        int rotation = 0;
        if (previewDisplayView.getDisplay().getRotation() == Surface.ROTATION_90)
            rotation = 90;
        else if (previewDisplayView.getDisplay().getRotation() == Surface.ROTATION_180)
            rotation = 180;
        else if (previewDisplayView.getDisplay().getRotation() == Surface.ROTATION_270)
            rotation = 270;

        Matrix matrix = new Matrix();
        matrix.postRotate(-rotation, centerX, centerY);
        // Scale matrix
//        matrix.postScale(
//                (float) previewWidth / frameSize.getHeight(),
//                (float) previewHeight / frameSize.getWidth(),
//                centerX,
//                centerY
//        );

        Log.w(TAG, "rotation " + rotation);

        // Assign transformation to view
        previewDisplayView.setTransform(matrix);
    }


    private static String getLandmarksDebugString(NormalizedLandmarkList landmarks) {
        int landmarkIndex = 0;
        String landmarksString = "";
        for (NormalizedLandmark landmark : landmarks.getLandmarkList()) {
            landmarksString +=
                    "\t\tLandmark["
                            + landmarkIndex
                            + "]: ("
                            + landmark.getX()
                            + ", "
                            + landmark.getY()
                            + ", "
                            + landmark.getZ()
                            + ")\n";
            ++landmarkIndex;
        }
        return landmarksString;
    }


    enum Gestures {
        FIVE,
        FOUR,
        TREE,
        TWO,
        ONE,
        YEAH,
        ROCK,
        SPIDERMAN,
        FIST,
        OK,
        UNKNOWN
    }


    static boolean fingerIsOpen(double pseudoFixKeyPoint, double point1, double point2) {
        return point1 < pseudoFixKeyPoint && point2 < pseudoFixKeyPoint;
    }

    static boolean thumbIsOpen(List<LandmarkProto.NormalizedLandmark> landmarks) {
        return fingerIsOpen(landmarks.get(2).getX(), landmarks.get(3).getX(), landmarks.get(4).getX());
    }

    static boolean firstFingerIsOpen(List<LandmarkProto.NormalizedLandmark> landmarks) {
        return fingerIsOpen(landmarks.get(6).getY(), landmarks.get(7).getY(), landmarks.get(8).getY());
    }

    static boolean secondFingerIsOpen(List<LandmarkProto.NormalizedLandmark> landmarks) {
        return fingerIsOpen(landmarks.get(10).getY(), landmarks.get(11).getY(), landmarks.get(12).getY());
    }

    static boolean thirdFingerIsOpen(List<LandmarkProto.NormalizedLandmark> landmarks) {
        return fingerIsOpen(landmarks.get(14).getY(), landmarks.get(15).getY(), landmarks.get(16).getY());
    }

    static boolean fourthFingerIsOpen(List<LandmarkProto.NormalizedLandmark> landmarks) {
        return fingerIsOpen(landmarks.get(18).getY(), landmarks.get(19).getY(), landmarks.get(20).getY());
    }

    static double getEuclideanDistanceAB(double aX, double aY, double bX, double bY) {
        return Math.sqrt(Math.pow(aX - bX, 2) + Math.pow(aY - bY, 2));
    }

    static boolean isThumbNearFirstFinger(NormalizedLandmark point1, NormalizedLandmark point2) {
        return getEuclideanDistanceAB(point1.getX(), point1.getY(), point2.getX(), point2.getY()) < 0.1;
    }

    static Gestures handGestureRecognition(List<LandmarkProto.NormalizedLandmark> landmarks) {
        if (landmarks.get(0).getX() == 0 && landmarks.get(0).getY() == 0) {
            return Gestures.UNKNOWN;
        }

        // finger states
        boolean thumbIsOpen = thumbIsOpen(landmarks);
        boolean firstFingerIsOpen = firstFingerIsOpen(landmarks);
        boolean secondFingerIsOpen = secondFingerIsOpen(landmarks);
        boolean thirdFingerIsOpen = thirdFingerIsOpen(landmarks);
        boolean fourthFingerIsOpen = fourthFingerIsOpen(landmarks);
        if (thumbIsOpen &&
                firstFingerIsOpen &&
                secondFingerIsOpen &&
                thirdFingerIsOpen &&
                fourthFingerIsOpen)
            return Gestures.FIVE;
        else if (!thumbIsOpen &&
                firstFingerIsOpen &&
                secondFingerIsOpen &&
                thirdFingerIsOpen &&
                fourthFingerIsOpen)
            return Gestures.FOUR;
        else if (thumbIsOpen &&
                firstFingerIsOpen &&
                secondFingerIsOpen &&
                !thirdFingerIsOpen &&
                !fourthFingerIsOpen)
            return Gestures.TREE;
        else if (thumbIsOpen &&
                firstFingerIsOpen &&
                !secondFingerIsOpen &&
                !thirdFingerIsOpen &&
                !fourthFingerIsOpen)
            return Gestures.TWO;
        else if (!thumbIsOpen &&
                firstFingerIsOpen &&
                !secondFingerIsOpen &&
                !thirdFingerIsOpen &&
                !fourthFingerIsOpen)
            return Gestures.ONE;
        else if (!thumbIsOpen &&
                firstFingerIsOpen &&
                secondFingerIsOpen &&
                !thirdFingerIsOpen &&
                !fourthFingerIsOpen)
            return Gestures.YEAH;
        else if (!thumbIsOpen &&
                firstFingerIsOpen &&
                !secondFingerIsOpen &&
                !thirdFingerIsOpen &&
                fourthFingerIsOpen)
            return Gestures.ROCK;
        else if (thumbIsOpen &&
                firstFingerIsOpen &&
                !secondFingerIsOpen &&
                !thirdFingerIsOpen &&
                fourthFingerIsOpen)
            return Gestures.SPIDERMAN;
        else if (!thumbIsOpen &&
                !firstFingerIsOpen &&
                !secondFingerIsOpen &&
                !thirdFingerIsOpen &&
                !fourthFingerIsOpen)
            return Gestures.FIST;
        else if (!firstFingerIsOpen &&
                secondFingerIsOpen &&
                thirdFingerIsOpen &&
                fourthFingerIsOpen &&
                isThumbNearFirstFinger(landmarks.get(4), landmarks.get(8)))
            return Gestures.OK;
        else
            return Gestures.UNKNOWN;
    }



    private ResultCallback mResultCallback;

    public void setResultCallback(ResultCallback resultCallback) {
        this.mResultCallback = resultCallback;
    }

    public interface ResultCallback {
        void onResultCall(String result);
    }

}
