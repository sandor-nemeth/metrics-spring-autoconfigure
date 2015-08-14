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

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

/**
 * {@link ConfigurationProperties Configuration properties} for the {@link
 * com.codahale.metrics.CsvReporter CSV reporter}.
 */
@ConfigurationProperties(value = "spring.metrics.reporters.csv")
class CsvReporterProperties extends AbstractMetricsReporterProperties {

  private Locale formatFor = Locale.US;

  @NotBlank
  private String reportFolder;

  public Locale getFormatFor() {
    return formatFor;
  }

  public void setFormatFor(Locale formatFor) {
    this.formatFor = formatFor;
  }

  public String getReportFolder() {
    return reportFolder;
  }

  public void setReportFolder(String reportFolder) {
    this.reportFolder = reportFolder;
  }
}
