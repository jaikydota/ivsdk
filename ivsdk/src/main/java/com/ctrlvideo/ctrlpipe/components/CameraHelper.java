package com.ctrlvideo.ctrlpipe.components;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.util.Size;
import javax.annotation.Nullable;

public abstract class CameraHelper {
  protected static final String TAG = "CameraHelper";
  
  protected OnCameraStartedListener onCameraStartedListener;
  
  protected CameraFacing cameraFacing;
  
  public abstract void startCamera(Activity paramActivity, CameraFacing paramCameraFacing, @Nullable SurfaceTexture paramSurfaceTexture);
  
  public abstract Size computeDisplaySizeFromViewSize(Size paramSize);
  
  public enum CameraFacing {
    FRONT, BACK;
  }
  
  public void setOnCameraStartedListener(@Nullable OnCameraStartedListener listener) {
    this.onCameraStartedListener = listener;
  }
  
  public static interface OnCameraStartedListener {
    void onCameraStarted(@Nullable SurfaceTexture param1SurfaceTexture);
  }
}
