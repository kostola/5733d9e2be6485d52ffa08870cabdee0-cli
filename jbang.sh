#!/bin/bash
set -x
set -e

cd "$( dirname $0 )"
mvn -f logic/pom.xml clean install
jbang --verbose --fresh jbang/ob.java $@
