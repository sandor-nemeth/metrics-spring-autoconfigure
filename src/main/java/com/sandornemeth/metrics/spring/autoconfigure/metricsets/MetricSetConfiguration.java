package com.sandornemeth.metrics.spring.autoconfigure.metricsets;

import com.codahale.metrics.MetricRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

/**
 * @author sandornemeth
 */
@Configuration
@Import({
    JvmMetricSetConfiguration.class
})
public class MetricSetConfiguration {

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
