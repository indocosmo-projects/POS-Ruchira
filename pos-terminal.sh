#!/bin/bash

BASEDIR=$(dirname $0)
cd ${BASEDIR}

LD_LIBRARY_PATH=${BASEDIR}/libs/shared:${LD_LIBRARY_PATH}
export LD_LIBRARY_PATH

while true
do
	java -jar ./pos-terminal.jar 
	ret=$?
	echo $ret
	if [ $ret -ne 5 ]; then
     	    exit 0
	fi

done
