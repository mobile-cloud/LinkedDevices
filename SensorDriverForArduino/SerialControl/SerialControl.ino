#define LM35 A0

String comdata = "";
boolean sendflag=false;
int pinLed = 13;//定义连接LED的数字口，当允许通过串口发送数据时，点亮LED，否则关闭LED
int val = 0;				//存放AD变量缓存
float temp = 0;			//温度值

void setup()
{
    Serial.begin(9600);
}

void loop()
{
    val = analogRead(LM35);			//读取AD值
    temp = val * 0.48876;				//计算温度值

    while (Serial.available() > 0)  
    {
        comdata += char(Serial.read());
        delay(2);
    }
    if (comdata.length() > 0)
    {
        Serial.print("Read from serial port:");
        Serial.println(comdata);
        
        if(comdata=="serial start")
        {
          sendflag=true;
        }
        else if(comdata=="serial stop")
        {
          sendflag=false;
        }
        
        comdata = "";
    }
  if(sendflag)//如果允许通过串口发送数据，则点亮LED并发送数据，否则关闭LED
  {
    digitalWrite(pinLed, HIGH);
    Serial.print("temp value:");
    Serial.println(temp);
  }
  else
  {
    digitalWrite(pinLed, LOW);
  }
   delay(1000);
}
