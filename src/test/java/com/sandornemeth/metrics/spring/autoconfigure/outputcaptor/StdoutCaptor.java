/**
 * Copyright (C) 2015 Sandor Nemeth (sandor.nemeth.1986@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sandornemeth.metrics.spring.autoconfigure.outputcaptor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StdoutCaptor implements OutputCaptor {

  private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private PrintStream originalOutputStream;

  @Override
  public void start() {
    originalOutputStream = System.out;
    System.setOut(new PrintStream(outputStream));
  }

  @Override
  public String get() {
    return outputStream.toString();
  }

  @Override
  public void close() throws Exception {
    System.setOut(originalOutputStream);
    outputStream.close();
  }
}
