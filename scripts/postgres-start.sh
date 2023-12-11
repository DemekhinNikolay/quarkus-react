#!/bin/bash
set -e

NETWORK=quarkus-network

if ! docker network list|grep $NETWORK; then
  docker network create $NETWORK
fi

docker run --rm --name postgresql --network $NETWORK -p 5432:5432 -e POSTGRES_PASSWORD=pgpass -d postgres:14