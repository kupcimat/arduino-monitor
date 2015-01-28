/*
 * TODO
 */

int POT_SENSOR = A0;

void setup() {
  // initialize serial and wait for port to open
  Serial.begin(9600);
  while (!Serial);
}

void loop() {
  // read analog sensor value
  int value = analogRead(POT_SENSOR);
  // write sensor value to serial
  Serial.println(value);

  // sleep
  delay(1000);
}

