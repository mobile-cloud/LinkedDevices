package org.linkeddev.abstdev;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusMethod;

@BusInterface (name = "org.abstdev.businterface.temp")
public interface ITemp {
	@BusMethod
	public int getTemp() throws BusException;
	
}
