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
