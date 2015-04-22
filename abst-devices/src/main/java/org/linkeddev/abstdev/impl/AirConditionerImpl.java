package org.linkeddev.abstdev.impl;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;
import org.linkeddev.abstdev.IAirConditioner;
import org.linkeddev.abstdev.constant.NativeLib;

class AirConditionerImpl implements IAirConditioner, BusObject {
	static{
		System.loadLibrary(NativeLib.ALLJOYN_JAVA);
	}

	@Override
	public int turnOn() throws BusException {
		System.out.println("AirConditioner -> turnOn() is invoked.");
		return 0;
	}

	@Override
	public int turnOff() throws BusException {
		System.out.println("AirConditioner -> turnOff() is invoked.");
		return 0;
	}

	@Override
	public int getTemp() throws BusException {
		System.out.println("AirConditioner -> getTemp() is invoked.");
		return 0;
	}

	@Override
	public int setTemp(int temp) throws BusException {
		System.out.println("AirConditioner -> setTemp() is invoked.");
		return 0;
	}

	@Override
	public int increaseWind() throws BusException {
		System.out.println("AirConditioner -> increaseWind() is invoked.");
		return 0;
	}

	@Override
	public int decreaseWind() throws BusException {
		System.out.println("AirConditioner -> decreaseWind() is invoked.");
		return 0;
	}
	
}
