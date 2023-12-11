#!/bin/bash
set -e

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
PROJECT_DIR=$(cd $SCRIPT_DIR/.. && pwd)

$PROJECT_DIR/target/code-with-quarkus-1.0.0-SNAPSHOT-runner.exe \
  -Dquarkus.datasource.username=postgres \
  -Dquarkus.datasource.password=pgpass \
  -Dquarkus.datasource.reactive.url=postgresql://localhost:5432/postgres \
  -Dquarkus.hibernate-orm.database.generation=create