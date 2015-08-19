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

import com.codahale.metrics.jvm.MemoryUsageGaugeSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class ReporterTest {

  protected AnnotationConfigApplicationContext context;
  protected OutputCaptor outputCaptor = new StdoutCaptor();

  @Before
  public void prepare() {
  }

  @Test
  public void test() throws Exception {
    outputCaptor.start();
    load(TestConf.class,
         "spring.metrics.reporters.console.enabled:true",
         "spring.metrics.reporters.console.rateUnit:SECONDS",
         "spring.metrics.reporters.console.durationUnit:MILLISECONDS",
         "spring.metrics.reporters.console.reportInterval:100",
         "spring.metrics.reporters.console.reportIntervalUnit:MILLISECONDS");
    Thread.sleep(200);
    outputCaptor.close();
    String output = outputCaptor.get();
    verifyRegisteredJvmMetrics(output);
  }

  protected void verifyRegisteredJvmMetrics(String output) {
    Set<String> registeredMemoryUsageGaugeKeys = new MemoryUsageGaugeSet().getMetrics().keySet();
    registeredMemoryUsageGaugeKeys.forEach(k -> assertThat(output, containsString(k)));
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

  @After
  public void cleanUp() throws Exception {
    if (null != context) {
      context.close();
    }

  }

  @Configuration
  protected static class TestConf {

  }

}
