#include <IRremote.h>      // 引用 IRRemote 函式库

const int irReceiverPin = 2;             // 红外线接收器 OUTPUT 讯号接在 pin 2

IRrecv irrecv(irReceiverPin);            // 定义 IRrecv 物件来接收红外线讯号
decode_results results;                  // 解码结果将放在 decode_results 结构的 result 变数里

void setup()
{
  Serial.begin(9600);                     // 开启 Serial port, 通讯速率为 9600 bps
  irrecv.enableIRIn();                   // 启动红外线解码
  Serial.print("begin:\n");
}

// 显示红外线协定种类
void showIRProtocol(decode_results *results) 
{
  Serial.print("Protocol: ");
  
  // 判断红外线协定种类
  switch(results->decode_type) {
   case NEC:
     Serial.print("NEC");
     break;
   case SONY:
     Serial.print("SONY");
     break;
   case RC5:
     Serial.print("RC5");
     break;
   case RC6:
     Serial.print("RC6");
     break;
   default:
     Serial.print("Unknown encoding");  
  }  

  // 把红外线编码印到 Serial port
  Serial.print(", irCode: ");            
  Serial.print(results->value, HEX);    // 红外线编码
  Serial.print(",  bits: ");           
  Serial.println(results->bits);        // 红外线编码位元数    
}

void loop() 
{
  if (irrecv.decode(&results)) {         // 解码成功，收到一组红外线讯号
    showIRProtocol(&results);            // 显示红外线协定种类
    irrecv.resume();                     // 继续收下一组红外线讯号        
  }  
}
