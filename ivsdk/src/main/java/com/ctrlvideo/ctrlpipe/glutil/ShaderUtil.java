package com.ctrlvideo.ctrlpipe.glutil;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.google.common.flogger.FluentLogger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Map;
import javax.annotation.Nullable;

public class ShaderUtil {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  
  public static int loadShader(int shaderType, String source) {
    int shader = GLES20.glCreateShader(shaderType);
    GLES20.glShaderSource(shader, source);
    GLES20.glCompileShader(shader);
    int[] compiled = new int[1];
    GLES20.glGetShaderiv(shader, 35713, compiled, 0);
    if (compiled[0] == 0) {
      ((FluentLogger.Api)logger.atSevere()).log("Could not compile shader %d: %s", shaderType, 
          GLES20.glGetShaderInfoLog(shader));
      GLES20.glDeleteShader(shader);
      shader = 0;
    } 
    return shader;
  }
  
  public static int createProgram(String vertexSource, String fragmentSource, @Nullable Map<String, Integer> attributeLocations) {
    int vertexShader = loadShader(35633, vertexSource);
    if (vertexShader == 0)
      return 0; 
    int fragmentShader = loadShader(35632, fragmentSource);
    if (fragmentShader == 0)
      return 0; 
    int program = GLES20.glCreateProgram();
    if (program == 0)
      ((FluentLogger.Api)logger.atSevere()).log("Could not create program"); 
    GLES20.glAttachShader(program, vertexShader);
    GLES20.glAttachShader(program, fragmentShader);
    if (attributeLocations != null)
      for (Map.Entry<String, Integer> entry : attributeLocations.entrySet())
        GLES20.glBindAttribLocation(program, ((Integer)entry.getValue()).intValue(), entry.getKey());  
    GLES20.glLinkProgram(program);
    int[] linkStatus = new int[1];
    GLES20.glGetProgramiv(program, 35714, linkStatus, 0);
    if (linkStatus[0] != 1) {
      ((FluentLogger.Api)logger.atSevere()).log("Could not link program: %s", GLES20.glGetProgramInfoLog(program));
      GLES20.glDeleteProgram(program);
      program = 0;
    } 
    return program;
  }
  
  public static int createRgbaTexture(int width, int height) {
    int[] textureName = { 0 };
    GLES20.glGenTextures(1, textureName, 0);
    GLES20.glActiveTexture(33984);
    GLES20.glBindTexture(3553, textureName[0]);
    GLES20.glTexImage2D(3553, 0, 6408, width, height, 0, 6408, 5121, null);
    checkGlError("glTexImage2D");
    GLES20.glTexParameteri(3553, 10241, 9729);
    GLES20.glTexParameteri(3553, 10240, 9729);
    GLES20.glTexParameteri(3553, 10242, 33071);
    GLES20.glTexParameteri(3553, 10243, 33071);
    checkGlError("texture setup");
    return textureName[0];
  }
  
  public static int createRgbaTexture(Bitmap bitmap) {
    int[] textureName = { 0 };
    GLES20.glGenTextures(1, textureName, 0);
    GLES20.glActiveTexture(33984);
    GLES20.glBindTexture(3553, textureName[0]);
    GLUtils.texImage2D(3553, 0, bitmap, 0);
    checkGlError("texImage2D");
    GLES20.glTexParameteri(3553, 10241, 9729);
    GLES20.glTexParameteri(3553, 10240, 9729);
    GLES20.glTexParameteri(3553, 10242, 33071);
    GLES20.glTexParameteri(3553, 10243, 33071);
    checkGlError("texture setup");
    return textureName[0];
  }
  
  public static FloatBuffer floatBuffer(float... values) {
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(values.length * 4);
    byteBuffer.order(ByteOrder.nativeOrder());
    FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
    floatBuffer.put(values);
    floatBuffer.position(0);
    return floatBuffer;
  }
  
  public static void checkGlError(String msg) {
    int error = GLES20.glGetError();
    if (error != 0)
      throw new RuntimeException(msg + ": GL error: 0x" + Integer.toHexString(error)); 
  }
}
