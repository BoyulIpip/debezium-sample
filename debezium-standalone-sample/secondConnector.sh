#bin/bash


curl --location --request POST 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "containers2-connector",
    "config": {
            "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
            "plugin.name": "pgoutput",
            "database.hostname": "db",
            "database.port": "5432",
            "database.user": "postgres",
            "database.password": "postgres",
            "database.dbname": "test",
            "database.server.name": "myTest2",
            "database.history.kafka.topic": "dbhistory",
            "transforms": "unwrap",
            "transforms.unwrap.type": "io.debezium.transforms.ExtractNewRecordState",
            "include.schema.changes": "true",
            "table.include.list": "public.init_table"
      }
}'

sleep 1

echo '\n' Created connectors: "$(curl -s --location --request GET 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json')"