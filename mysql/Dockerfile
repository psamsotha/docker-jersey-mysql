FROM mysql:8.0.23

COPY ./schema.sql /docker-entrypoint-initdb.d/1-schema.sql
COPY ./data.sql /docker-entrypoint-initdb.d/2-data.sql
