package com.example.debeziumsample.service;

import static java.util.stream.Collectors.toMap;

import io.debezium.data.Envelope.FieldName;
import io.debezium.data.Envelope.Operation;
import io.debezium.engine.RecordChangeEvent;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RecordHandler {

  public void handle(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
    //log.debug("Got event: " + sourceRecordRecordChangeEvent);

    SourceRecord record = sourceRecordRecordChangeEvent.record();
    Struct value = (Struct) record.value();
    Optional<Operation> operationType = getOperationType(value);
    if (operationType.isEmpty() || !isOperationTypeExpected(operationType.get())) {
      log.debug("Unexpected operation type was");
    } else {
      Struct struct = (Struct) value.get(FieldName.AFTER);
      Map<String, Object> payload = struct.schema().fields().stream()
          .map(Field::name)
          .filter(fieldName -> struct.get(fieldName) != null)
          .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
          .collect(toMap(Pair::getKey, Pair::getValue));

      //todo get the table name and push payload to topic
      log.debug("Data: {}", payload);
    }
  }

  private Boolean isOperationTypeExpected(Operation opType) {
    return opType.equals(Operation.CREATE) || opType.equals(Operation.UPDATE);
  }

  private Optional<Operation> getOperationType(Struct value) {
    if (value == null) {
      return Optional.empty();
    }
    return Optional.of(Operation.forCode(value.getString(FieldName.OPERATION)));
  }
}
