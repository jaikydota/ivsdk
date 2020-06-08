package com.ctrlvideo.ctrlpipe.components;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTimestamp;
import android.os.Build;
import android.os.Process;
import android.util.Log;
import javax.annotation.Nullable;

import androidx.annotation.RequiresApi;

public class MicrophoneHelper {
  private static final String TAG = "MicrophoneHelper";
  
  private static final int AUDIO_ENCODING = 2;
  
  private static final int AUDIO_SOURCE = 1;
  
  private static final int BUFFER_SIZE_MULTIPLIER = 2;
  
  private static final int MAX_READ_INTERVAL_SEC = 1;
  
  private static final int BYTES_PER_MONO_SAMPLE = 2;
  
  private static final long UNINITIALIZED_TIMESTAMP = -1L;
  
  private static final long NANOS_PER_MICROS = 1000L;
  
  private static final long MICROS_PER_SECOND = 1000000L;
  
  private final int sampleRateInHz;
  
  private final int channelConfig;
  
  private final int bufferSize;
  
  private final int bytesPerSample;
  
  private byte[] audioData;
  
  private AudioTimestamp audioTimestamp;
  
  private long initialTimestamp = -1L;
  
  private long totalNumSamplesRead;
  
  private AudioRecord audioRecord;
  
  private Thread recordingThread;
  
  private boolean recording = false;
  
  private OnAudioDataAvailableListener onAudioDataAvailableListener;
  
  public MicrophoneHelper(int sampleRateInHz, int channelConfig) {
    this.sampleRateInHz = sampleRateInHz;
    this.channelConfig = channelConfig;
    int channelCount = (channelConfig == 12) ? 2 : 1;
    this.bytesPerSample = 2 * channelCount;
    int minBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, 2);
    if (minBufferSize == -1 || minBufferSize == -2) {
      Log.e("MicrophoneHelper", "AudioRecord minBufferSize unavailable.");
      this.bufferSize = sampleRateInHz * 1 * this.bytesPerSample * 2;
    } else {
      this.bufferSize = minBufferSize * 2;
    } 
  }
  
  @RequiresApi(api = Build.VERSION_CODES.M)
  private void setupAudioRecord() {
    this.audioData = new byte[this.bufferSize];
    Log.d("MicrophoneHelper", "AudioRecord(" + this.sampleRateInHz + ", " + this.bufferSize + ")");
    this
      
      .audioRecord = (new AudioRecord.Builder()).setAudioSource(1).setAudioFormat((new AudioFormat.Builder()).setEncoding(2).setSampleRate(this.sampleRateInHz).setChannelMask(this.channelConfig).build()).setBufferSizeInBytes(this.bufferSize).build();
    if (this.audioRecord.getState() != 1) {
      this.audioRecord.release();
      Log.e("MicrophoneHelper", "AudioRecord could not open.");
      return;
    } 
    this.recordingThread = new Thread(() -> {
          Process.setThreadPriority(-16);
          Log.v("MicrophoneHelper", "Running audio recording thread.");
          long startTimestamp = (this.initialTimestamp != -1L) ? this.initialTimestamp : (System.nanoTime() / 1000L);
          while (this.recording && this.audioRecord != null) {
            int numBytesRead = this.audioRecord.read(this.audioData, 0, this.bufferSize);
            long sampleBasedFallbackTimestamp = startTimestamp + this.totalNumSamplesRead * 1000000L / this.sampleRateInHz;
            long sampleBasedTimestamp = getTimestamp(sampleBasedFallbackTimestamp);
            if (numBytesRead <= 0) {
              if (numBytesRead == -3) {
                Log.e("MicrophoneHelper", "ERROR_INVALID_OPERATION");
                continue;
              } 
              if (numBytesRead == -2)
                Log.e("MicrophoneHelper", "ERROR_BAD_VALUE"); 
              continue;
            } 
            Log.v("MicrophoneHelper", "Read " + numBytesRead + " bytes of audio data.");
            if (this.recording)
              this.onAudioDataAvailableListener.onAudioDataAvailable((byte[])this.audioData.clone(), sampleBasedTimestamp); 
            int numSamplesRead = numBytesRead / this.bytesPerSample;
            this.totalNumSamplesRead += numSamplesRead;
          } 
        });
  }
  
  private long getTimestamp(long fallbackTimestamp) {
    if (Build.VERSION.SDK_INT >= 24) {
      if (this.audioTimestamp == null)
        this.audioTimestamp = new AudioTimestamp(); 
      int status = this.audioRecord.getTimestamp(this.audioTimestamp, 0);
      if (status == 0)
        return this.audioTimestamp.nanoTime / 1000L; 
      Log.e("MicrophoneHelper", "audioRecord.getTimestamp failed with status: " + status);
    } 
    return fallbackTimestamp;
  }
  
  public int getBufferSize() {
    return this.bufferSize;
  }
  
  public void setInitialTimestamp(long initialTimestamp) {
    this.initialTimestamp = initialTimestamp;
  }
  
  @RequiresApi(api = Build.VERSION_CODES.M)
  public void startMicrophone() {
    if (this.recording)
      return; 
    setupAudioRecord();
    this.audioRecord.startRecording();
    if (this.audioRecord.getRecordingState() != 3) {
      Log.e("MicrophoneHelper", "AudioRecord couldn't start recording.");
      this.audioRecord.release();
      return;
    } 
    this.recording = true;
    this.totalNumSamplesRead = 0L;
    this.recordingThread.start();
    Log.d("MicrophoneHelper", "AudioRecord is recording audio.");
  }
  
  public void stopMicrophone() {
    stopMicrophoneWithoutCleanup();
    cleanup();
    Log.d("MicrophoneHelper", "AudioRecord stopped recording audio.");
  }
  
  public void stopMicrophoneWithoutCleanup() {
    if (!this.recording)
      return; 
    this.recording = false;
    try {
      if (this.recordingThread != null)
        this.recordingThread.join(); 
    } catch (InterruptedException ie) {
      Log.e("MicrophoneHelper", "Exception: ", ie);
    } 
    this.audioRecord.stop();
    if (this.audioRecord.getRecordingState() != 1)
      Log.e("MicrophoneHelper", "AudioRecord.stop() didn't run properly."); 
  }
  
  public void cleanup() {
    if (this.recording)
      return; 
    this.audioRecord.release();
  }
  
  public void setOnAudioDataAvailableListener(@Nullable OnAudioDataAvailableListener listener) {
    this.onAudioDataAvailableListener = listener;
  }
  
  public static interface OnAudioDataAvailableListener {
    void onAudioDataAvailable(byte[] param1ArrayOfbyte, long param1Long);
  }
}
