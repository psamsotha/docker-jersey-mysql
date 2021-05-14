# Docker Jersey MySQL Starter

## Run

#### Start

```
docker-compose -f docker-compose.dev.yml up
```

Port 8080 in the docker container is published to 8080 on the host machine, so
you can call the API directly from the host machine

#### Stop

```
docker-compose -f docker-compose.dev.yml down
```

## API

This example is a simple API of customer information. There is only one endpoint

```
curl -i localhost:8080/customers/(1-7)
```

## MySQL

Initialization scripts in ./mysql are copied during the MySQL docker container
creation. They are run in alphabetical order, hence the naming with numbers
during the Dockerfile `COPY` instruction. In order for these scripts to be run, the data volume needs to be empty. If
you run docker-compose up multiple times, changes to the script will not take
effect. To fix this, you can delete the docker volume either with `docker volume prune`
or `docker volume ls` to list the volumes and then `docker volume rm` to remove
individual volumes. These will be named `xxx_mysql_data` and `xxx_mysql_config`.


## Logging

The log level can be set with the environment variable `LOG_LEVEL` set in the
`docker-compose.dev.yml` file. It will default to INFO if one is not specified.
