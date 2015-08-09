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
package com.sandornemeth.metrics.spring.autoconfigure.metricsets;

import com.sandornemeth.metrics.spring.autoconfigure.AbstractMetricsAutoconfigurationTestSupport;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class JvmMetricsSetConfigurationTest extends AbstractMetricsAutoconfigurationTestSupport {

  @Test
  public void shouldRegisterJvmMetrics() {
    this.load(JvmMetricsTest.class);
    Set<String> keys = this.metricKeySet();
    assertThat(keys, containsInAnyOrder("memory", "gc", "buffers", "threads"));
  }

  @Test
  public void shouldntRegisterJvmMetricsIfNotEnabled() {
    this.load(JvmMetricsTest.class, "spring.metrics.jvm.enabled:false");

    Set<String> keys = this.metricKeySet();
    assertThat(keys, not(containsInAnyOrder("memory", "gc", "buffers", "threads")));
  }

  protected Set<String> metricKeySet() {
    Map<String, Object> metrics =
        (Map<String, Object>) ReflectionTestUtils.getField(this.getMetricRegistry(), "metrics");
    return metrics.keySet().stream().map(s -> s.split("\\.")[0]).collect(Collectors.toSet());
  }

  @Configuration
  protected static class JvmMetricsTest {

  }

}
