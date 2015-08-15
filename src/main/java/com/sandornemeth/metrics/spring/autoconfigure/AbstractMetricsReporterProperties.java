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

import java.util.concurrent.TimeUnit;

/**
 * Abstract base class for {@link org.springframework.boot.context.properties.ConfigurationProperties
 * &#64;ConfigurationProperties} for {@link com.codahale.metrics.Reporter metrics reporters}.
 */
abstract class AbstractMetricsReporterProperties {

  private TimeUnit rateUnit = TimeUnit.SECONDS;
  private TimeUnit durationUnit = TimeUnit.MILLISECONDS;
  private long reportInterval = 1;
  private TimeUnit reportIntervalUnit = TimeUnit.SECONDS;

  public TimeUnit getRateUnit() {
    return rateUnit;
  }

  public void setRateUnit(TimeUnit rateUnit) {
    this.rateUnit = rateUnit;
  }

  public TimeUnit getDurationUnit() {
    return durationUnit;
  }

  public void setDurationUnit(TimeUnit durationUnit) {
    this.durationUnit = durationUnit;
  }

  public long getReportInterval() {
    return reportInterval;
  }

  public void setReportInterval(long reportInterval) {
    this.reportInterval = reportInterval;
  }

  public TimeUnit getReportIntervalUnit() {
    return reportIntervalUnit;
  }

  public void setReportIntervalUnit(TimeUnit reportIntervalUnit) {
    this.reportIntervalUnit = reportIntervalUnit;
  }
}
