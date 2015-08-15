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
import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.File;

/**
 *
 */
@Configuration
@Import({
    MetricsReporterConfiguration.ConsoleReporterConfiguration.class,
    MetricsReporterConfiguration.CsvReporterConfiguration.class,
    MetricsReporterConfiguration.Slf4jReporterConfiguration.class
})
class MetricsReporterConfiguration {

  /**
   * {@link MetricsConfigurerAdapter Metric configurer} for configuring an {@link
   * com.codahale.metrics.CsvReporter csv reporter}.
   */
  @Configuration
  @ConditionalOnProperty(prefix = "spring.metrics.reporters.console", name = "enabled")
  @EnableConfigurationProperties({ConsoleReporterProperties.class})
  protected static class ConsoleReporterConfiguration extends MetricsConfigurerAdapter {

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

  /**
   *
   */
  @Configuration
  @ConditionalOnProperty(prefix = "spring.metrics.reporters.csv", name = "enabled")
  @EnableConfigurationProperties(CsvReporterProperties.class)
  protected static class CsvReporterConfiguration extends MetricsConfigurerAdapter {

    @Autowired
    private CsvReporterProperties csvReporterProperties;

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
      CsvReporter csvReporter = CsvReporter.forRegistry(metricRegistry)
          .convertDurationsTo(csvReporterProperties.getDurationUnit())
          .convertRatesTo(csvReporterProperties.getRateUnit())
          .filter(MetricFilter.ALL)
          .formatFor(csvReporterProperties.getFormatFor())
          .build(new File(csvReporterProperties.getReportFolder()));
      registerReporter(csvReporter).start(csvReporterProperties.getReportInterval(),
                                          csvReporterProperties.getReportIntervalUnit());
    }
  }

  @Configuration
  @ConditionalOnProperty(prefix = "spring.metrics.reporters.slf4j", name = "enabled")
  @EnableConfigurationProperties(Slf4jReporterProperties.class)
  protected static class Slf4jReporterConfiguration extends MetricsConfigurerAdapter {

    @Autowired
    private Slf4jReporterProperties slf4jReporterProperties;

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
      Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
          .convertDurationsTo(slf4jReporterProperties.getDurationUnit())
          .convertRatesTo(slf4jReporterProperties.getRateUnit())
          .outputTo(slf4jReporterProperties.getLogger())
          .withLoggingLevel(slf4jReporterProperties.getLoggingLevel())
          .markWith(slf4jReporterProperties.getMarker())
          .prefixedWith(slf4jReporterProperties.getPrefix())
          .filter(MetricFilter.ALL)
          .build();
      registerReporter(reporter).start(slf4jReporterProperties.getReportInterval(),
                                       slf4jReporterProperties.getReportIntervalUnit());
    }
  }
}
