/*
 * Copyright 2011-2018 GatlingCorp (http://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.gatling.http.client.body.part.impl;

import io.gatling.http.client.body.part.ByteArrayPart;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;

public class ByteArrayPartImpl extends FileLikePartImpl<ByteArrayPart> {

  private final ByteBuf contentBuffer;

  public ByteArrayPartImpl(ByteArrayPart part, byte[] boundary) {
    super(part, boundary);
    contentBuffer = Unpooled.wrappedBuffer(part.getContent());
  }

  @Override
  protected long getContentLength() {
    return part.getContent().length;
  }

  @Override
  protected long copyContentInto(ByteBuf target) {
    return copyInto(contentBuffer, target, PartImplState.POST_CONTENT);
  }

  @Override
  protected long transferContentTo(WritableByteChannel target) throws IOException {
    return transferTo(contentBuffer, target, PartImplState.POST_CONTENT);
  }

  @Override
  public void close() {
    super.close();
    contentBuffer.release();
  }
}