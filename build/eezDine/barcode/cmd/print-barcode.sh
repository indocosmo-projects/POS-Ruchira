#!/bin/bash

smbclient //192.168.1.149/Honeywell -U LAP-058%cosmo2019INDIA -c "put ${1}"
