package com.example.debeziumsample.service;

import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DebeziumListener implements InitializingBean {

  @Autowired
  private RecordHandler handler;

  private final Executor executor = Executors.newSingleThreadExecutor();
  private final DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;

  public DebeziumListener(
      Properties debeziumConfig
  ) {
    this.debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
        .using(debeziumConfig)
        .notifying(val -> handler.handle(val))
        .build();
  }


  @Override
  public void afterPropertiesSet() throws Exception {
    executor.execute(debeziumEngine);
  }

  @PreDestroy
  private void stop() throws IOException {
    if (debeziumEngine != null) {
      debeziumEngine.close();
    }
  }
}
