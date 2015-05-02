/*
 * Arduino Sensor
 */

// protocol
char   MSG_DELIMITER = '\n';
String MSG_GET_POT   = "get pot";

// sensors
int POT_SENSOR = A0;

void setup() {
  // initialize serial and wait for port to open
  Serial.begin(9600);
  while (!Serial);
}

void loop() {
  // check if there is any request
  if (Serial.available()) {
    // read client request
    String request = Serial.readStringUntil(MSG_DELIMITER);

    if (request.equals(MSG_GET_POT)) {
      Serial.println(readPotValue());
    }
  }

  // sleep
  delay(10);
}

int readPotValue() {
  // read analog sensor value
  return analogRead(POT_SENSOR);
}

