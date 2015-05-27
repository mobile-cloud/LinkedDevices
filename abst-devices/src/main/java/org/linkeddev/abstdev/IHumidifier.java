package org.linkeddev.abstdev;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusMethod;
import org.alljoyn.bus.BusException;

@BusInterface (name= "org.absdev.businterface.humidifier")
public interface IHumidifier{
	@BusMethod
	public int getHumidity() throws BusException;
	
	@BusMethod
	public int setHumidity(int humi) throws BusException;
	
	@BusMethod
	public int turnOff() throws BusException;
	
	@BusMethod
	public int turnOn() throws BusException;
	
	@BusMethod
    public int getOnOff() throws BusException;
}