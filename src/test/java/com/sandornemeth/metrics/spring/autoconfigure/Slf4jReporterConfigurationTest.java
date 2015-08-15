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


import com.codahale.metrics.Slf4jReporter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.Closeable;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class Slf4jReporterConfigurationTest extends AbstractMetricsAutoconfigurationTestSupport {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void shouldNotRegisterSlf4jConfigurationByDefault() {
    this.load(Slf4jTestConfiguration.class);
    thrown.expect(NoSuchBeanDefinitionException.class);
    thrown.expectMessage("Slf4jReporterConfiguration");
    this.context.getBean(MetricsReporterConfiguration.Slf4jReporterConfiguration.class);
  }

  @Test
  public void shouldRegisterDefaultSlf4jConfiguration() {
    this.load(Slf4jTestConfiguration.class, "spring.metrics.reporters.slf4j.enabled=true");

    MetricsReporterConfiguration.Slf4jReporterConfiguration
        slf4jReporterConfiguration =
        this.context.getBean(MetricsReporterConfiguration.Slf4jReporterConfiguration.class);
    assertThat(slf4jReporterConfiguration, notNullValue());
    Set<Closeable> reporters =
        (Set<Closeable>) ReflectionTestUtils.getField(slf4jReporterConfiguration, "reporters");
    assertThat(reporters, notNullValue());
    assertThat(reporters, hasSize(1));
    Closeable reporter = reporters.iterator().next();
    assertThat(reporter.getClass(), equalTo(Slf4jReporter.class));

  }

  @Test
  public void shouldRegisterCustomSlf4jConfiguration() throws Exception {
    this.load(Slf4jTestConfiguration.class, "spring.metrics.reporters.slf4j.enabled=true",
              "spring.metrics.reporters.slf4j.logger=test.helloLogger",
              "spring.metrics.reporters.slf4j.loggingLevel=info",
              "spring.metrics.reporters.slf4j.marker=marker",
              "spring.metrics.reporters.slf4j.prefix=prefix");


    MetricsReporterConfiguration.Slf4jReporterConfiguration
        slf4jReporterConfiguration =
        this.context.getBean(MetricsReporterConfiguration.Slf4jReporterConfiguration.class);
    assertThat(slf4jReporterConfiguration, notNullValue());
    Set<Closeable> reporters =
        (Set<Closeable>) ReflectionTestUtils.getField(slf4jReporterConfiguration, "reporters");
    assertThat(reporters, notNullValue());
    assertThat(reporters, hasSize(1));
    Closeable reporter = reporters.iterator().next();
    assertThat(reporter.getClass(), equalTo(Slf4jReporter.class));
    Thread.sleep(3000);
  }


  @Configuration
  protected static class Slf4jTestConfiguration {

  }
}
