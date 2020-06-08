package com.ctrlvideo.ivsdk;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.graphics.SurfaceTexture;
import android.util.Size;

import javax.annotation.Nullable;

public abstract class CameraHelper {
    protected static final String TAG = "CameraHelper";
    protected CameraHelper.OnCameraStartedListener onCameraStartedListener;
    protected CameraHelper.CameraFacing cameraFacing;

    public CameraHelper() {
    }


    public void setOnCameraStartedListener(@Nullable CameraHelper.OnCameraStartedListener listener) {
        this.onCameraStartedListener = listener;
    }

    public static enum CameraFacing {
        FRONT,
        BACK;

        private CameraFacing() {
        }
    }

    public interface OnCameraStartedListener {
        void onCameraStarted(@Nullable SurfaceTexture surfaceTexture, Size frameSize);
    }
}
