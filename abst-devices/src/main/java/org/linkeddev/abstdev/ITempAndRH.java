package org.linkeddev.abstdev;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusMethod;

@BusInterface (name = "org.abstdev.businterface.tempandrh")
public interface ITempAndRH {
	@BusMethod
	public int getTemp() throws BusException;
	
	@BusMethod
	public int getRH() throws BusException;
}
