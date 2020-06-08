package com.ctrlvideo.ctrlpipe.glutil;

import android.opengl.GLES20;
import android.opengl.Matrix;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class TextureRenderer {
  private static final FloatBuffer TEXTURE_VERTICES = ShaderUtil.floatBuffer(new float[] { 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F });
  
  private static final String TAG = "TextureRenderer";
  
  private static final int ATTRIB_POSITION = 1;
  
  private static final int ATTRIB_TEXTURE_COORDINATE = 2;
  
  private int program = 0;
  
  private int frameUniform;
  
  private int textureTransformUniform;
  
  private float[] textureTransformMatrix = new float[16];
  
  public void setup() {
    Map<String, Integer> attributeLocations = new HashMap<>();
    attributeLocations.put("position", Integer.valueOf(1));
    attributeLocations.put("texture_coordinate", Integer.valueOf(2));
    this
      .program = ShaderUtil.createProgram("uniform mat4 texture_transform;\nattribute vec4 position;\nattribute mediump vec4 texture_coordinate;\nvarying mediump vec2 sample_coordinate;\n\nvoid main() {\n  gl_Position = position;\n  sample_coordinate = (texture_transform * texture_coordinate).xy;\n}", "varying mediump vec2 sample_coordinate;\nuniform sampler2D video_frame;\n\nvoid main() {\n  gl_FragColor = texture2D(video_frame, sample_coordinate);\n}", attributeLocations);
    this.frameUniform = GLES20.glGetUniformLocation(this.program, "video_frame");
    this.textureTransformUniform = GLES20.glGetUniformLocation(this.program, "texture_transform");
    ShaderUtil.checkGlError("glGetUniformLocation");
    Matrix.setIdentityM(this.textureTransformMatrix, 0);
  }
  
  public void render(int textureName) {
    GLES20.glClear(16384);
    GLES20.glActiveTexture(33984);
    ShaderUtil.checkGlError("glActiveTexture");
    GLES20.glBindTexture(3553, textureName);
    ShaderUtil.checkGlError("glBindTexture");
    GLES20.glTexParameteri(3553, 10241, 9729);
    GLES20.glTexParameteri(3553, 10240, 9729);
    GLES20.glTexParameteri(3553, 10242, 33071);
    GLES20.glTexParameteri(3553, 10243, 33071);
    ShaderUtil.checkGlError("glTexParameteri");
    GLES20.glUseProgram(this.program);
    ShaderUtil.checkGlError("glUseProgram");
    GLES20.glUniform1i(this.frameUniform, 0);
    ShaderUtil.checkGlError("glUniform1i");
    GLES20.glUniformMatrix4fv(this.textureTransformUniform, 1, false, this.textureTransformMatrix, 0);
    ShaderUtil.checkGlError("glUniformMatrix4fv");
    GLES20.glEnableVertexAttribArray(1);
    GLES20.glVertexAttribPointer(1, 2, 5126, false, 0, CommonShaders.SQUARE_VERTICES);
    GLES20.glEnableVertexAttribArray(2);
    GLES20.glVertexAttribPointer(2, 2, 5126, false, 0, TEXTURE_VERTICES);
    ShaderUtil.checkGlError("program setup");
    GLES20.glDrawArrays(5, 0, 4);
    ShaderUtil.checkGlError("glDrawArrays");
    GLES20.glBindTexture(3553, 0);
    ShaderUtil.checkGlError("glBindTexture");
    GLES20.glFlush();
  }
  
  public void release() {
    GLES20.glDeleteProgram(this.program);
  }
}
