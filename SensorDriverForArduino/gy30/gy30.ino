#include <Wire.h>  
 
// GY-30
 
// BH1750FVI
 
// in ADDR 'L' mode 7bit addr
 
#define ADDR 0b0100011
 
// addr 'H' mode
 
// #define ADDR 0b1011100
 
 
void setup() {
 
  // put your setup code here, to run once:
 
  Serial.begin(9600);
 
  while (!Serial) {
 
    ; // wait for serial port to connect. Needed for Leonardo only
 
  }
 
  Wire.begin();
 
  pinMode(13, OUTPUT);
 
 
  Wire.beginTransmission(ADDR);
 
  Wire.write(0b00000001);
 
  Wire.endTransmission();
 
 
 
}
 
 
void loop() {
 
  // put your main code here, to run repeatedly: 
 
  int val = 0;
 
 
  // reset
 
  Wire.beginTransmission(ADDR);
 
  Wire.write(0b00000111);
 
  Wire.endTransmission();
 
  digitalWrite(13, LOW);
 
  delay(100);
 
 
  Wire.beginTransmission(ADDR);
 
  Wire.write(0b00010000);//change to continuously H-Resolution Mode
 
  Wire.endTransmission();
 
 
  // typical read delay 120ms
 
  delay(120);
 
 
  Wire.requestFrom(ADDR, 2); // 2byte every time
 
 
 
  for (val=0; Wire.available()>=1; ) {
 
    char c = Wire.read();
 
    //Serial.println(c, HEX);
 
    val = (val << 8) + (c & 0xFF);
 
  }   
 
  val = val / 1.2;
 
  Serial.print("lx: ");
 
  Serial.println(val); 
 
 
  Serial.println("OK");
 
  digitalWrite(13, HIGH);
 
  delay(100);
 
 
 
}

