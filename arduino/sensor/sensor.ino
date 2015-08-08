/*
 * Arduino Sensor
 */

// protocol
char   MSG_DELIMITER = '\n';
String MSG_GET_POT   = "get pot";
String MSG_GET_TEMP  = "get temperature";
String MSG_GET_HUMI  = "get humidity";
String MSG_INVALID   = "-1";

// sensors
int POT_SENSOR = A0;
int TEMP_SENSOR = A1;
int HUMI_SENSOR = A2;

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

    // reply with requested value
    if (request.equals(MSG_GET_POT)) {
      Serial.println(analogRead(POT_SENSOR));

    } else if (request.equals(MSG_GET_TEMP)) {
      Serial.println(analogRead(TEMP_SENSOR));

    } else if (request.equals(MSG_GET_HUMI)) {
      Serial.println(analogRead(HUMI_SENSOR));

    } else {
      // return -1 for invalid request
      Serial.println(MSG_INVALID);
    }
  }

  // sleep
  delay(10);
}

