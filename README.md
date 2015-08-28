## spring-boot-autoconfigure

[![Build Status](https://travis-ci.org/sandor-nemeth/metrics-spring-autoconfigure.svg?branch=master)](https://travis-ci.org/sandor-nemeth/metrics-spring-autoconfigure)
[![Coverage Status](https://coveralls.io/repos/sandor-nemeth/metrics-spring-autoconfigure/badge.svg?branch=master&service=github)](https://coveralls.io/github/sandor-nemeth/metrics-spring-autoconfigure?branch=master)
[![License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[ ![Download](https://api.bintray.com/packages/sandor-nemeth/maven/metrics-spring-autoconfigure/images/download.svg) ](https://bintray.com/sandor-nemeth/maven/metrics-spring-autoconfigure/_latestVersion)

Spring-Boot autoconfiguration library for the 
[metrics-spring](https://github.com/ryantenney/metrics-spring) library.

**This documentation is only for the configuration of the plugin. For the possibilities of the 
[metrics-spring library](https://github.com/ryantenney/metrics-spring) library, and the 
[Dropwizard Metrics](http://metrics.dropwizard.io/) library itself please see the corresponding 
pages themselves.**

**This project is under active development, please expect that until v1.0.0 breaking changes can
 be submitted.**
 
### Where to get it
 
The project is available currently on bintray:

```xml
<!-- Repository configuration -->
<repository>
  <id>bintray-sandor-nemeth-maven</id>
  <name>bintray</name>
  <url>http://dl.bintray.com/sandor-nemeth/maven</url>
  <snapshots>
    <enabled>false</enabled>
  </snapshots>
</repository>

<!-- Artifact -->
<dependency>
  <groupId>com.sandornemeth</groupId>
  <artifactId>metrics-spring-autoconfigure</artifactId>
  <version>0.0.1</version>
</dependency>
```

### How to use it

Use it with Spring Boot's ```application.yml``` or ```application
.properties```. The examples below are in yaml, but they can be easily ported to properties. 

All ```*Unit``` properties are constants from the ```java.util.concurrent.TimeUnit``` class, and 
now nothing else is supported.

As every reporter-type is an array, you are welcome to configure as many reporters, as you want.

```yml 
spring:
  metrics:
    reporters:
      console:
        -
          rateUnit: SECONDS
          durationUnit: MILLISECONDS
          reportInterval: 100
          reportIntervalUnit: MILLISECONDS
      csv:
        - 
          rateUnit: SECONDS
          durationUnit: MILLISECONDS
          reportInterval: 100
          reportIntervalUnit: MILLISECONDS
          formatFor: US                             # java.util.Locale language tag
          reportFolder: /report_dir                 # absolute path to the report folder
      slf4j:
        - 
          rateUnit: SECONDS
          durationUnit: MILLISECONDS
          reportInterval: 100
          reportIntervalUnit: MILLISECONDS
          prefix: prefix
          logger: com.sandornemeth.MetricReport
          loggingLevel: WARN                        # values: TRACE, DEBUG, INFO, WARN, ERROR
          marker: marker
```

Also, if the ```metrics-jvm``` library is included, metrics for the JVM are published.

### What is to come: 

- Support for Graphite and Ganglia reporters
- Metric filter configuration (regex and custom MetricFilter based on Spring beans)
- Custom naming for the JVM metrics
- I am not quite sure at the moment, what kind of health checks and instrumentations can be 
supported via this configuration, but I'll investigate it later.
