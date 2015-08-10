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

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author sandornemeth
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.metrics.reporters.console", name = "enabled")
@EnableConfigurationProperties({ConsoleReporterProperties.class})
class ConsoleReporterConfiguration extends MetricsConfigurerAdapter {

  @Autowired
  private ConsoleReporterProperties consoleReporterProperties;

  @Override
  public void configureReporters(MetricRegistry metricRegistry) {
    ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(metricRegistry)
        .convertRatesTo(consoleReporterProperties.getRateUnit())
        .convertDurationsTo(consoleReporterProperties.getDurationUnit())
        .build();
    registerReporter(consoleReporter).start(consoleReporterProperties.getReportInterval(),
                                            consoleReporterProperties.getReportIntervalUnit());
  }
}
