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
package com.sandornemeth.metrics.spring.autoconfigure;

import com.sandornemeth.metrics.spring.autoconfigure.outputcaptor.OutputCaptor;
import com.sandornemeth.metrics.spring.autoconfigure.outputcaptor.StdoutCaptor;

import org.junit.Test;

import static org.junit.Assert.assertThat;

public class ConsoleReporterTest extends ReporterTestCaseBase {

  @Test
  public void runConsoleReporterTest() throws Exception {
    executeReporterTest(200,
                        "spring.metrics.reporters.console[0].enabled:true",
                        "spring.metrics.reporters.console[0].rateUnit:SECONDS",
                        "spring.metrics.reporters.console[0].durationUnit:MILLISECONDS",
                        "spring.metrics.reporters.console[0].reportInterval:100",
                        "spring.metrics.reporters.console[0].reportIntervalUnit:MILLISECONDS");
  }


  @Override
  OutputCaptor getOutputCaptor() {
    return new StdoutCaptor();
  }
}
