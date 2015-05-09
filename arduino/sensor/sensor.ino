/*
 * Arduino Sensor
 */

// protocol
char   MSG_DELIMITER = '\n';
String MSG_GET_POT   = "get pot";
String MSG_GET_TEMP  = "get temperature";
String MSG_GET_HUMI  = "get humidity";

// sensors
int POT_SENSOR = A0;
// TODO both sensors use the same port for now
int TEMP_SENSOR = A1;
int HUMI_SENSOR = A1;

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
    }

    if (request.equals(MSG_GET_TEMP)) {
      Serial.println(analogRead(TEMP_SENSOR));
    }

    if (request.equals(MSG_GET_HUMI)) {
      Serial.println(analogRead(HUMI_SENSOR));
    }
  }

  // sleep
  delay(10);
}

