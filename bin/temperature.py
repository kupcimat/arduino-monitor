#!/usr/bin/python3

import numpy as np

# constants
KELVIN_ZERO = 273.15


def steinhart_value(coefficients, R):
    model = np.array([1, np.log(R), np.log(R)**3])
    T_inv = np.dot(coefficients, model)
    return 1/T_inv - KELVIN_ZERO


def compute_R(V_measured):
    V_in = 5
    V_out = V_measured/1024 * V_in
    R_constant = 50000
    return R_constant * (V_in/V_out - 1)


# measured values
R = np.array([170291, 50000, 16421])
T = np.array([0.56, 25.0, 50.56])
ones = np.ones(R.size)

# find Steinhart-Hart coefficients
A = np.column_stack((ones, np.log(R), np.log(R)**3))
B = 1/(T + KELVIN_ZERO)
coefficients = np.linalg.solve(A, B)

print('Steinhart-Hart coefficients={}'.format(coefficients))

# compute temperature
V_measured = 447.0
R_measured = compute_R(V_measured)
temperature = steinhart_value(coefficients, R_measured)

print('Thermistor R={}'.format(R_measured))
print('Thermistor T={}'.format(temperature))
