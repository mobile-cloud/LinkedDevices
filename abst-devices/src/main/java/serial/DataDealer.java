package serial;

import org.linkeddev.abstdev.constant.Sensors;

public class DataDealer {
	//all data
	
/*	public float DataLM35=0;
	public float DataDHT11Temp=0;
	public float DataDHT11Humi=0;
	public int DataDB=0;*/
	public String DataLM35="";
	public String DataDHT11Temp="";
	public String DataDHT11Humi="";
	public String DataDB="";
	/*
	 * Type Change
	 */
	//change the string type to the float type
	   public static   float stringToFloat(String floatstr)
	   {
	     Float floatee;
	     floatee = Float.valueOf(floatstr);
	     return floatee.floatValue();
	   }
	   //change the string type to the int type
	   public static   int stringToInt(String intstr)
	   {
	     Integer integer;
	     integer = Integer.valueOf(intstr);
	     return integer.intValue();
	   }
	   
	public void DealData(String ori){
		String[] strarray=ori.split("=");
	      if(strarray.length>=2){
		      switch(strarray[0]){
/*		      case Sensors.LM35:
		    	  DataLM35=stringToFloat(strarray[1]);
		    	  break;
		      case Sensors.DHT11_Humidity:
		    	  DataDHT11Humi=stringToFloat(strarray[1]);
		    	  break;
		      case Sensors.DHT11_Temp:
		    	  DataDHT11Temp=stringToFloat(strarray[1]);
		    	  break;
		      case Sensors.DB:
		    	  DataDB=stringToInt(strarray[1]);
		    	  break;*/
		      case Sensors.LM35:
		    	  DataLM35=(strarray[1]);
		    	  break;
		      case Sensors.DHT11_Humidity:
		    	  DataDHT11Humi=(strarray[1]);
		    	  break;
		      case Sensors.DHT11_Temp:
		    	  DataDHT11Temp=(strarray[1]);
		    	  break;
		      case Sensors.DB:
		    	  DataDB=(strarray[1]);
		    	  break;
		      }
	    	  
	      }

		
	}
	public static void main(String[] args){
		String test="db=3";
		DataDealer dd=new DataDealer();
		dd.DealData(test);
		System.out.println("DataLM35:"+dd.DataLM35);
		System.out.println("DataDHT11_Humi:"+dd.DataDHT11Humi);
		System.out.println("DataDHT11_Temp:"+dd.DataDHT11Temp);
		System.out.println("DataDB:"+dd.DataDB);
		
		
	}

}
