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
