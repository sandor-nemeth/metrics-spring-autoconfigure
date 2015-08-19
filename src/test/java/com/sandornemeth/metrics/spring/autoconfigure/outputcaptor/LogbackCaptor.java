package com.sandornemeth.metrics.spring.autoconfigure.outputcaptor;

public class LogbackCaptor implements OutputCaptor {

  private String loggerName;
  private String contents;

  public LogbackCaptor(String loggerName) {
    this.loggerName = loggerName;
  }

  @Override
  public void start() {
    LogbackCapture.start(loggerName, null, null);
  }

  @Override
  public String get() {
    return contents;
  }

  @Override
  public void close() throws Exception {
    contents = LogbackCapture.stop();
  }
}
