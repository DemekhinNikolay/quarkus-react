#!/bin/bash
set -e

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
PROJECT_DIR=$(cd $SCRIPT_DIR/.. && pwd)

NETWORK=quarkus-network

cd $PROJECT_DIR
docker run -d --rm -p 8080:8080 --network $NETWORK --name quarkus-react quarkus-react \
  -Dquarkus.datasource.username=postgres \
  -Dquarkus.datasource.password=pgpass \
  -Dquarkus.datasource.reactive.url=postgresql://postgresql:5432/postgres \
  -Dquarkus.hibernate-orm.database.generation=create