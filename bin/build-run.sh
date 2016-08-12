#!/usr/bin/env bash

BASEDIR=$(dirname $0)
. ${BASEDIR}/../../scripts/build-run.sh

build-run ${BASEDIR} com.aronim.cloud.component.ComponentApplication