package com.ctrlvideo.ctrlpipe.formats.proto;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class LandmarkProto {
  public static void registerAllExtensions(ExtensionRegistryLite registry) {}
  
  public static final class Landmark extends GeneratedMessageLite<Landmark, Landmark.Builder> implements LandmarkOrBuilder {
    private int bitField0_;
    
    public static final int X_FIELD_NUMBER = 1;
    
    private float x_;
    
    public static final int Y_FIELD_NUMBER = 2;
    
    private float y_;
    
    public static final int Z_FIELD_NUMBER = 3;
    
    private float z_;
    
    public boolean hasX() {
      return ((this.bitField0_ & 0x1) == 1);
    }
    
    public float getX() {
      return this.x_;
    }
    
    private void setX(float value) {
      this.bitField0_ |= 0x1;
      this.x_ = value;
    }
    
    private void clearX() {
      this.bitField0_ &= 0xFFFFFFFE;
      this.x_ = 0.0F;
    }
    
    public boolean hasY() {
      return ((this.bitField0_ & 0x2) == 2);
    }
    
    public float getY() {
      return this.y_;
    }
    
    private void setY(float value) {
      this.bitField0_ |= 0x2;
      this.y_ = value;
    }
    
    private void clearY() {
      this.bitField0_ &= 0xFFFFFFFD;
      this.y_ = 0.0F;
    }
    
    public boolean hasZ() {
      return ((this.bitField0_ & 0x4) == 4);
    }
    
    public float getZ() {
      return this.z_;
    }
    
    private void setZ(float value) {
      this.bitField0_ |= 0x4;
      this.z_ = value;
    }
    
    private void clearZ() {
      this.bitField0_ &= 0xFFFFFFFB;
      this.z_ = 0.0F;
    }
    
    public void writeTo(CodedOutputStream output) throws IOException {
      if ((this.bitField0_ & 0x1) == 1)
        output.writeFloat(1, this.x_); 
      if ((this.bitField0_ & 0x2) == 2)
        output.writeFloat(2, this.y_); 
      if ((this.bitField0_ & 0x4) == 4)
        output.writeFloat(3, this.z_); 
      this.unknownFields.writeTo(output);
    }
    
    public int getSerializedSize() {
      int size = this.memoizedSerializedSize;
      if (size != -1)
        return size; 
      size = 0;
      if ((this.bitField0_ & 0x1) == 1)
        size += 
          CodedOutputStream.computeFloatSize(1, this.x_); 
      if ((this.bitField0_ & 0x2) == 2)
        size += 
          CodedOutputStream.computeFloatSize(2, this.y_); 
      if ((this.bitField0_ & 0x4) == 4)
        size += 
          CodedOutputStream.computeFloatSize(3, this.z_); 
      size += this.unknownFields.getSerializedSize();
      this.memoizedSerializedSize = size;
      return size;
    }
    
    public static Landmark parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return (Landmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }
    
    public static Landmark parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return (Landmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }
    
    public static Landmark parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return (Landmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }
    
    public static Landmark parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return (Landmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }
    
    public static Landmark parseFrom(InputStream input) throws IOException {
      return (Landmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }
    
    public static Landmark parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return (Landmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    
    public static Landmark parseDelimitedFrom(InputStream input) throws IOException {
      return (Landmark)parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    
    public static Landmark parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return (Landmark)parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    
    public static Landmark parseFrom(CodedInputStream input) throws IOException {
      return (Landmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }
    
    public static Landmark parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return (Landmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    
    public static Builder newBuilder() {
      return (Builder)DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(Landmark prototype) {
      return (Builder)((Builder)DEFAULT_INSTANCE.toBuilder()).mergeFrom(prototype);
    }
    
    public static final class Builder extends GeneratedMessageLite.Builder<Landmark, Builder> implements LandmarkProto.LandmarkOrBuilder {
      private Builder() {
        super(LandmarkProto.Landmark.DEFAULT_INSTANCE);
      }
      
      public boolean hasX() {
        return ((LandmarkProto.Landmark)this.instance).hasX();
      }
      
      public float getX() {
        return ((LandmarkProto.Landmark)this.instance).getX();
      }
      
      public Builder setX(float value) {
        copyOnWrite();
        ((LandmarkProto.Landmark)this.instance).setX(value);
        return this;
      }
      
      public Builder clearX() {
        copyOnWrite();
        ((LandmarkProto.Landmark)this.instance).clearX();
        return this;
      }
      
      public boolean hasY() {
        return ((LandmarkProto.Landmark)this.instance).hasY();
      }
      
      public float getY() {
        return ((LandmarkProto.Landmark)this.instance).getY();
      }
      
      public Builder setY(float value) {
        copyOnWrite();
        ((LandmarkProto.Landmark)this.instance).setY(value);
        return this;
      }
      
      public Builder clearY() {
        copyOnWrite();
        ((LandmarkProto.Landmark)this.instance).clearY();
        return this;
      }
      
      public boolean hasZ() {
        return ((LandmarkProto.Landmark)this.instance).hasZ();
      }
      
      public float getZ() {
        return ((LandmarkProto.Landmark)this.instance).getZ();
      }
      
      public Builder setZ(float value) {
        copyOnWrite();
        ((LandmarkProto.Landmark)this.instance).setZ(value);
        return this;
      }
      
      public Builder clearZ() {
        copyOnWrite();
        ((LandmarkProto.Landmark)this.instance).clearZ();
        return this;
      }
    }
    
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
      GeneratedMessageLite.Visitor visitor;
      CodedInputStream input;
      Landmark other;
      ExtensionRegistryLite extensionRegistry;
      switch (method) {
        case NEW_MUTABLE_INSTANCE:
          return new Landmark();
        case IS_INITIALIZED:
          return DEFAULT_INSTANCE;
        case MAKE_IMMUTABLE:
          return null;
        case NEW_BUILDER:
          return new Builder();
        case VISIT:
          visitor = (GeneratedMessageLite.Visitor)arg0;
          other = (Landmark)arg1;
          this.x_ = visitor.visitFloat(
              hasX(), this.x_, other
              .hasX(), other.x_);
          this.y_ = visitor.visitFloat(
              hasY(), this.y_, other
              .hasY(), other.y_);
          this.z_ = visitor.visitFloat(
              hasZ(), this.z_, other
              .hasZ(), other.z_);
          if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE)
            this.bitField0_ |= other.bitField0_; 
          return this;
        case MERGE_FROM_STREAM:
          input = (CodedInputStream)arg0;
          extensionRegistry = (ExtensionRegistryLite)arg1;
          try {
            boolean done = false;
            while (!done) {
              int tag = input.readTag();
              switch (tag) {
                case 0:
                  done = true;
                  break;
                case 13:
                  this.bitField0_ |= 0x1;
                  this.x_ = input.readFloat();
                  break;
                case 21:
                  this.bitField0_ |= 0x2;
                  this.y_ = input.readFloat();
                  break;
                case 29:
                  this.bitField0_ |= 0x4;
                  this.z_ = input.readFloat();
                  break;
              } 
            } 
          } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e.setUnfinishedMessage(this));
          } catch (IOException e) {
            throw new RuntimeException((new InvalidProtocolBufferException(e
                  
                  .getMessage())).setUnfinishedMessage(this));
          } finally {}
        case GET_DEFAULT_INSTANCE:
          return DEFAULT_INSTANCE;
        case GET_PARSER:
          if (PARSER == null)
            synchronized (Landmark.class) {
              if (PARSER == null)
                PARSER = (Parser<Landmark>)new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE); 
            }  
          return PARSER;
      } 
      throw new UnsupportedOperationException();
    }
    
    private static final Landmark DEFAULT_INSTANCE = new Landmark();
    
    private static volatile Parser<Landmark> PARSER;
    
    static {
      DEFAULT_INSTANCE.makeImmutable();
    }
    
    public static Landmark getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }
    
    public static Parser<Landmark> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }
  
  public static final class LandmarkList extends GeneratedMessageLite<LandmarkList, LandmarkList.Builder> implements LandmarkListOrBuilder {
    public static final int LANDMARK_FIELD_NUMBER = 1;
    
    private Internal.ProtobufList<LandmarkProto.Landmark> landmark_ = emptyProtobufList();
    
    public List<LandmarkProto.Landmark> getLandmarkList() {
      return (List<LandmarkProto.Landmark>)this.landmark_;
    }
    
    public List<? extends LandmarkProto.LandmarkOrBuilder> getLandmarkOrBuilderList() {
      return (List)this.landmark_;
    }
    
    public int getLandmarkCount() {
      return this.landmark_.size();
    }
    
    public LandmarkProto.Landmark getLandmark(int index) {
      return (LandmarkProto.Landmark)this.landmark_.get(index);
    }
    
    public LandmarkProto.LandmarkOrBuilder getLandmarkOrBuilder(int index) {
      return (LandmarkProto.LandmarkOrBuilder)this.landmark_.get(index);
    }
    
    private void ensureLandmarkIsMutable() {
      if (!this.landmark_.isModifiable())
        this
          .landmark_ = GeneratedMessageLite.mutableCopy(this.landmark_); 
    }
    
    private void setLandmark(int index, LandmarkProto.Landmark value) {
      if (value == null)
        throw new NullPointerException(); 
      ensureLandmarkIsMutable();
      this.landmark_.set(index, value);
    }
    
    private void setLandmark(int index, LandmarkProto.Landmark.Builder builderForValue) {
      ensureLandmarkIsMutable();
      this.landmark_.set(index, builderForValue.build());
    }
    
    private void addLandmark(LandmarkProto.Landmark value) {
      if (value == null)
        throw new NullPointerException(); 
      ensureLandmarkIsMutable();
      this.landmark_.add(value);
    }
    
    private void addLandmark(int index, LandmarkProto.Landmark value) {
      if (value == null)
        throw new NullPointerException(); 
      ensureLandmarkIsMutable();
      this.landmark_.add(index, value);
    }
    
    private void addLandmark(LandmarkProto.Landmark.Builder builderForValue) {
      ensureLandmarkIsMutable();
      this.landmark_.add(builderForValue.build());
    }
    
    private void addLandmark(int index, LandmarkProto.Landmark.Builder builderForValue) {
      ensureLandmarkIsMutable();
      this.landmark_.add(index, builderForValue.build());
    }
    
    private void addAllLandmark(Iterable<? extends LandmarkProto.Landmark> values) {
      ensureLandmarkIsMutable();
      AbstractMessageLite.addAll(values, (Collection)this.landmark_);
    }
    
    private void clearLandmark() {
      this.landmark_ = emptyProtobufList();
    }
    
    private void removeLandmark(int index) {
      ensureLandmarkIsMutable();
      this.landmark_.remove(index);
    }
    
    public void writeTo(CodedOutputStream output) throws IOException {
      for (int i = 0; i < this.landmark_.size(); i++)
        output.writeMessage(1, (MessageLite)this.landmark_.get(i)); 
      this.unknownFields.writeTo(output);
    }
    
    public int getSerializedSize() {
      int size = this.memoizedSerializedSize;
      if (size != -1)
        return size; 
      size = 0;
      for (int i = 0; i < this.landmark_.size(); i++)
        size += 
          CodedOutputStream.computeMessageSize(1, (MessageLite)this.landmark_.get(i)); 
      size += this.unknownFields.getSerializedSize();
      this.memoizedSerializedSize = size;
      return size;
    }
    
    public static LandmarkList parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return (LandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }
    
    public static LandmarkList parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return (LandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }
    
    public static LandmarkList parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return (LandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }
    
    public static LandmarkList parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return (LandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }
    
    public static LandmarkList parseFrom(InputStream input) throws IOException {
      return (LandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }
    
    public static LandmarkList parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return (LandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    
    public static LandmarkList parseDelimitedFrom(InputStream input) throws IOException {
      return (LandmarkList)parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    
    public static LandmarkList parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return (LandmarkList)parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    
    public static LandmarkList parseFrom(CodedInputStream input) throws IOException {
      return (LandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }
    
    public static LandmarkList parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return (LandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    
    public static Builder newBuilder() {
      return (Builder)DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(LandmarkList prototype) {
      return (Builder)((Builder)DEFAULT_INSTANCE.toBuilder()).mergeFrom(prototype);
    }
    
    public static final class Builder extends GeneratedMessageLite.Builder<LandmarkList, Builder> implements LandmarkProto.LandmarkListOrBuilder {
      private Builder() {
        super(LandmarkProto.LandmarkList.DEFAULT_INSTANCE);
      }
      
      public List<LandmarkProto.Landmark> getLandmarkList() {
        return Collections.unmodifiableList(((LandmarkProto.LandmarkList)this.instance)
            .getLandmarkList());
      }
      
      public int getLandmarkCount() {
        return ((LandmarkProto.LandmarkList)this.instance).getLandmarkCount();
      }
      
      public LandmarkProto.Landmark getLandmark(int index) {
        return ((LandmarkProto.LandmarkList)this.instance).getLandmark(index);
      }
      
      public Builder setLandmark(int index, LandmarkProto.Landmark value) {
        copyOnWrite();
        ((LandmarkProto.LandmarkList)this.instance).setLandmark(index, value);
        return this;
      }
      
      public Builder setLandmark(int index, LandmarkProto.Landmark.Builder builderForValue) {
        copyOnWrite();
        ((LandmarkProto.LandmarkList)this.instance).setLandmark(index, builderForValue);
        return this;
      }
      
      public Builder addLandmark(LandmarkProto.Landmark value) {
        copyOnWrite();
        ((LandmarkProto.LandmarkList)this.instance).addLandmark(value);
        return this;
      }
      
      public Builder addLandmark(int index, LandmarkProto.Landmark value) {
        copyOnWrite();
        ((LandmarkProto.LandmarkList)this.instance).addLandmark(index, value);
        return this;
      }
      
      public Builder addLandmark(LandmarkProto.Landmark.Builder builderForValue) {
        copyOnWrite();
        ((LandmarkProto.LandmarkList)this.instance).addLandmark(builderForValue);
        return this;
      }
      
      public Builder addLandmark(int index, LandmarkProto.Landmark.Builder builderForValue) {
        copyOnWrite();
        ((LandmarkProto.LandmarkList)this.instance).addLandmark(index, builderForValue);
        return this;
      }
      
      public Builder addAllLandmark(Iterable<? extends LandmarkProto.Landmark> values) {
        copyOnWrite();
        ((LandmarkProto.LandmarkList)this.instance).addAllLandmark(values);
        return this;
      }
      
      public Builder clearLandmark() {
        copyOnWrite();
        ((LandmarkProto.LandmarkList)this.instance).clearLandmark();
        return this;
      }
      
      public Builder removeLandmark(int index) {
        copyOnWrite();
        ((LandmarkProto.LandmarkList)this.instance).removeLandmark(index);
        return this;
      }
    }
    
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
      GeneratedMessageLite.Visitor visitor;
      CodedInputStream input;
      LandmarkList other;
      ExtensionRegistryLite extensionRegistry;
      switch (method) {
        case NEW_MUTABLE_INSTANCE:
          return new LandmarkList();
        case IS_INITIALIZED:
          return DEFAULT_INSTANCE;
        case MAKE_IMMUTABLE:
          this.landmark_.makeImmutable();
          return null;
        case NEW_BUILDER:
          return new Builder();
        case VISIT:
          visitor = (GeneratedMessageLite.Visitor)arg0;
          other = (LandmarkList)arg1;
          this.landmark_ = visitor.visitList(this.landmark_, other.landmark_);
          if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE);
          return this;
        case MERGE_FROM_STREAM:
          input = (CodedInputStream)arg0;
          extensionRegistry = (ExtensionRegistryLite)arg1;
          try {
            boolean done = false;
            while (!done) {
              int tag = input.readTag();
              switch (tag) {
                case 0:
                  done = true;
                  break;
                case 10:
                  if (!this.landmark_.isModifiable())
                    this
                      .landmark_ = GeneratedMessageLite.mutableCopy(this.landmark_); 
                  this.landmark_.add(input
                      .readMessage(LandmarkProto.Landmark.parser(), extensionRegistry));
                  break;
              } 
            } 
          } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e.setUnfinishedMessage(this));
          } catch (IOException e) {
            throw new RuntimeException((new InvalidProtocolBufferException(e
                  
                  .getMessage())).setUnfinishedMessage(this));
          } finally {}
        case GET_DEFAULT_INSTANCE:
          return DEFAULT_INSTANCE;
        case GET_PARSER:
          if (PARSER == null)
            synchronized (LandmarkList.class) {
              if (PARSER == null)
                PARSER = (Parser<LandmarkList>)new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE); 
            }  
          return PARSER;
      } 
      throw new UnsupportedOperationException();
    }
    
    private static final LandmarkList DEFAULT_INSTANCE = new LandmarkList();
    
    private static volatile Parser<LandmarkList> PARSER;
    
    static {
      DEFAULT_INSTANCE.makeImmutable();
    }
    
    public static LandmarkList getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }
    
    public static Parser<LandmarkList> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }
  
  public static final class NormalizedLandmark extends GeneratedMessageLite<NormalizedLandmark, NormalizedLandmark.Builder> implements NormalizedLandmarkOrBuilder {
    private int bitField0_;
    
    public static final int X_FIELD_NUMBER = 1;
    
    private float x_;
    
    public static final int Y_FIELD_NUMBER = 2;
    
    private float y_;
    
    public static final int Z_FIELD_NUMBER = 3;
    
    private float z_;
    
    public boolean hasX() {
      return ((this.bitField0_ & 0x1) == 1);
    }
    
    public float getX() {
      return this.x_;
    }
    
    private void setX(float value) {
      this.bitField0_ |= 0x1;
      this.x_ = value;
    }
    
    private void clearX() {
      this.bitField0_ &= 0xFFFFFFFE;
      this.x_ = 0.0F;
    }
    
    public boolean hasY() {
      return ((this.bitField0_ & 0x2) == 2);
    }
    
    public float getY() {
      return this.y_;
    }
    
    private void setY(float value) {
      this.bitField0_ |= 0x2;
      this.y_ = value;
    }
    
    private void clearY() {
      this.bitField0_ &= 0xFFFFFFFD;
      this.y_ = 0.0F;
    }
    
    public boolean hasZ() {
      return ((this.bitField0_ & 0x4) == 4);
    }
    
    public float getZ() {
      return this.z_;
    }
    
    private void setZ(float value) {
      this.bitField0_ |= 0x4;
      this.z_ = value;
    }
    
    private void clearZ() {
      this.bitField0_ &= 0xFFFFFFFB;
      this.z_ = 0.0F;
    }
    
    public void writeTo(CodedOutputStream output) throws IOException {
      if ((this.bitField0_ & 0x1) == 1)
        output.writeFloat(1, this.x_); 
      if ((this.bitField0_ & 0x2) == 2)
        output.writeFloat(2, this.y_); 
      if ((this.bitField0_ & 0x4) == 4)
        output.writeFloat(3, this.z_); 
      this.unknownFields.writeTo(output);
    }
    
    public int getSerializedSize() {
      int size = this.memoizedSerializedSize;
      if (size != -1)
        return size; 
      size = 0;
      if ((this.bitField0_ & 0x1) == 1)
        size += 
          CodedOutputStream.computeFloatSize(1, this.x_); 
      if ((this.bitField0_ & 0x2) == 2)
        size += 
          CodedOutputStream.computeFloatSize(2, this.y_); 
      if ((this.bitField0_ & 0x4) == 4)
        size += 
          CodedOutputStream.computeFloatSize(3, this.z_); 
      size += this.unknownFields.getSerializedSize();
      this.memoizedSerializedSize = size;
      return size;
    }
    
    public static NormalizedLandmark parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return (NormalizedLandmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }
    
    public static NormalizedLandmark parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return (NormalizedLandmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }
    
    public static NormalizedLandmark parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return (NormalizedLandmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }
    
    public static NormalizedLandmark parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return (NormalizedLandmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }
    
    public static NormalizedLandmark parseFrom(InputStream input) throws IOException {
      return (NormalizedLandmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }
    
    public static NormalizedLandmark parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return (NormalizedLandmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    
    public static NormalizedLandmark parseDelimitedFrom(InputStream input) throws IOException {
      return (NormalizedLandmark)parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    
    public static NormalizedLandmark parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return (NormalizedLandmark)parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    
    public static NormalizedLandmark parseFrom(CodedInputStream input) throws IOException {
      return (NormalizedLandmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }
    
    public static NormalizedLandmark parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return (NormalizedLandmark)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    
    public static Builder newBuilder() {
      return (Builder)DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(NormalizedLandmark prototype) {
      return (Builder)((Builder)DEFAULT_INSTANCE.toBuilder()).mergeFrom(prototype);
    }
    
    public static final class Builder extends GeneratedMessageLite.Builder<NormalizedLandmark, Builder> implements LandmarkProto.NormalizedLandmarkOrBuilder {
      private Builder() {
        super(LandmarkProto.NormalizedLandmark.DEFAULT_INSTANCE);
      }
      
      public boolean hasX() {
        return ((LandmarkProto.NormalizedLandmark)this.instance).hasX();
      }
      
      public float getX() {
        return ((LandmarkProto.NormalizedLandmark)this.instance).getX();
      }
      
      public Builder setX(float value) {
        copyOnWrite();
        ((LandmarkProto.NormalizedLandmark)this.instance).setX(value);
        return this;
      }
      
      public Builder clearX() {
        copyOnWrite();
        ((LandmarkProto.NormalizedLandmark)this.instance).clearX();
        return this;
      }
      
      public boolean hasY() {
        return ((LandmarkProto.NormalizedLandmark)this.instance).hasY();
      }
      
      public float getY() {
        return ((LandmarkProto.NormalizedLandmark)this.instance).getY();
      }
      
      public Builder setY(float value) {
        copyOnWrite();
        ((LandmarkProto.NormalizedLandmark)this.instance).setY(value);
        return this;
      }
      
      public Builder clearY() {
        copyOnWrite();
        ((LandmarkProto.NormalizedLandmark)this.instance).clearY();
        return this;
      }
      
      public boolean hasZ() {
        return ((LandmarkProto.NormalizedLandmark)this.instance).hasZ();
      }
      
      public float getZ() {
        return ((LandmarkProto.NormalizedLandmark)this.instance).getZ();
      }
      
      public Builder setZ(float value) {
        copyOnWrite();
        ((LandmarkProto.NormalizedLandmark)this.instance).setZ(value);
        return this;
      }
      
      public Builder clearZ() {
        copyOnWrite();
        ((LandmarkProto.NormalizedLandmark)this.instance).clearZ();
        return this;
      }
    }
    
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
      GeneratedMessageLite.Visitor visitor;
      CodedInputStream input;
      NormalizedLandmark other;
      ExtensionRegistryLite extensionRegistry;
      switch (method) {
        case NEW_MUTABLE_INSTANCE:
          return new NormalizedLandmark();
        case IS_INITIALIZED:
          return DEFAULT_INSTANCE;
        case MAKE_IMMUTABLE:
          return null;
        case NEW_BUILDER:
          return new Builder();
        case VISIT:
          visitor = (GeneratedMessageLite.Visitor)arg0;
          other = (NormalizedLandmark)arg1;
          this.x_ = visitor.visitFloat(
              hasX(), this.x_, other
              .hasX(), other.x_);
          this.y_ = visitor.visitFloat(
              hasY(), this.y_, other
              .hasY(), other.y_);
          this.z_ = visitor.visitFloat(
              hasZ(), this.z_, other
              .hasZ(), other.z_);
          if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE)
            this.bitField0_ |= other.bitField0_; 
          return this;
        case MERGE_FROM_STREAM:
          input = (CodedInputStream)arg0;
          extensionRegistry = (ExtensionRegistryLite)arg1;
          try {
            boolean done = false;
            while (!done) {
              int tag = input.readTag();
              switch (tag) {
                case 0:
                  done = true;
                  break;
                case 13:
                  this.bitField0_ |= 0x1;
                  this.x_ = input.readFloat();
                  break;
                case 21:
                  this.bitField0_ |= 0x2;
                  this.y_ = input.readFloat();
                  break;
                case 29:
                  this.bitField0_ |= 0x4;
                  this.z_ = input.readFloat();
                  break;
              } 
            } 
          } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e.setUnfinishedMessage(this));
          } catch (IOException e) {
            throw new RuntimeException((new InvalidProtocolBufferException(e
                  
                  .getMessage())).setUnfinishedMessage(this));
          } finally {}
        case GET_DEFAULT_INSTANCE:
          return DEFAULT_INSTANCE;
        case GET_PARSER:
          if (PARSER == null)
            synchronized (NormalizedLandmark.class) {
              if (PARSER == null)
                PARSER = (Parser<NormalizedLandmark>)new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE); 
            }  
          return PARSER;
      } 
      throw new UnsupportedOperationException();
    }
    
    private static final NormalizedLandmark DEFAULT_INSTANCE = new NormalizedLandmark();
    
    private static volatile Parser<NormalizedLandmark> PARSER;
    
    static {
      DEFAULT_INSTANCE.makeImmutable();
    }
    
    public static NormalizedLandmark getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }
    
    public static Parser<NormalizedLandmark> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }
  
  public static final class NormalizedLandmarkList extends GeneratedMessageLite<NormalizedLandmarkList, NormalizedLandmarkList.Builder> implements NormalizedLandmarkListOrBuilder {
    public static final int LANDMARK_FIELD_NUMBER = 1;
    
    private Internal.ProtobufList<LandmarkProto.NormalizedLandmark> landmark_ = emptyProtobufList();
    
    public List<LandmarkProto.NormalizedLandmark> getLandmarkList() {
      return (List<LandmarkProto.NormalizedLandmark>)this.landmark_;
    }
    
    public List<? extends LandmarkProto.NormalizedLandmarkOrBuilder> getLandmarkOrBuilderList() {
      return (List)this.landmark_;
    }
    
    public int getLandmarkCount() {
      return this.landmark_.size();
    }
    
    public LandmarkProto.NormalizedLandmark getLandmark(int index) {
      return (LandmarkProto.NormalizedLandmark)this.landmark_.get(index);
    }
    
    public LandmarkProto.NormalizedLandmarkOrBuilder getLandmarkOrBuilder(int index) {
      return (LandmarkProto.NormalizedLandmarkOrBuilder)this.landmark_.get(index);
    }
    
    private void ensureLandmarkIsMutable() {
      if (!this.landmark_.isModifiable())
        this
          .landmark_ = GeneratedMessageLite.mutableCopy(this.landmark_); 
    }
    
    private void setLandmark(int index, LandmarkProto.NormalizedLandmark value) {
      if (value == null)
        throw new NullPointerException(); 
      ensureLandmarkIsMutable();
      this.landmark_.set(index, value);
    }
    
    private void setLandmark(int index, LandmarkProto.NormalizedLandmark.Builder builderForValue) {
      ensureLandmarkIsMutable();
      this.landmark_.set(index, builderForValue.build());
    }
    
    private void addLandmark(LandmarkProto.NormalizedLandmark value) {
      if (value == null)
        throw new NullPointerException(); 
      ensureLandmarkIsMutable();
      this.landmark_.add(value);
    }
    
    private void addLandmark(int index, LandmarkProto.NormalizedLandmark value) {
      if (value == null)
        throw new NullPointerException(); 
      ensureLandmarkIsMutable();
      this.landmark_.add(index, value);
    }
    
    private void addLandmark(LandmarkProto.NormalizedLandmark.Builder builderForValue) {
      ensureLandmarkIsMutable();
      this.landmark_.add(builderForValue.build());
    }
    
    private void addLandmark(int index, LandmarkProto.NormalizedLandmark.Builder builderForValue) {
      ensureLandmarkIsMutable();
      this.landmark_.add(index, builderForValue.build());
    }
    
    private void addAllLandmark(Iterable<? extends LandmarkProto.NormalizedLandmark> values) {
      ensureLandmarkIsMutable();
      AbstractMessageLite.addAll(values, (Collection)this.landmark_);
    }
    
    private void clearLandmark() {
      this.landmark_ = emptyProtobufList();
    }
    
    private void removeLandmark(int index) {
      ensureLandmarkIsMutable();
      this.landmark_.remove(index);
    }
    
    public void writeTo(CodedOutputStream output) throws IOException {
      for (int i = 0; i < this.landmark_.size(); i++)
        output.writeMessage(1, (MessageLite)this.landmark_.get(i)); 
      this.unknownFields.writeTo(output);
    }
    
    public int getSerializedSize() {
      int size = this.memoizedSerializedSize;
      if (size != -1)
        return size; 
      size = 0;
      for (int i = 0; i < this.landmark_.size(); i++)
        size += 
          CodedOutputStream.computeMessageSize(1, (MessageLite)this.landmark_.get(i)); 
      size += this.unknownFields.getSerializedSize();
      this.memoizedSerializedSize = size;
      return size;
    }
    
    public static NormalizedLandmarkList parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return (NormalizedLandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }
    
    public static NormalizedLandmarkList parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return (NormalizedLandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }
    
    public static NormalizedLandmarkList parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return (NormalizedLandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }
    
    public static NormalizedLandmarkList parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return (NormalizedLandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }
    
    public static NormalizedLandmarkList parseFrom(InputStream input) throws IOException {
      return (NormalizedLandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }
    
    public static NormalizedLandmarkList parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return (NormalizedLandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    
    public static NormalizedLandmarkList parseDelimitedFrom(InputStream input) throws IOException {
      return (NormalizedLandmarkList)parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    
    public static NormalizedLandmarkList parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return (NormalizedLandmarkList)parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    
    public static NormalizedLandmarkList parseFrom(CodedInputStream input) throws IOException {
      return (NormalizedLandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }
    
    public static NormalizedLandmarkList parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return (NormalizedLandmarkList)GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    
    public static Builder newBuilder() {
      return (Builder)DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(NormalizedLandmarkList prototype) {
      return (Builder)((Builder)DEFAULT_INSTANCE.toBuilder()).mergeFrom(prototype);
    }
    
    public static final class Builder extends GeneratedMessageLite.Builder<NormalizedLandmarkList, Builder> implements LandmarkProto.NormalizedLandmarkListOrBuilder {
      private Builder() {
        super(LandmarkProto.NormalizedLandmarkList.DEFAULT_INSTANCE);
      }
      
      public List<LandmarkProto.NormalizedLandmark> getLandmarkList() {
        return Collections.unmodifiableList(((LandmarkProto.NormalizedLandmarkList)this.instance)
            .getLandmarkList());
      }
      
      public int getLandmarkCount() {
        return ((LandmarkProto.NormalizedLandmarkList)this.instance).getLandmarkCount();
      }
      
      public LandmarkProto.NormalizedLandmark getLandmark(int index) {
        return ((LandmarkProto.NormalizedLandmarkList)this.instance).getLandmark(index);
      }
      
      public Builder setLandmark(int index, LandmarkProto.NormalizedLandmark value) {
        copyOnWrite();
        ((LandmarkProto.NormalizedLandmarkList)this.instance).setLandmark(index, value);
        return this;
      }
      
      public Builder setLandmark(int index, LandmarkProto.NormalizedLandmark.Builder builderForValue) {
        copyOnWrite();
        ((LandmarkProto.NormalizedLandmarkList)this.instance).setLandmark(index, builderForValue);
        return this;
      }
      
      public Builder addLandmark(LandmarkProto.NormalizedLandmark value) {
        copyOnWrite();
        ((LandmarkProto.NormalizedLandmarkList)this.instance).addLandmark(value);
        return this;
      }
      
      public Builder addLandmark(int index, LandmarkProto.NormalizedLandmark value) {
        copyOnWrite();
        ((LandmarkProto.NormalizedLandmarkList)this.instance).addLandmark(index, value);
        return this;
      }
      
      public Builder addLandmark(LandmarkProto.NormalizedLandmark.Builder builderForValue) {
        copyOnWrite();
        ((LandmarkProto.NormalizedLandmarkList)this.instance).addLandmark(builderForValue);
        return this;
      }
      
      public Builder addLandmark(int index, LandmarkProto.NormalizedLandmark.Builder builderForValue) {
        copyOnWrite();
        ((LandmarkProto.NormalizedLandmarkList)this.instance).addLandmark(index, builderForValue);
        return this;
      }
      
      public Builder addAllLandmark(Iterable<? extends LandmarkProto.NormalizedLandmark> values) {
        copyOnWrite();
        ((LandmarkProto.NormalizedLandmarkList)this.instance).addAllLandmark(values);
        return this;
      }
      
      public Builder clearLandmark() {
        copyOnWrite();
        ((LandmarkProto.NormalizedLandmarkList)this.instance).clearLandmark();
        return this;
      }
      
      public Builder removeLandmark(int index) {
        copyOnWrite();
        ((LandmarkProto.NormalizedLandmarkList)this.instance).removeLandmark(index);
        return this;
      }
    }
    
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
      GeneratedMessageLite.Visitor visitor;
      CodedInputStream input;
      NormalizedLandmarkList other;
      ExtensionRegistryLite extensionRegistry;
      switch (method) {
        case NEW_MUTABLE_INSTANCE:
          return new NormalizedLandmarkList();
        case IS_INITIALIZED:
          return DEFAULT_INSTANCE;
        case MAKE_IMMUTABLE:
          this.landmark_.makeImmutable();
          return null;
        case NEW_BUILDER:
          return new Builder();
        case VISIT:
          visitor = (GeneratedMessageLite.Visitor)arg0;
          other = (NormalizedLandmarkList)arg1;
          this.landmark_ = visitor.visitList(this.landmark_, other.landmark_);
          if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE);
          return this;
        case MERGE_FROM_STREAM:
          input = (CodedInputStream)arg0;
          extensionRegistry = (ExtensionRegistryLite)arg1;
          try {
            boolean done = false;
            while (!done) {
              int tag = input.readTag();
              switch (tag) {
                case 0:
                  done = true;
                  break;
                case 10:
                  if (!this.landmark_.isModifiable())
                    this
                      .landmark_ = GeneratedMessageLite.mutableCopy(this.landmark_); 
                  this.landmark_.add(input
                      .readMessage(LandmarkProto.NormalizedLandmark.parser(), extensionRegistry));
                  break;
              } 
            } 
          } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e.setUnfinishedMessage(this));
          } catch (IOException e) {
            throw new RuntimeException((new InvalidProtocolBufferException(e
                  
                  .getMessage())).setUnfinishedMessage(this));
          } finally {}
        case GET_DEFAULT_INSTANCE:
          return DEFAULT_INSTANCE;
        case GET_PARSER:
          if (PARSER == null)
            synchronized (NormalizedLandmarkList.class) {
              if (PARSER == null)
                PARSER = (Parser<NormalizedLandmarkList>)new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE); 
            }  
          return PARSER;
      } 
      throw new UnsupportedOperationException();
    }
    
    private static final NormalizedLandmarkList DEFAULT_INSTANCE = new NormalizedLandmarkList();
    
    private static volatile Parser<NormalizedLandmarkList> PARSER;
    
    static {
      DEFAULT_INSTANCE.makeImmutable();
    }
    
    public static NormalizedLandmarkList getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }
    
    public static Parser<NormalizedLandmarkList> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }
  
  static {
  
  }
  
  public static interface NormalizedLandmarkListOrBuilder extends MessageLiteOrBuilder {
    List<LandmarkProto.NormalizedLandmark> getLandmarkList();
    
    LandmarkProto.NormalizedLandmark getLandmark(int param1Int);
    
    int getLandmarkCount();
  }
  
  public static interface NormalizedLandmarkOrBuilder extends MessageLiteOrBuilder {
    boolean hasX();
    
    float getX();
    
    boolean hasY();
    
    float getY();
    
    boolean hasZ();
    
    float getZ();
  }
  
  public static interface LandmarkListOrBuilder extends MessageLiteOrBuilder {
    List<LandmarkProto.Landmark> getLandmarkList();
    
    LandmarkProto.Landmark getLandmark(int param1Int);
    
    int getLandmarkCount();
  }
  
  public static interface LandmarkOrBuilder extends MessageLiteOrBuilder {
    boolean hasX();
    
    float getX();
    
    boolean hasY();
    
    float getY();
    
    boolean hasZ();
    
    float getZ();
  }
}
