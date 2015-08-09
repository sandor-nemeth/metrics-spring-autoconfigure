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

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.BufferPoolMetricSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.lang.management.ManagementFactory;

/**
 * @author sandornemeth
 */
@Configuration
@ConditionalOnClass(MemoryUsageGaugeSet.class)
@ConditionalOnProperty(prefix = "spring.metrics.jvm", name = "enabled", matchIfMissing = true)
public class JvmMetricSetConfiguration implements MetricSetConfigurer {

  @Override
  public void configureMetricSets(MetricRegistry metricRegistry) {
    metricRegistry.register("gc", new GarbageCollectorMetricSet());
    metricRegistry.register("buffers",
                            new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));
    metricRegistry.register("memory", new MemoryUsageGaugeSet());
    metricRegistry.register("threads", new ThreadStatesGaugeSet());
  }
}
