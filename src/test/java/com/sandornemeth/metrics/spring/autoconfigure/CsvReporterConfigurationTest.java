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

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.context.annotation.Configuration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CsvReporterConfigurationTest extends AbstractMetricsAutoconfigurationTestSupport {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Test
  public void shouldNotLoadACsvReporterIfNoneConfigured() throws InterruptedException {
    String[] contents = temporaryFolder.getRoot().list();
    assertThat(contents.length, is(0));
    this.load(CsvReporterTestConfiguration.class);

    // sleep a little bit and verify if report files are generated.
    Thread.sleep(2000);
    String[] loggedContents = temporaryFolder.getRoot().list((f, name) -> name.endsWith(".csv"));
    assertThat(loggedContents.length, is(0));
  }

  @Test public void shouldNotLoadCsvReporterWhenExplicitlyNotEnabled() throws InterruptedException {
    String[] contents = temporaryFolder.getRoot().list();
    assertThat(contents.length, is(0));
    this.load(CsvReporterTestConfiguration.class, "spring.metrics.reporters.csv.enabled=false",
              "spring.metrics.reporters.csv.formatFor=US",
              "spring.metrics.reporters.csv.reportFolder=" + temporaryFolder.getRoot()
                  .getAbsolutePath());

    // sleep a little bit and verify if report files are generated.
    Thread.sleep(2000);
    String[] loggedContents = temporaryFolder.getRoot().list((f, name) -> name.endsWith(".csv"));
    assertThat(loggedContents.length, is(0));
  }

  @Test
  public void csvReporterShouldBeActivatedOnConfiguration() throws InterruptedException {
    String[] contents = temporaryFolder.getRoot().list();
    assertThat(contents.length, is(0));
    this.load(CsvReporterTestConfiguration.class, "spring.metrics.reporters.csv.enabled=true",
              "spring.metrics.reporters.csv.formatFor=US",
              "spring.metrics.reporters.csv.reportFolder=" + temporaryFolder.getRoot()
                  .getAbsolutePath());

    // sleep a little bit and verify if report files are generated.
    Thread.sleep(2000);
    String[] loggedContents = temporaryFolder.getRoot().list((f, name) -> name.endsWith(".csv"));
    assertThat(loggedContents.length, Matchers.greaterThan(0));
  }

  @Configuration
  protected static class CsvReporterTestConfiguration {

  }

}
