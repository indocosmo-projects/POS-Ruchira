#!/bin/bash

smbclient //192.168.1.31/Honeywell -U jojesh%nova -c "put ${1}"
