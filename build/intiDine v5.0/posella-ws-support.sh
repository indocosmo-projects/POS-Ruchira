#!/bin/bash

BASEDIR=$(dirname $0)
cd ${BASEDIR}

#java -jar ./posella-ws-support.jar $@

unset DISPLAY
java -Djava.awt.headless=true -jar ./posella-ws-support.jar $@
