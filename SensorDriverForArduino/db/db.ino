#define db A0
int temp,data;
 
void setup() 
{
  Serial.begin(9600); 
}
 
 
void loop() 
{
     temp = analogRead(db);
     temp = (long)100*temp/1024;
     Serial.print("db:");
     Serial.println(temp);
     delay(1000);
}

