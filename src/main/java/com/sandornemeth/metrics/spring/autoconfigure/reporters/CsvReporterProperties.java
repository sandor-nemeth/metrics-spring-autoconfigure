package com.sandornemeth.metrics.spring.autoconfigure.reporters;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import java.util.Locale;

/**
 *
 */
@ConfigurationProperties(value = "spring.metrics.reporter.csv")
public class CsvReporterProperties extends AbstractMetricsProperties {

  private Locale formatFor;
  private Resource file;

  public Locale getFormatFor() {
    return formatFor;
  }

  public void setFormatFor(Locale formatFor) {
    this.formatFor = formatFor;
  }

  public Resource getFile() {
    return file;
  }

  public void setFile(Resource file) {
    this.file = file;
  }
}
