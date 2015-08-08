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

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.BufferPoolMetricSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

/**
 *
 */
@Configuration
@ConditionalOnClass({MetricRegistry.class})
@Import({MetricsAnnotationConfiguration.class})
public class MetricsAutoConfiguration {

  @Configuration
  protected static class MetricSetConfiguration {

    @Autowired
    private MetricRegistry metricRegistry;

    private List<MetricSetConfigurer> metricSetConfigurers = new ArrayList<>();

    @Autowired(required = false)
    public void setMetricSetConfigurers(Collection<MetricSetConfigurer> metricSetConfigurers) {
      if (null != metricSetConfigurers) {
        this.metricSetConfigurers.addAll(metricSetConfigurers);
      }
    }

    @PostConstruct
    public void initializeMetricSets() {
      this.metricSetConfigurers.forEach(c -> c.configureMetricSets(this.metricRegistry));
    }
  }

  @Configuration
  @ConditionalOnClass(MemoryUsageGaugeSet.class)
  @ConditionalOnProperty(prefix = "spring.metrics.jvm",  name = "enabled", matchIfMissing = true)
  protected static class JvmConfiguration implements MetricSetConfigurer {

    @Override
    public void configureMetricSets(MetricRegistry metricRegistry) {
      metricRegistry.register("gc", new GarbageCollectorMetricSet());
      metricRegistry.register("buffers",
                              new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));
      metricRegistry.register("memory", new MemoryUsageGaugeSet());
      metricRegistry.register("threads", new ThreadStatesGaugeSet());
    }
  }




}
