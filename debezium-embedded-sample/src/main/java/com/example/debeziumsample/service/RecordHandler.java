package com.example.debeziumsample.service;

import io.debezium.data.Envelope.FieldName;
import io.debezium.data.Envelope.Operation;
import io.debezium.engine.RecordChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RecordHandler {

  public void handle(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent){
    //todo parse than push to kafka topic
    SourceRecord record = sourceRecordRecordChangeEvent.record();
    log.info("Got record: " + record.toString());
    Struct value = (Struct) record.value();
    if(value!=null){
      Operation operation = Operation.forCode((String) value.get(FieldName.OPERATION));
      if(operation!=Operation.READ){
        String recordName = operation == Operation.DELETE ? FieldName.BEFORE : FieldName.AFTER; // Handling Update & Insert operations.
        Struct struct = (Struct) value.get(recordName);
        log.info(struct.toString());
      }
    }
  }
}
