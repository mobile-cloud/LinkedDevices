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
	public int getOnOff() throws BusException;
	
	@BusMethod
	public int getTemp() throws BusException;
	
	@BusMethod
	public int setTemp(int temp) throws BusException;
	
	@BusMethod
	public int getWindLevel() throws BusException;
	
	@BusMethod
	public int increaseWind() throws BusException;
	
	@BusMethod
	public int decreaseWind() throws BusException;
	
	@BusMethod
	public int cooling() throws BusException;
	
	@BusMethod
	public int heating() throws BusException;
	
	@BusMethod
	public String getCondition() throws BusException;
}
