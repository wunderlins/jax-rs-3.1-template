#!/usr/bin/env bash
script_filename=$(readlink -f $(dirname ${BASH_SOURCE[0]}))
echo "$script_filename"

KEYCLOAK_VERSION=20.0.1
KEYCLOAK_LISTEN_PORT=8444
KEYCLOAK_DATA_DIR=$script_filename/data/keycloak/import