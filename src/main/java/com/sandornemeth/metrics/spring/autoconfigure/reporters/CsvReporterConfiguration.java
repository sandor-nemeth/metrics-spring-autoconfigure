package com.sandornemeth.metrics.spring.autoconfigure.reporters;

import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.metrics.reporters.csv", name = "enabled")
@EnableConfigurationProperties(CsvReporterProperties.class)
public class CsvReporterConfiguration extends MetricsConfigurerAdapter {

  @Autowired
  private CsvReporterProperties csvReporterProperties;

  @Override
  public void configureReporters(MetricRegistry metricRegistry) {
    CsvReporter csvReporter = null;
    try {
      csvReporter = CsvReporter.forRegistry(metricRegistry)
          .convertDurationsTo(csvReporterProperties.getDurationUnit())
          .convertRatesTo(csvReporterProperties.getRateUnit())
          .filter(MetricFilter.ALL)
          .formatFor(csvReporterProperties.getFormatFor())
          .build(csvReporterProperties.getFile().getFile());
      registerReporter(csvReporter).start(csvReporterProperties.getReportInterval(),
                                          csvReporterProperties.getReportIntervalUnit());
    } catch (IOException e) {
      throw new ExceptionInInitializerError(e);
    }

  }
}
