package com.example.debeziumsample.config;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Configuration {

  @Autowired
  private DatabaseConfiguration databaseConfiguration;

  @Bean
  Properties debeziumConfig() throws IOException {
    File offsetStorageTemplate = File.createTempFile("offsets_", ".dat");
    File dbHistoryTempFile = File.createTempFile("db_history_", ".dat");
    final Properties props = new Properties();
    props.setProperty("name", "engine");
    props.setProperty("connector.class", "io.debezium.connector.postgresql.PostgresConnector");
    props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
    props.setProperty("offset.storage.file.filename", offsetStorageTemplate.getAbsolutePath());
    props.setProperty("offset.flush.interval.ms", "60000");
    props.setProperty("database.hostname", databaseConfiguration.getHost());
    props.setProperty("plugin.name", "pgoutput");
    props.setProperty("database.port", databaseConfiguration.getPort());
    props.setProperty("database.user", databaseConfiguration.getUsername());
    props.setProperty("database.password", databaseConfiguration.getPassword());
    props.setProperty("database.dbname", databaseConfiguration.getDatabase());
//    props.setProperty("transforms","filter");
//    props.setProperty("transforms.filter.type","io.debezium.embedded.ExampleFilterTransform");
//    props.setProperty("transforms.filter.language","jsr223.groovy");
//    props.setProperty("transforms.filter.condition","value.op == 'u' || value.op == 'c'");
    props.setProperty("slot.name", "tst_slot_name");
    props.setProperty("database.server.name", "my-app-connector");
    props.setProperty("database.history",
        "io.debezium.relational.history.FileDatabaseHistory");
    props.setProperty("database.history.file.filename",
        dbHistoryTempFile.getAbsolutePath());
    return props;
  }
}
