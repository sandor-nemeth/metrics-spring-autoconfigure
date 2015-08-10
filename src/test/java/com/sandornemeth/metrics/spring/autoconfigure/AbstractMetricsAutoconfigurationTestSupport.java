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

import org.junit.After;
import org.springframework.boot.test.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author sandornemeth
 */
public abstract class AbstractMetricsAutoconfigurationTestSupport {

  protected AnnotationConfigApplicationContext context;

  @After
  public void close() {
    if (null != this.context) {
      this.context.close();
    }
  }

  protected MetricRegistry getMetricRegistry() {
    return this.context.getBean(MetricRegistry.class);
  }

  protected void load(Class<?> config, String... environment) {
    this.context = doLoad(new Class<?>[]{config}, environment);
  }

  private AnnotationConfigApplicationContext doLoad(Class<?>[] configs, String... environment) {
    AnnotationConfigApplicationContext
        applicationContext =
        new AnnotationConfigApplicationContext();
    applicationContext.register(configs);
    applicationContext.register(MetricsAutoConfiguration.class);
    EnvironmentTestUtils.addEnvironment(applicationContext, environment);
    applicationContext.refresh();
    return applicationContext;
  }


}
