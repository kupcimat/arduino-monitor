#!/usr/bin/python3

import math
import numpy as np
import matplotlib.pyplot as plot

def train_model(degree, x, y):
    model_parameters = np.polyfit(x, y, degree)
    return model_parameters[::-1]

def predict_value(model_parameters, x):
    polynome = map(lambda i: model_parameters[i] * x**i, range(len(model_parameters)))
    return sum(polynome)

def compute_rmse(y_data, y_model):
    return math.sqrt(sum((y_data - y_model)**2) / len(y_data))

def plot_model(model_parameters, x_data, y_data):
    y_model = [predict_value(model_parameters, x) for x in x_data]
    plot.plot(x_data, y_data, 'b')
    plot.plot(x_data, y_model, 'r--')
    plot.show()

def compute_R(V_measured):
    V_in = 5
    V_out = V_measured/1024 * V_in
    R_constant = 50000
    return R_constant * (V_in/V_out - 1)

# training data
data = np.array([[2890000, 20],
                 [900000,  30],
                 [270000,  40],
                 [81000,   50],
                 [33000,   60],
                 [13000,   70],
                 [5300,    80],
                 [2200,    90],
                 [1500,    95]])

x_data = np.log(data[:,0])
y_data = data[:,1]

# compare different models
model_complexity = range(1, 6)
model_errors = []

# plot models
for degree in model_complexity:
    model = train_model(degree, x_data, y_data)
    rmse = compute_rmse(y_data, [predict_value(model, x) for x in x_data])
    model_errors.append(rmse)

    print('Model degree={}, rmse={}, parameters={}'.format(degree, rmse, model))
    plot_model(model, x_data, y_data)

# plot model errors
plot.plot(model_complexity, model_errors, 'r')
plot.show()

# compute humidity
V_measured = 193
R_measured = compute_R(V_measured)
humidity = predict_value(model, np.log(R_measured))

print('Humidity R={}'.format(R_measured))
print('Humidity H={}'.format(humidity))
