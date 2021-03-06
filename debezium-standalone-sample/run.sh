#bin/bash


curl --location --request POST 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "containers1-connector",
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
            "include.schema.changes": "true",
            "table.include.list": "public.init_table"
      }
}'

sleep 1

echo '\n' Created connectors: "$(curl -s --location --request GET 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json')"

#"transforms.filter.type": "io.debezium.transforms.Filter",
#            "transforms.filter.language": "jsr223.groovy",
#            "transforms.filter.condition": "value.op == 'u' || value.op == 'c'",