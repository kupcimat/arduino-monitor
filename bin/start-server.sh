#!/bin/bash

if [ $# -ne 2 ]; then
  echo "Usage $0 <host> <port>"
  exit 1
fi

HOST=$1
PORT=$2
JDBC_STRING="jdbc:h2:./arduino-monitor;INIT=runscript from '../db/create.sql'"

echo "Starting arduino-monitor server on ${HOST}:${PORT}..."
java -jar \
     -Darduino-monitor.http.host=$HOST \
     -Darduino-monitor.http.port=$PORT \
     -Darduino-monitor.db.jdbc-string="$JDBC_STRING" \
     arduino-monitor-assembly-*.jar
