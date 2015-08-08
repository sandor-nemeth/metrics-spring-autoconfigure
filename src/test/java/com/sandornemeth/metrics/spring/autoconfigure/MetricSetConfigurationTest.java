package com.sandornemeth.metrics.spring.autoconfigure;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class MetricSetConfigurationTest {

  @Test
  public void testNullListPassedToSetMetricSetConfigurers() {
    MetricsAutoConfiguration.MetricSetConfiguration
        metricSetConfiguration =
        new MetricsAutoConfiguration.MetricSetConfiguration();
    metricSetConfiguration.setMetricSetConfigurers(null);
    List<MetricSetConfigurer> metricSetConfigurers =
        (List<MetricSetConfigurer>) ReflectionTestUtils
            .getField(metricSetConfiguration, "metricSetConfigurers");

    assertThat(metricSetConfigurers, notNullValue());
    assertThat(metricSetConfigurers, hasSize(0));
  }

}
