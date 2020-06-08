package com.ctrlvideo.ctrlpipe.components;

import com.google.mediapipe.framework.TextureFrame;

public interface TextureFrameConsumer {
  void onNewFrame(TextureFrame paramTextureFrame);
}
