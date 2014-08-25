#include <dht11.h>
 
dht11 DHT11;
 
#define DHT11PIN 2
#define LM35 A0
#define DB A1

int val = 0;				//存放AD变量缓存
float temp = 0;			//温度值
int db=0;                        //分贝值

void setup()
{
  Serial.begin(9600);		//串口波特率9600
  Serial.println("MultiSensor Test");
  Serial.println("Sensor 1:LM35");
  Serial.println("Sensor 2:dht11");
  Serial.println("Sensor 3:db");
}

void loop()
{
  /////////////////////////////////////////////////////////////////////
  //LM35
  Serial.println("Read LM35 sensor: ");
  val = analogRead(LM35);			//读取AD值
  temp = val * 0.48876;				//计算温度值
  Serial.print("LM35= ");		
  Serial.println(temp);				//串口输出温度值
  /////////////////////////////////////////////////////////////////////
  //dht11
    int chk = DHT11.read(DHT11PIN);
 
  Serial.print("Read dht 11 sensor: ");
  switch (chk)
  {
    case DHTLIB_OK: 
                Serial.println("OK"); 
                break;
    case DHTLIB_ERROR_CHECKSUM: 
                Serial.println("Checksum error"); 
                break;
    case DHTLIB_ERROR_TIMEOUT: 
                Serial.println("Time out error"); 
                break;
    default: 
                Serial.println("Unknown error"); 
                break;
  }
 
  Serial.print("Humidity (%)=");
  Serial.println((float)DHT11.humidity, 2);
 
  Serial.print("Temperature (oC)=");
  Serial.println((float)DHT11.temperature, 2);
////////////////////////////////////////////////////////////////////
//db
  Serial.println("Read db sensor: ");
  val=analogRead(DB);
  db = (long)100*val/1024;
  Serial.print("db=");
  Serial.println(db);
  

  delay(2000);
}
