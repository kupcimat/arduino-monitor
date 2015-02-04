#!/usr/bin/python3

import json
import serial
from datetime import datetime
from urllib.request import Request
from urllib.request import urlopen

# TODO list:
# - better logging

def map_value(value, old_max, new_max):
  return (value / old_max) * new_max

def create_log(value):
  timestamp = datetime.utcnow().isoformat()
  json_data = json.dumps({'timestamp': timestamp, 'value': value})
  return json_data.encode('utf8')

def send_log(uri, log):
  request = Request(uri, data=log, method='POST', headers={'Content-Type': 'application/json'})
  response = urlopen(request)
  return response

def log(serial_port, serial_speed):
  """Log sensor values using serial port"""

  print('Starting Arduino logger on port {} with speed {}...'.format(serial_port, serial_speed))
  serial_terminal = serial.Serial(serial_port, serial_speed)

  log_number = 0
  while True:
    line = serial_terminal.readline()
    if len(line.strip()) > 0:
      n = int(line.strip())
      percent = round(map_value(n, 1023, 100))
      sensor_data = create_log(percent)

      print('Sending sensor data: {}'.format(sensor_data))
      send_log('http://localhost:8080/logs', sensor_data)
      log_number += 1

  print('Closing serial port...')
  serial_terminal.close()

# main
log('/dev/ttyACM0', 9600)
