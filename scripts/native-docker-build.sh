#!/bin/bash
set -e

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
PROJECT_DIR=$(cd $SCRIPT_DIR/.. && pwd)

cd $PROJECT_DIR
docker build -t quarkus-react -f src/main/docker/Dockerfile.native .