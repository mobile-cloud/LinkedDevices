package org.linkeddev.abstdev.impl;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;
import org.linkeddev.abstdev.IHumidifier;
import org.linkeddev.abstdev.constant.NativeLib;

class HumidifierImpl implements IHumidifier,BusObject {
	static{
		System.loadLibrary(NativeLib.ALLJOYN_JAVA);
	}

	@Override
	public int getHumidifier() throws BusException {
		System.out.println("Humidifier.getHumi() is invoked");
		return 0;
	}

	@Override
	public int setHumidifier(int humi) throws BusException {
		System.out.println("Humidifier.setHumi() is invoked");
		return 0;
	}

	@Override
	public int turnOff() throws BusException {
		System.out.println("Humidifier.turnOff() is invoked");
		return 0;
	}

	@Override
	public int turnOn() throws BusException {
		System.out.println("Humdifier.turnOn() is invoked");
		return 0;
	}
}
