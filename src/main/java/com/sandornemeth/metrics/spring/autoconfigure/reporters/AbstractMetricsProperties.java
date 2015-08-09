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
package com.sandornemeth.metrics.spring.autoconfigure.reporters;

import java.util.concurrent.TimeUnit;

/**
 *
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public abstract class AbstractMetricsProperties {

  private TimeUnit rateUnit;
  private TimeUnit durationUnit;
  private long reportInterval;
  private TimeUnit reportIntervalUnit;

  public TimeUnit getRateUnit() {
    return this.rateUnit;
  }

  public TimeUnit getDurationUnit() {
    return this.durationUnit;
  }

  public long getReportInterval() {
    return this.reportInterval;
  }

  public TimeUnit getReportIntervalUnit() {
    return this.reportIntervalUnit;
  }

  public void setRateUnit(TimeUnit rateUnit) {
    this.rateUnit = rateUnit;
  }

  public void setDurationUnit(TimeUnit durationUnit) {
    this.durationUnit = durationUnit;
  }

  public void setReportInterval(long reportInterval) {
    this.reportInterval = reportInterval;
  }

  public void setReportIntervalUnit(TimeUnit reportIntervalUnit) {
    this.reportIntervalUnit = reportIntervalUnit;
  }
}
