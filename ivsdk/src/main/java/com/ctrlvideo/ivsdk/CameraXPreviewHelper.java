package com.ctrlvideo.ivsdk;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.util.Size;

import androidx.camera.core.CameraX;
import androidx.camera.core.CameraX.LensFacing;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.camera.core.PreviewConfig.Builder;
import androidx.camera.core.UseCase;
import androidx.lifecycle.LifecycleOwner;


public class CameraXPreviewHelper extends CameraHelper {
    private static final String TAG = "CameraXPreviewHelper";
    private Preview preview;
    public Size frameSize;
    private int frameRotation;

    public CameraXPreviewHelper() {
    }

    public void startCamera(Activity context, CameraFacing cameraFacing, SurfaceTexture surfaceTexture) {
        LensFacing cameraLensFacing = cameraFacing == CameraFacing.FRONT ? LensFacing.FRONT : LensFacing.BACK;
        PreviewConfig previewConfig = (new Builder()).setLensFacing(cameraLensFacing).build();
        this.preview = new Preview(previewConfig);
        this.preview.setOnPreviewOutputUpdateListener((previewOutput) -> {
            if (!previewOutput.getTextureSize().equals(this.frameSize)) {
                this.frameSize = previewOutput.getTextureSize();
                this.frameRotation = previewOutput.getRotationDegrees();
                if (this.frameSize.getWidth() == 0 || this.frameSize.getHeight() == 0) {
                    Log.d("CameraXPreviewHelper", "Invalid frameSize.");
                    return;
                }
            }

            if (this.onCameraStartedListener != null) {
                this.onCameraStartedListener.onCameraStarted(previewOutput.getSurfaceTexture(), this.frameSize);
            }

        });
        CameraX.bindToLifecycle((LifecycleOwner)context, new UseCase[]{this.preview});
    }

    public void stopCamera(){
        if (this.preview != null)
            this.preview.removePreviewOutputListener();
        CameraX.unbindAll();
    }

    public Size computeDisplaySizeFromViewSize(Size viewSize) {
        if (viewSize != null && this.frameSize != null) {
            float frameAspectRatio = this.frameRotation != 90 && this.frameRotation != 270 ? (float)this.frameSize.getWidth() / (float)this.frameSize.getHeight() : (float)this.frameSize.getHeight() / (float)this.frameSize.getWidth();
            float viewAspectRatio = (float)viewSize.getWidth() / (float)viewSize.getHeight();
            int scaledWidth;
            int scaledHeight;
            if (frameAspectRatio < viewAspectRatio) {
                scaledWidth = viewSize.getWidth();
                scaledHeight = Math.round((float)viewSize.getWidth() / frameAspectRatio);
            } else {
                scaledHeight = viewSize.getHeight();
                scaledWidth = Math.round((float)viewSize.getHeight() * frameAspectRatio);
            }

            return new Size(scaledWidth, scaledHeight);
        } else {
            Log.d("CameraXPreviewHelper", "viewSize or frameSize is null.");
            return null;
        }
    }
}
