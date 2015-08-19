package com.sandornemeth.metrics.spring.autoconfigure;

import com.sandornemeth.metrics.spring.autoconfigure.outputcaptor.CsvOutputCaptor;
import com.sandornemeth.metrics.spring.autoconfigure.outputcaptor.LogbackCaptor;
import com.sandornemeth.metrics.spring.autoconfigure.outputcaptor.OutputCaptor;

import org.junit.Test;

/**
 * @author sandornemeth
 */
public class Slf4jReporterTest extends ReporterTestCaseBase {

  private static final String METRIC_LOGGER_NAME = "log.metric";

  @Override
  OutputCaptor getOutputCaptor() {
    return new LogbackCaptor(METRIC_LOGGER_NAME);
  }

  @Test
  public void runCsvReporterTest() throws Exception {
    executeReporterTest(200,
                        "spring.metrics.reporters.slf4j[0].enabled:true",
                        "spring.metrics.reporters.slf4j[0].rateUnit:SECONDS",
                        "spring.metrics.reporters.slf4j[0].durationUnit:MILLISECONDS",
                        "spring.metrics.reporters.slf4j[0].reportInterval:100",
                        "spring.metrics.reporters.slf4j[0].reportIntervalUnit:MILLISECONDS",
                        "spring.metrics.reporters.slf4j[0].prefix:prefix",
                        "spring.metrics.reporters.slf4j[0].logger:" + METRIC_LOGGER_NAME,
                        "spring.metrics.reporters.slf4j[0].loggingLevel:WARN");
  }
}
