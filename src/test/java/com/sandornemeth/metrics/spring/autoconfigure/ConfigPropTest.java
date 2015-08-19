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
    import org.springframework.context.annotation.Configuration;

    import static org.hamcrest.CoreMatchers.equalTo;
    import static org.junit.Assert.assertThat;

public class ConfigPropTest extends AbstractMetricsAutoconfigurationTestSupport {

  @Test
  public void shouldLoadAllProperties() {
    this.load(Conf.class,
              "spring.metrics.reporters.console[0].rateUnit=SECONDS",
              "spring.metrics.reporters.console[0].durationUnit=MILLISECONDS",
              "spring.metrics.reporters.console[0].reportIntervalUnit=SECONDS",
              "spring.metrics.reporters.console[0].reportInterval=1");

    MetricConfigurationProperties
        configurationProperties =
        this.context.getBean(MetricConfigurationProperties.class);
    assertThat(configurationProperties.getReporters().getConsole().size(), equalTo(1));

  }

  @Configuration
  protected static class Conf {

  }

}
