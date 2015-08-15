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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

/**
 * {@link ConfigurationProperties Configuration properties} for the {@link Slf4jReporter SLF4J
 * reporter}.
 */
@ConfigurationProperties(prefix = "spring.metrics.reporters.slf4j")
public class Slf4jReporterProperties extends AbstractMetricsReporterProperties {

  private Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
  private Slf4jReporter.LoggingLevel loggingLevel = Slf4jReporter.LoggingLevel.INFO;
  private Marker marker = null;
  private String prefix = "";

  public Logger getLogger() {
    return logger;
  }

  public void setLogger(String logger) {
    this.logger = LoggerFactory.getLogger(logger);
  }

  public Slf4jReporter.LoggingLevel getLoggingLevel() {
    return loggingLevel;
  }

  public void setLoggingLevel(String loggingLevel) {
    this.loggingLevel = Slf4jReporter.LoggingLevel.valueOf(loggingLevel.toUpperCase(Locale.getDefault()));
  }

  public Marker getMarker() {
    return marker;
  }

  public void setMarker(String marker) {
    this.marker = MarkerFactory.getMarker(marker);
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }
}
