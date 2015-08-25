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

import com.sandornemeth.metrics.spring.autoconfigure.outputcaptor.LogbackCaptor;
import com.sandornemeth.metrics.spring.autoconfigure.outputcaptor.OutputCaptor;

import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * @author sandornemeth
 */
public class Slf4jReporterTest extends ReporterTestCaseBase {

  private static final String METRIC_LOGGER_NAME = "log.metric";

  @Override
  OutputCaptor getOutputCaptor() {
    LoggerFactory.getLogger("hello"); // for some reason this is needed.
    return new LogbackCaptor(METRIC_LOGGER_NAME);
  }

  @Test
  public void runCsvReporterTest() throws Exception {
    executeReporterTest(200,
                        "spring.metrics.reporters.slf4j[0].enabled:true",
                        "spring.metrics.reporters.slf4j[0].rateUnit:SECONDS",
                        "spring.metrics.reporters.slf4j[0].durationUnit:MILLISECONDS",
                        "spring.metrics.reporters.slf4j[0].reportInterval:100",
                        "spring.metrics.reporters.slf4j[0].reportIntervalUnit:MILLISECONDS",
                        "spring.metrics.reporters.slf4j[0].prefix:prefix",
                        "spring.metrics.reporters.slf4j[0].logger:" + METRIC_LOGGER_NAME,
                        "spring.metrics.reporters.slf4j[0].loggingLevel:WARN");
  }
}
