#!/usr/bin/python3

import time
import json
import serial
import numpy as np
from datetime import datetime
from urllib.request import Request
from urllib.request import urlopen

# TODO list:
# - better logging


def compute_R(V_measured):
    V_in = 5
    V_out = V_measured/1024 * V_in
    R_constant = 50000
    return R_constant * (V_in/V_out - 1)


def steinhart_value(coefficients, R):
    model = np.array([1, np.log(R), np.log(R)**3])
    T_inv = np.dot(coefficients, model)
    return 1/T_inv - 273.15


def predict_value(model_parameters, x):
    polynome = map(lambda i: model_parameters[i] * x**i, range(len(model_parameters)))
    return sum(polynome)


def compute_temperature(V_measured):
    temperature = steinhart_value([9.65517792e-04, 2.10709736e-04, 8.57903980e-08], compute_R(V_measured))
    return temperature


def compute_humidity(V_measured):
    humidity = predict_value([202.68978603, -17.14067255, 0.32834708], np.log(compute_R(V_measured)))
    return humidity


def create_log(temperature, humidity):
    json_data = json.dumps({
        'timestamp': datetime.utcnow().isoformat(),
        'values': {
            'temperature': temperature,
            'humidity': humidity
        }})
    return json_data.encode('utf8')


def send_log(uri, log):
    request = Request(uri, data=log, method='POST', headers={'Content-Type': 'application/json'})
    response = urlopen(request)
    return response


def query_arduino(query, serial_connection):
    # TODO retry if len(line.strip()) = 0
    serial_connection.write((query + '\n').encode())
    response = serial_connection.readline()
    return int(response.strip())


def log(serial_port, serial_speed):
    """Log sensor values using serial port"""

    print('Starting Arduino logger on port {} with speed {}...'.format(serial_port, serial_speed))
    serial_connection = serial.Serial(serial_port, serial_speed)

    while True:
        temperature = compute_temperature(query_arduino('get temperature', serial_connection))
        humidity = compute_humidity(query_arduino('get humidity', serial_connection))
        sensor_data = create_log(temperature, humidity)

        print('Sending sensor data: {}'.format(sensor_data))
        send_log('http://localhost:8080/logs', sensor_data)
        time.sleep(2)

    print('Closing serial port...')
    serial_connection.close()

# main
log('/dev/ttyACM0', 9600)
