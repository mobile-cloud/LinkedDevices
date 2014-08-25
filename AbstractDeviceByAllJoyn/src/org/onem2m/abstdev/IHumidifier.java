package org.onem2m.abstdev;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusMethod;
import org.alljoyn.bus.BusException;

@BusInterface (name= "org.absdev.businterface.humidifier")
public interface IHumidifier{
	@BusMethod
	public int getHumidifier() throws BusException;
	
	@BusMethod
	public int setHumidifier(int humi) throws BusException;
	
	@BusMethod
	public int turnOff() throws BusException;
	
	@BusMethod
	public int turnOn() throws BusException;
}