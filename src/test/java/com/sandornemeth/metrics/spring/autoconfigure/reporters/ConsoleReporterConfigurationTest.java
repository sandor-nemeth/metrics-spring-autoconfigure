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
package com.sandornemeth.metrics.spring.autoconfigure.reporters;

import com.sandornemeth.metrics.spring.autoconfigure.AbstractMetricsAutoconfigurationTestSupport;

import com.codahale.metrics.MetricRegistry;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

public class ConsoleReporterConfigurationTest extends AbstractMetricsAutoconfigurationTestSupport {

  @Test
  public void testConsoleReporterIsNotLoaded() throws Exception {
    this.load(ConsoleReporterTestConfiguration.class,
              "spring.metrics.reporters.console.enabled=true",
              "spring.metrics.reporters.console.rateUnit=SECONDS",
              "spring.metrics.reporters.console.durationUnit=MILLISECONDS",
              "spring.metrics.reporters.console.reportInterval=1",
              "spring.metrics.reporters.console.reportIntervalUnit=SECONDS"
              );

    MetricRegistry metricRegistry = getMetricRegistry();
  }

  @Configuration
  protected static class ConsoleReporterTestConfiguration {

  }

}
