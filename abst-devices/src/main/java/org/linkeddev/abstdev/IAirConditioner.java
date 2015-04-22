package org.linkeddev.abstdev;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusMethod;

@BusInterface (name = "org.abstdev.businterface.airconditioner")
public interface IAirConditioner {
	@BusMethod
	public int turnOn() throws BusException;
	
	@BusMethod
	public int turnOff() throws BusException;
	
	@BusMethod
	public int getTemp() throws BusException;
	
	@BusMethod
	public int setTemp(int temp) throws BusException;
	
	@BusMethod
	public int increaseWind() throws BusException;
	
	@BusMethod
	public int decreaseWind() throws BusException;
}
