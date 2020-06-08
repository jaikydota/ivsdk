//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ctrlvideo.ctrlpipe.glutil;

import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.google.mediapipe.framework.Compat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

public class EglManager {
  private static final String TAG = "EglManager";
  public static final int EGL_CONTEXT_CLIENT_VERSION = 12440;
  public static final int EGL_OPENGL_ES2_BIT = 4;
  public static final int EGL_OPENGL_ES3_BIT_KHR = 64;
  public static final int EGL_DRAW = 12377;
  public static final int EGL_READ = 12378;
  public static final int EGL14_API_LEVEL = 17;
  private EGL10 egl;
  private EGLDisplay eglDisplay;
  private EGLConfig eglConfig;
  private EGLContext eglContext;
  private int[] singleIntArray;
  private int glVersion;
  private long nativeEglContext;
  private android.opengl.EGLContext egl14Context;

  public EglManager(@Nullable Object parentContext) {
    this(parentContext, (int[])null);
  }

  public EglManager(@Nullable Object parentContext, @Nullable int[] additionalConfigAttributes) {
    this.eglDisplay = EGL10.EGL_NO_DISPLAY;
    this.eglConfig = null;
    this.eglContext = EGL10.EGL_NO_CONTEXT;
    this.nativeEglContext = 0L;
    this.egl14Context = null;
    this.singleIntArray = new int[1];
    this.egl = (EGL10)EGLContext.getEGL();
    this.eglDisplay = this.egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
    if (this.eglDisplay == EGL10.EGL_NO_DISPLAY) {
      throw new RuntimeException("eglGetDisplay failed");
    } else {
      int[] version = new int[2];
      if (!this.egl.eglInitialize(this.eglDisplay, version)) {
        throw new RuntimeException("eglInitialize failed");
      } else {
        EGLContext realParentContext;
        if (parentContext == null) {
          realParentContext = EGL10.EGL_NO_CONTEXT;
        } else if (parentContext instanceof EGLContext) {
          realParentContext = (EGLContext)parentContext;
        } else {
          if (VERSION.SDK_INT < 17 || !(parentContext instanceof android.opengl.EGLContext)) {
            throw new RuntimeException("invalid parent context: " + parentContext);
          }

          if (parentContext == EGL14.EGL_NO_CONTEXT) {
            realParentContext = EGL10.EGL_NO_CONTEXT;
          } else {
            realParentContext = this.egl10ContextFromEgl14Context((android.opengl.EGLContext)parentContext);
          }
        }

        try {
          this.createContext(realParentContext, 3, additionalConfigAttributes);
          this.glVersion = 3;
        } catch (RuntimeException var6) {
          Log.w("EglManager", "could not create GLES 3 context: " + var6);
          this.createContext(realParentContext, 2, additionalConfigAttributes);
          this.glVersion = 2;
        }

      }
    }
  }

  public EGLContext getContext() {
    return this.eglContext;
  }

  public long getNativeContext() {
    if (this.nativeEglContext == 0L) {
      this.grabContextVariants();
    }

    return this.nativeEglContext;
  }

  public android.opengl.EGLContext getEgl14Context() {
    if (VERSION.SDK_INT < 17) {
      throw new RuntimeException("cannot use EGL14 on API level < 17");
    } else {
      if (this.egl14Context == null) {
        this.grabContextVariants();
      }

      return this.egl14Context;
    }
  }

  public int getGlMajorVersion() {
    return this.glVersion;
  }

  public void makeCurrent(EGLSurface drawSurface, EGLSurface readSurface) {
    if (!this.egl.eglMakeCurrent(this.eglDisplay, drawSurface, readSurface, this.eglContext)) {
      throw new RuntimeException("eglMakeCurrent failed");
    }
  }

  public void makeNothingCurrent() {
    if (!this.egl.eglMakeCurrent(this.eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT)) {
      throw new RuntimeException("eglMakeCurrent failed");
    }
  }

  public EGLSurface createWindowSurface(Object surface) {
    if (!(surface instanceof Surface) && !(surface instanceof SurfaceTexture) && !(surface instanceof SurfaceHolder) && !(surface instanceof SurfaceView)) {
      throw new RuntimeException("invalid surface: " + surface);
    } else {
      int[] surfaceAttribs = new int[]{12344};
      EGLSurface eglSurface = this.egl.eglCreateWindowSurface(this.eglDisplay, this.eglConfig, surface, surfaceAttribs);
      this.checkEglError("eglCreateWindowSurface");
      if (eglSurface == null) {
        throw new RuntimeException("surface was null");
      } else {
        return eglSurface;
      }
    }
  }

  public EGLSurface createOffscreenSurface(int width, int height) {
    int[] surfaceAttribs = new int[]{12375, width, 12374, height, 12344};
    EGLSurface eglSurface = this.egl.eglCreatePbufferSurface(this.eglDisplay, this.eglConfig, surfaceAttribs);
    this.checkEglError("eglCreatePbufferSurface");
    if (eglSurface == null) {
      throw new RuntimeException("surface was null");
    } else {
      return eglSurface;
    }
  }

  public void release() {
    if (this.eglDisplay != EGL10.EGL_NO_DISPLAY) {
      this.egl.eglMakeCurrent(this.eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
      this.egl.eglDestroyContext(this.eglDisplay, this.eglContext);
      this.egl.eglTerminate(this.eglDisplay);
    }

    this.eglDisplay = EGL10.EGL_NO_DISPLAY;
    this.eglContext = EGL10.EGL_NO_CONTEXT;
    this.eglConfig = null;
  }

  public void releaseSurface(EGLSurface eglSurface) {
    this.egl.eglDestroySurface(this.eglDisplay, eglSurface);
  }

  private void createContext(EGLContext parentContext, int glVersion, @Nullable int[] additionalConfigAttributes) {
    this.eglConfig = this.getConfig(glVersion, additionalConfigAttributes);
    if (this.eglConfig == null) {
      throw new RuntimeException("Unable to find a suitable EGLConfig");
    } else {
      int[] contextAttrs = new int[]{12440, glVersion, 12344};
      this.eglContext = this.egl.eglCreateContext(this.eglDisplay, this.eglConfig, parentContext, contextAttrs);
      if (this.eglContext == null) {
        int error = this.egl.eglGetError();
        throw new RuntimeException("Could not create GL context: EGL error: 0x" + Integer.toHexString(error) + (error == 12294 ? ": parent context uses a different version of OpenGL" : ""));
      }
    }
  }

  private void grabContextVariants() {
    EGLContext previousContext = this.egl.eglGetCurrentContext();
    EGLDisplay previousDisplay = this.egl.eglGetCurrentDisplay();
    EGLSurface previousDrawSurface = this.egl.eglGetCurrentSurface(12377);
    EGLSurface previousReadSurface = this.egl.eglGetCurrentSurface(12378);
    EGLSurface tempEglSurface = null;
    if (previousContext != this.eglContext) {
      tempEglSurface = this.createOffscreenSurface(1, 1);
      this.makeCurrent(tempEglSurface, tempEglSurface);
    }

    this.nativeEglContext = Compat.getCurrentNativeEGLContext();
    if (VERSION.SDK_INT >= 17) {
      this.egl14Context = EGL14.eglGetCurrentContext();
    }

    if (previousContext != this.eglContext) {
      this.egl.eglMakeCurrent(previousDisplay, previousDrawSurface, previousReadSurface, previousContext);
      this.releaseSurface(tempEglSurface);
    }

  }

  private EGLContext egl10ContextFromEgl14Context(android.opengl.EGLContext context) {
    android.opengl.EGLContext previousContext = EGL14.eglGetCurrentContext();
    android.opengl.EGLDisplay previousDisplay = EGL14.eglGetCurrentDisplay();
    android.opengl.EGLSurface previousDrawSurface = EGL14.eglGetCurrentSurface(12377);
    android.opengl.EGLSurface previousReadSurface = EGL14.eglGetCurrentSurface(12378);
    android.opengl.EGLDisplay defaultDisplay = EGL14.eglGetDisplay(0);
    android.opengl.EGLSurface tempEglSurface = null;
    if (!previousContext.equals(context)) {
      int[] surfaceAttribs = new int[]{12375, 1, 12374, 1, 12344};
      android.opengl.EGLConfig tempConfig = this.getThrowawayConfig(defaultDisplay);
      tempEglSurface = EGL14.eglCreatePbufferSurface(previousDisplay, tempConfig, surfaceAttribs, 0);
      EGL14.eglMakeCurrent(defaultDisplay, tempEglSurface, tempEglSurface, context);
    }

    EGLContext egl10Context = this.egl.eglGetCurrentContext();
    if (!previousContext.equals(context)) {
      EGL14.eglMakeCurrent(previousDisplay, previousDrawSurface, previousReadSurface, previousContext);
      EGL14.eglDestroySurface(defaultDisplay, tempEglSurface);
    }

    return egl10Context;
  }

  private android.opengl.EGLConfig getThrowawayConfig(android.opengl.EGLDisplay display) {
    int[] attribList = new int[]{12339, 5, 12344};
    android.opengl.EGLConfig[] configs = new android.opengl.EGLConfig[1];
    int[] numConfigs = this.singleIntArray;
    if (!EGL14.eglChooseConfig(display, attribList, 0, configs, 0, 1, numConfigs, 0)) {
      throw new IllegalArgumentException("eglChooseConfig failed");
    } else if (numConfigs[0] <= 0) {
      throw new IllegalArgumentException("No configs match requested attributes");
    } else {
      return configs[0];
    }
  }

  private int[] mergeAttribLists(int[] list1, @Nullable int[] list2) {
    if (list2 == null) {
      return list1;
    } else {
      HashMap<Integer, Integer> attribMap = new HashMap();
      int[][] var4 = new int[][]{list1, list2};
      int i = var4.length;

      for(int var6 = 0; var6 < i; ++var6) {
        int[] list = var4[var6];

        for(int j = 0; j < list.length / 2; ++j) {
          int key = list[2 * j];
          int value = list[2 * j + 1];
          if (key == 12344) {
            break;
          }

          attribMap.put(key, value);
        }
      }

      int[] merged = new int[attribMap.size() * 2 + 1];
      i = 0;

      Entry e;
      for(Iterator var12 = attribMap.entrySet().iterator(); var12.hasNext(); merged[i++] = (Integer)e.getValue()) {
        e = (Entry)var12.next();
        merged[i++] = (Integer)e.getKey();
      }

      merged[i] = 12344;
      return merged;
    }
  }

  private EGLConfig getConfig(int glVersion, @Nullable int[] additionalConfigAttributes) {
    int[] baseAttribList = new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 16, 12352, glVersion == 3 ? 64 : 4, 12339, 5, 12344};
    int[] attribList = this.mergeAttribLists(baseAttribList, additionalConfigAttributes);
    int[] numConfigs = this.singleIntArray;
    if (!this.egl.eglChooseConfig(this.eglDisplay, attribList, (EGLConfig[])null, 0, numConfigs)) {
      throw new IllegalArgumentException("eglChooseConfig failed");
    } else if (numConfigs[0] <= 0) {
      throw new IllegalArgumentException("No configs match requested attributes");
    } else {
      EGLConfig[] configs = new EGLConfig[numConfigs[0]];
      if (!this.egl.eglChooseConfig(this.eglDisplay, attribList, configs, configs.length, numConfigs)) {
        throw new IllegalArgumentException("eglChooseConfig#2 failed");
      } else {
        EGLConfig bestConfig = null;
        EGLConfig[] var8 = configs;
        int var9 = configs.length;

        for(int var10 = 0; var10 < var9; ++var10) {
          EGLConfig config = var8[var10];
          int r = this.findConfigAttrib(config, 12324, 0);
          int g = this.findConfigAttrib(config, 12323, 0);
          int b = this.findConfigAttrib(config, 12322, 0);
          int a = this.findConfigAttrib(config, 12321, 0);
          if (r == 8 && g == 8 && b == 8 && a == 8) {
            bestConfig = config;
            break;
          }
        }

        if (bestConfig == null) {
          bestConfig = configs[0];
        }

        return bestConfig;
      }
    }
  }

  private void checkEglError(String msg) {
    int error;
    if ((error = this.egl.eglGetError()) != 12288) {
      throw new RuntimeException(msg + ": EGL error: 0x" + Integer.toHexString(error));
    }
  }

  private int findConfigAttrib(EGLConfig config, int attribute, int defaultValue) {
    return this.egl.eglGetConfigAttrib(this.eglDisplay, config, attribute, this.singleIntArray) ? this.singleIntArray[0] : defaultValue;
  }
}
