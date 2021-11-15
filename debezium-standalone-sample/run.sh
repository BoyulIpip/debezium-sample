#bin/bash


curl --location --request POST 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "containers-connector",
    "config": {
            "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
            "plugin.name": "pgoutput",
            "database.hostname": "db",
            "database.port": "5432",
            "database.user": "postgres",
            "database.password": "postgres",
            "database.dbname": "test",
            "database.server.name": "myTest",
            "database.history.kafka.topic": "dbhistory",
            "table.include.list": "public.init_table",
            "key.converter": "io.confluent.connect.avro.AvroConverter",
            "key.converter.schema.registry.url": "http://schema-registry:8081",
            "value.converter": "io.confluent.connect.avro.AvroConverter",
            "value.converter.schema.registry.url": "http://schema-registry:8081"
      }
}'

sleep 1

echo '\n' Created connectors: "$(curl -s --location --request GET 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json')"
