#!/usr/bin/python3

import time
import random

from logger import create_log
from logger import send_log


def send_random_logs(arduino_monitor='localhost:8080', sleep_time=2):
    for i in range(10):
        random_value = random.uniform(0, 100)
        mock_data = create_log(random_value, random_value, random_value, random_value)

        print('Sending mock data to {}... {}'.format(arduino_monitor, (10 - i)))
        send_log('http://{}/logs'.format(arduino_monitor), mock_data)
        time.sleep(sleep_time)


if __name__ == '__main__':
    import sys

    if len(sys.argv) >= 3:
        send_random_logs(sys.argv[1], int(sys.argv[2]))
    elif len(sys.argv) == 2:
        send_random_logs(sys.argv[1])
    else:
        send_random_logs()
