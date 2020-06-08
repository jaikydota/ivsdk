package com.ctrlvideo.ctrlpipe.components;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.util.Size;
import androidx.camera.core.CameraX;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.camera.core.UseCase;
import androidx.lifecycle.LifecycleOwner;

public class CameraXPreviewHelper extends CameraHelper {
  private static final String TAG = "CameraXPreviewHelper";
  
  private Preview preview;
  
  private Size frameSize;
  
  private int frameRotation;
  
  public void startCamera(Activity context, CameraHelper.CameraFacing cameraFacing, SurfaceTexture surfaceTexture) {
    CameraX.LensFacing cameraLensFacing = (cameraFacing == CameraHelper.CameraFacing.FRONT) ? CameraX.LensFacing.FRONT : CameraX.LensFacing.BACK;
    PreviewConfig previewConfig = (new PreviewConfig.Builder()).setLensFacing(cameraLensFacing).build();
    this.preview = new Preview(previewConfig);
    this.preview.setOnPreviewOutputUpdateListener(previewOutput -> {
          if (!previewOutput.getTextureSize().equals(this.frameSize)) {
            this.frameSize = previewOutput.getTextureSize();
            this.frameRotation = previewOutput.getRotationDegrees();
            if (this.frameSize.getWidth() == 0 || this.frameSize.getHeight() == 0) {
              Log.d("CameraXPreviewHelper", "Invalid frameSize.");
              return;
            } 
          } 
          if (this.onCameraStartedListener != null)
            this.onCameraStartedListener.onCameraStarted(previewOutput.getSurfaceTexture()); 
        });
    CameraX.bindToLifecycle((LifecycleOwner)context, new UseCase[] { (UseCase)this.preview });
  }
  
  public Size computeDisplaySizeFromViewSize(Size viewSize) {
    int scaledWidth, scaledHeight;
    if (viewSize == null || this.frameSize == null) {
      Log.d("CameraXPreviewHelper", "viewSize or frameSize is null.");
      return null;
    } 
    float frameAspectRatio = (this.frameRotation == 90 || this.frameRotation == 270) ? (this.frameSize.getHeight() / this.frameSize.getWidth()) : (this.frameSize.getWidth() / this.frameSize.getHeight());
    float viewAspectRatio = viewSize.getWidth() / viewSize.getHeight();
    if (frameAspectRatio < viewAspectRatio) {
      scaledWidth = viewSize.getWidth();
      scaledHeight = Math.round(viewSize.getWidth() / frameAspectRatio);
    } else {
      scaledHeight = viewSize.getHeight();
      scaledWidth = Math.round(viewSize.getHeight() * frameAspectRatio);
    } 
    return new Size(scaledWidth, scaledHeight);
  }
}
