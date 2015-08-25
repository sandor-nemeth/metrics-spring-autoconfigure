/**
 * Copyright (C) 2015 Sandor Nemeth (sandor.nemeth.1986@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.sandornemeth.metrics.spring.autoconfigure;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.jvm.BufferPoolMetricSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

/**
 * {@link org.springframework.boot.autoconfigure.EnableAutoConfiguration Auto-configuration} class
 * for configuring the metrics-spring library.
 */
@Configuration
@ConditionalOnClass({MetricRegistry.class})
@Import(MetricsAnnotationConfiguration.class)
@EnableConfigurationProperties(MetricsConfigurationProperties.class)
public class MetricsAutoConfiguration {

  @Configuration
  protected static class ReporterConfigurer extends MetricsConfigurerAdapter {

    @Autowired
    private MetricsConfigurationProperties metricsConfigurationProperties;

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
      configureConsoleReporters(metricRegistry);
      configureCsvReporters(metricRegistry);
      configureSlf4jReporters(metricRegistry);
    }

    private void configureConsoleReporters(MetricRegistry metricRegistry) {
      metricsConfigurationProperties.getReporters().getConsole().forEach(r -> {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
            .convertDurationsTo(r.getDurationUnit())
            .convertRatesTo(r.getRateUnit())
            .filter(MetricFilter.ALL)
            .build();
        this.registerReporter(reporter).start(r.getReportInterval(), r.getReportIntervalUnit());
      });
    }

    private void configureCsvReporters(MetricRegistry metricRegistry) {
      metricsConfigurationProperties.getReporters().getCsv().forEach(r -> {
        CsvReporter reporter = CsvReporter.forRegistry(metricRegistry)
            .filter(MetricFilter.ALL)
            .convertDurationsTo(r.getDurationUnit())
            .convertRatesTo(r.getRateUnit())
            .formatFor(r.getFormatFor())
            .build(new File(r.getReportFolder()));
        this.registerReporter(reporter).start(r.getReportInterval(), r.getReportIntervalUnit());
      });
    }

    private void configureSlf4jReporters(MetricRegistry metricRegistry) {
      metricsConfigurationProperties.getReporters().getSlf4j().forEach(r -> {
        Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
            .convertDurationsTo(r.getDurationUnit())
            .convertRatesTo(r.getRateUnit())
            .outputTo(r.getLogger())
            .withLoggingLevel(r.getLoggingLevel())
            .markWith(r.getMarker())
            .prefixedWith(r.getPrefix())
            .build();
        this.registerReporter(reporter).start(r.getReportInterval(), r.getReportIntervalUnit());
      });
    }
  }

  /**
   * Configuration for metric sets.
   */
  @Configuration
  protected static class MetricSetConfiguration {

    @Autowired
    private MetricRegistry metricRegistry;

    private List<MetricSetConfigurer> metricSetConfigurers = new ArrayList<>();

    @Autowired(required = false)
    public void setMetricSetConfigurers(Collection<MetricSetConfigurer> metricSetConfigurers) {
      if (null != metricSetConfigurers) {
        this.metricSetConfigurers.addAll(metricSetConfigurers);
      }
    }

    @PostConstruct
    public void initializeMetricSets() {
      this.metricSetConfigurers.forEach(c -> c.configureMetricSets(this.metricRegistry));
    }
  }

  /**
   *
   */
  @Configuration
  @ConditionalOnClass(MemoryUsageGaugeSet.class)
  @ConditionalOnProperty(prefix = "spring.metrics.jvm", name = "enabled", matchIfMissing = true)
  protected static class JvmMetricSetConfiguration implements MetricSetConfigurer {

    @Override
    public void configureMetricSets(MetricRegistry metricRegistry) {
      metricRegistry.register("gc", new GarbageCollectorMetricSet());
      metricRegistry.register("buffers",
                              new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));
      metricRegistry.register("memory", new MemoryUsageGaugeSet());
      metricRegistry.register("threads", new ThreadStatesGaugeSet());
    }
  }
}
