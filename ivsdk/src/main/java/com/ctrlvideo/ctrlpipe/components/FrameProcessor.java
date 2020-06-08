package com.ctrlvideo.ctrlpipe.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.mediapipe.framework.GraphTextureFrame;
import com.google.mediapipe.framework.MediaPipeException;
import com.google.mediapipe.framework.Packet;
import com.google.mediapipe.framework.PacketCallback;
import com.google.mediapipe.framework.PacketGetter;
import com.google.mediapipe.framework.SurfaceOutput;
import com.google.mediapipe.framework.TextureFrame;
import com.google.common.base.Preconditions;
import com.google.mediapipe.framework.AndroidAssetUtil;
import com.google.mediapipe.framework.AndroidPacketCreator;
import com.google.mediapipe.framework.Graph;
import com.google.mediapipe.framework.GraphService;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;

public class FrameProcessor implements TextureFrameProcessor {
  private static final String TAG = "FrameProcessor";
  
  private List<TextureFrameConsumer> consumers = new ArrayList<>();
  
  private Graph mediapipeGraph;
  
  private AndroidPacketCreator packetCreator;
  
  private OnWillAddFrameListener addFrameListener;
  
  private String videoInputStream;
  
  private String videoInputStreamCpu;
  
  private String videoOutputStream;
  
  private SurfaceOutput videoSurfaceOutput;
  
  private final AtomicBoolean started = new AtomicBoolean(false);
  
  private boolean hybridPath = false;
  
  public FrameProcessor(Context context, long parentNativeContext, String graphName, String inputStream, String outputStream) {
    this.mediapipeGraph = new Graph();
    this.videoInputStream = inputStream;
    this.videoOutputStream = outputStream;
    try {
      if ((new File(graphName)).isAbsolute()) {
        this.mediapipeGraph.loadBinaryGraph(graphName);
      } else {
        this.mediapipeGraph.loadBinaryGraph(
            AndroidAssetUtil.getAssetBytes(context.getAssets(), graphName));
      } 
      this.packetCreator = new AndroidPacketCreator(this.mediapipeGraph);
      this.mediapipeGraph.addPacketCallback(this.videoOutputStream, new PacketCallback() {
            public void process(Packet packet) {
              List<TextureFrameConsumer> currentConsumers;
              synchronized (this) {
                currentConsumers = FrameProcessor.this.consumers;
              } 
              for (TextureFrameConsumer consumer : currentConsumers) {
                GraphTextureFrame graphTextureFrame = PacketGetter.getTextureFrame(packet);
                if (Log.isLoggable("FrameProcessor", 2))
                  Log.v("FrameProcessor", 
                      
                      String.format("Output tex: %d width: %d height: %d to consumer %h", new Object[] { Integer.valueOf(graphTextureFrame.getTextureName()), Integer.valueOf(graphTextureFrame.getWidth()), Integer.valueOf(graphTextureFrame.getHeight()), consumer })); 
                consumer.onNewFrame((TextureFrame)graphTextureFrame);
              } 
            }
          });
      this.mediapipeGraph.setParentGlContext(parentNativeContext);
    } catch (MediaPipeException e) {
      Log.e("FrameProcessor", "Mediapipe error: ", (Throwable)e);
    } 
    this.videoSurfaceOutput = this.mediapipeGraph.addSurfaceOutput(this.videoOutputStream);
  }
  
  public static interface OnWillAddFrameListener {
    void onWillAddFrame(long param1Long);
  }
  
  public synchronized <T> void setServiceObject(GraphService<T> service, T object) {
    this.mediapipeGraph.setServiceObject(service, object);
  }
  
  public void setInputSidePackets(Map<String, Packet> inputSidePackets) {
    Preconditions.checkState(
        !this.started.get(), "setInputSidePackets must be called before the graph is started");
    this.mediapipeGraph.setInputSidePackets(inputSidePackets);
  }
  
  public void setConsumer(TextureFrameConsumer listener) {
    synchronized (this) {
      this.consumers = Arrays.asList(new TextureFrameConsumer[] { listener });
    } 
  }
  
  public void setVideoInputStreamCpu(String inputStream) {
    this.videoInputStreamCpu = inputStream;
  }
  
  public void setHybridPath() {
    this.hybridPath = true;
  }
  
  public void addPacketCallback(String outputStream, PacketCallback callback) {
    this.mediapipeGraph.addPacketCallback(outputStream, callback);
  }
  
  public void addConsumer(TextureFrameConsumer listener) {
    synchronized (this) {
      List<TextureFrameConsumer> newConsumers = new ArrayList<>(this.consumers);
      newConsumers.add(listener);
      this.consumers = newConsumers;
    } 
  }
  
  public boolean removeConsumer(TextureFrameConsumer listener) {
    boolean existed;
    synchronized (this) {
      List<TextureFrameConsumer> newConsumers = new ArrayList<>(this.consumers);
      existed = newConsumers.remove(listener);
      this.consumers = newConsumers;
    } 
    return existed;
  }
  
  public Graph getGraph() {
    return this.mediapipeGraph;
  }
  
  public AndroidPacketCreator getPacketCreator() {
    return this.packetCreator;
  }
  
  public SurfaceOutput getVideoSurfaceOutput() {
    return this.videoSurfaceOutput;
  }
  
  public void close() {
    if (this.started.get()) {
      try {
        this.mediapipeGraph.closeAllPacketSources();
        this.mediapipeGraph.waitUntilGraphDone();
      } catch (MediaPipeException e) {
        Log.e("FrameProcessor", "Mediapipe error: ", (Throwable)e);
      } 
      try {
        this.mediapipeGraph.tearDown();
      } catch (MediaPipeException e) {
        Log.e("FrameProcessor", "Mediapipe error: ", (Throwable)e);
      } 
    } 
  }
  
  public void preheat() {
    if (!this.started.getAndSet(true))
      startGraph(); 
  }
  
  public void setOnWillAddFrameListener(@Nullable OnWillAddFrameListener addFrameListener) {
    this.addFrameListener = addFrameListener;
  }
  
  private boolean maybeAcceptNewFrame() {
    if (!this.started.getAndSet(true))
      startGraph(); 
    return true;
  }
  
  public void onNewFrame(TextureFrame frame) {
    if (Log.isLoggable("FrameProcessor", 2))
      Log.v("FrameProcessor", 
          
          String.format("Input tex: %d width: %d height: %d", new Object[] { Integer.valueOf(frame.getTextureName()), Integer.valueOf(frame.getWidth()), Integer.valueOf(frame.getHeight()) })); 
    if (!maybeAcceptNewFrame()) {
      frame.release();
      return;
    } 
    if (this.addFrameListener != null)
      this.addFrameListener.onWillAddFrame(frame.getTimestamp()); 
    Packet imagePacket = this.packetCreator.createGpuBuffer(frame);
    try {
      this.mediapipeGraph.addConsumablePacketToInputStream(this.videoInputStream, imagePacket, frame
          .getTimestamp());
    } catch (MediaPipeException e) {
      Log.e("FrameProcessor", "Mediapipe error: ", (Throwable)e);
    } 
    imagePacket.release();
  }
  
  public void onNewFrame(Bitmap bitmap, long timestamp) {
    if (!maybeAcceptNewFrame())
      return; 
    if (!this.hybridPath && this.addFrameListener != null)
      this.addFrameListener.onWillAddFrame(timestamp); 
    Packet packet = getPacketCreator().createRgbImageFrame(bitmap);
    try {
      this.mediapipeGraph.addConsumablePacketToInputStream(this.videoInputStreamCpu, packet, timestamp);
    } catch (MediaPipeException e) {
      Log.e("FrameProcessor", "Mediapipe error: ", (Throwable)e);
    } 
    packet.release();
  }
  
  public void waitUntilIdle() {
    try {
      this.mediapipeGraph.waitUntilGraphIdle();
    } catch (MediaPipeException e) {
      Log.e("FrameProcessor", "Mediapipe error: ", (Throwable)e);
    } 
  }
  
  private void startGraph() {
    this.mediapipeGraph.startRunningGraph();
  }
}
