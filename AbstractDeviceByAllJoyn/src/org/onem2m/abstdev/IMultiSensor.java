package org.onem2m.abstdev;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusMethod;

@BusInterface (name = "org.abstdev.businterface.multisensor")
public interface IMultiSensor {
/*	@BusMethod
	public float getLM35Temp() throws BusException;
	@BusMethod
	public float getDHT11Temp() throws BusException;
	@BusMethod
	public float getDHT11Humi() throws BusException;
	@BusMethod
	public int getDB() throws BusException;*/
	@BusMethod
	public String getLM35Temp() throws BusException;
	@BusMethod
	public String getDHT11Temp() throws BusException;
	@BusMethod
	public String getDHT11Humi() throws BusException;
	@BusMethod
	public String getDB() throws BusException;

}
