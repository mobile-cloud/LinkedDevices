package org.linkeddev.abstdev.impl;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;
import org.linkeddev.abstdev.IHumidifier;
import org.linkeddev.abstdev.constant.NativeLib;
import org.linkeddev.ws.representations.Humidifier;

class HumidifierImpl implements IHumidifier,BusObject {
	static{
		System.loadLibrary(NativeLib.ALLJOYN_JAVA);
	}
	
	private Humidifier HUMI = new Humidifier();

	@Override
	public int getHumidity() throws BusException {
		System.out.println("Humidifier.getHumi() is invoked");
		return this.HUMI.getHumidity();
	}

	@Override
	public int setHumidity(int humi) throws BusException {
		System.out.println("Humidifier.setHumi() is invoked");
		this.HUMI.setHumidity(humi);
		return 0;
	}

	@Override
	public int turnOff() throws BusException {
		System.out.println("Humidifier.turnOff() is invoked");
		this.HUMI.setOnOff(Humidifier.Off);
		return this.HUMI.getOnOff();
	}

	@Override
	public int turnOn() throws BusException {
		System.out.println("Humdifier.turnOn() is invoked");
		this.HUMI.setOnOff(Humidifier.On);
		return this.HUMI.getOnOff();
	}

    @Override
    public int getOnOff() throws BusException {
        System.out.println("Humidifier.getOnOff() is invoked.");
        return this.HUMI.getOnOff();
    }
}
