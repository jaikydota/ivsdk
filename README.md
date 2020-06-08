# IVSDK
ctrlvideo.com Interaction Video SDK for Android.<br>


### Using
Add to your layout xml:
```
<com.ctrlvideo.ivsdk.IVView
    android:id="@+id/ivViewContainer"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
</com.ctrlvideo.ivsdk.IVView>
```

Binding to your code:
```
IVView ivView = findViewById(R.id.ivViewContainer);
ivView.initIVView(pid, new IVListener());
```

Implement this Interface:
```
private class IVListener implements IVViewListener {

    void onIVViewStateChanged(String state);

    long getPlayerCurrentTime();

    void seekToTime(long time);

    void setPlayOrPause(String state);

    void onIVViewClick(String info);   
    
    void onEventStateChanged(String eType, String state);
 
}
```

### *Enjoy it!*
