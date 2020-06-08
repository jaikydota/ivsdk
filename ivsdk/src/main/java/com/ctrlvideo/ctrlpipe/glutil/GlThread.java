package com.ctrlvideo.ctrlpipe.glutil;

import android.opengl.GLES20;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import javax.annotation.Nullable;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLSurface;

public class GlThread extends Thread {
  private static final String TAG = "GlThread";
  
  private static final String THREAD_NAME = "mediapipe.glutil.GlThread";
  
  private boolean ready;
  
  private final Object startLock = new Object();
  
  protected volatile EglManager eglManager;
  
  protected EGLSurface eglSurface = null;
  
  protected Handler handler = null;
  
  protected Looper looper = null;
  
  protected int framebuffer = 0;
  
  public GlThread(@Nullable Object parentContext) {
    this(parentContext, (int[])null);
  }
  
  public GlThread(@Nullable Object parentContext, @Nullable int[] additionalConfigAttributes) {
    this.eglManager = new EglManager(parentContext, additionalConfigAttributes);
    setName("mediapipe.glutil.GlThread");
  }
  
  public Handler getHandler() {
    return this.handler;
  }
  
  public Looper getLooper() {
    return this.looper;
  }
  
  public EglManager getEglManager() {
    return this.eglManager;
  }
  
  public EGLContext getEGLContext() {
    return this.eglManager.getContext();
  }
  
  public int getFramebuffer() {
    return this.framebuffer;
  }
  
  public void bindFramebuffer(int texture, int width, int height) {
    GLES20.glBindFramebuffer(36160, this.framebuffer);
    GLES20.glFramebufferTexture2D(36160, 36064, 3553, texture, 0);
    int status = GLES20.glCheckFramebufferStatus(36160);
    if (status != 36053)
      throw new RuntimeException("Framebuffer not complete, status=" + status); 
    GLES20.glViewport(0, 0, width, height);
    ShaderUtil.checkGlError("glViewport");
  }
  
  public void run() {
    Looper.prepare();
    this.handler = createHandler();
    this.looper = Looper.myLooper();
    Log.d("GlThread", String.format("Starting GL thread %s", new Object[] { getName() }));
    prepareGl();
    synchronized (this.startLock) {
      this.ready = true;
      this.startLock.notify();
    } 
    Looper.loop();
    this.looper = null;
    releaseGl();
    this.eglManager.release();
    Log.d("GlThread", String.format("Stopping GL thread %s", new Object[] { getName() }));
    synchronized (this.startLock) {
      this.ready = false;
    } 
  }
  
  public boolean quitSafely() {
    if (this.looper == null)
      return false; 
    this.looper.quitSafely();
    return true;
  }
  
  public void waitUntilReady() throws InterruptedException {
    synchronized (this.startLock) {
      while (!this.ready)
        this.startLock.wait(); 
    } 
  }
  
  public void prepareGl() {
    this.eglSurface = createEglSurface();
    this.eglManager.makeCurrent(this.eglSurface, this.eglSurface);
    GLES20.glDisable(2929);
    GLES20.glDisable(2884);
    int[] values = new int[1];
    GLES20.glGenFramebuffers(1, values, 0);
    this.framebuffer = values[0];
  }
  
  public void releaseGl() {
    if (this.framebuffer != 0) {
      int[] values = new int[1];
      values[0] = this.framebuffer;
      GLES20.glDeleteFramebuffers(1, values, 0);
      this.framebuffer = 0;
    } 
    this.eglManager.makeNothingCurrent();
    if (this.eglSurface != null) {
      this.eglManager.releaseSurface(this.eglSurface);
      this.eglSurface = null;
    } 
  }
  
  protected Handler createHandler() {
    return new Handler();
  }
  
  protected EGLSurface createEglSurface() {
    return this.eglManager.createOffscreenSurface(1, 1);
  }
}
