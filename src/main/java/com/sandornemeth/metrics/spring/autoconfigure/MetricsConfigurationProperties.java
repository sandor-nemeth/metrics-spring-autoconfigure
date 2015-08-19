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

import com.codahale.metrics.Slf4jReporter;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;

/**
 * Configuration properties for the metrics.
 */
@ConfigurationProperties("spring.metrics")
@Getter
@Setter
public class MetricsConfigurationProperties {

  private Reporters reporters;

  @Getter
  @Setter
  public static class Reporters {

    private List<ConsoleReporterProperties> console = new ArrayList<>();
    private List<CsvReporterProperties> csv = new ArrayList<>();
    private List<Slf4jReporterProperties> slf4j = new ArrayList<>();
    private List<GraphiteReporterProperties> graphite = new ArrayList<>();

  }

  @Getter
  @Setter
  public abstract static class ReporterPropertiesBase {

    private TimeUnit rateUnit = TimeUnit.SECONDS;
    private TimeUnit durationUnit = TimeUnit.MILLISECONDS;
    private long reportInterval = 1;
    private TimeUnit reportIntervalUnit = TimeUnit.SECONDS;
  }

  @Getter
  @Setter
  public static class ConsoleReporterProperties extends ReporterPropertiesBase {

  }

  @Getter
  @Setter
  public static class CsvReporterProperties extends ReporterPropertiesBase {

    private Locale formatFor = Locale.US;
    @NotBlank
    private String reportFolder;
  }

  @Getter
  @Setter
  public static class Slf4jReporterProperties extends ReporterPropertiesBase {

    private Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    private Slf4jReporter.LoggingLevel loggingLevel = Slf4jReporter.LoggingLevel.INFO;
    private Marker marker = null;
    private String prefix = "";

    public void setLogger(String logger) {
      this.logger = LoggerFactory.getLogger(logger);
    }

    public void setLoggingLevel(String loggingLevel) {
      this.loggingLevel = Slf4jReporter.LoggingLevel.valueOf(
          loggingLevel.toUpperCase(Locale.getDefault()));
    }

    public void setMarker(String marker) {
      this.marker = MarkerFactory.getMarker(marker);
    }

  }

  @Getter @Setter
  public static class GraphiteReporterProperties extends ReporterPropertiesBase {
    @NotBlank
    private String graphiteHost;
    private int graphitePort;
  }
}
