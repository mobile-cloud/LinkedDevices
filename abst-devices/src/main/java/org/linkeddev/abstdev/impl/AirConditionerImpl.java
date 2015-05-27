package org.linkeddev.abstdev.impl;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;
import org.linkeddev.abstdev.IAirConditioner;
import org.linkeddev.abstdev.constant.NativeLib;
import org.linkeddev.ws.representations.AirConditioner;

class AirConditionerImpl implements IAirConditioner, BusObject {
	static{
		System.loadLibrary(NativeLib.ALLJOYN_JAVA);
	}
	
	private AirConditioner AC = new AirConditioner();

	@Override
	public int turnOn() throws BusException {
		System.out.println("AirConditioner.turnOn() is invoked.");
		this.AC.setOnOff(AirConditioner.On);
		return this.AC.getOnOff();
	}

	@Override
	public int turnOff() throws BusException {
		System.out.println("AirConditioner.turnOff() is invoked.");
		this.AC.setOnOff(AirConditioner.Off);
		return this.AC.getOnOff();
	}

	@Override
	public int getTemp() throws BusException {
		System.out.println("AirConditioner.getTemp() is invoked.");
		return this.AC.getTemp();
	}

	@Override
	public int setTemp(int temp) throws BusException {
		System.out.println("AirConditioner.setTemp() is invoked.");
		this.AC.setTemp(temp);
		return 0;
	}

	@Override
	public int increaseWind() throws BusException {
		System.out.println("AirConditioner.increaseWind() is invoked.");
		int currentWind = this.AC.getWindLevel();
		currentWind += 1;
		this.AC.setWindLevel(currentWind > AirConditioner.highestWindLevel ? AirConditioner.highestWindLevel : currentWind);
		return this.AC.getWindLevel();
	}

	@Override
	public int decreaseWind() throws BusException {
		System.out.println("AirConditioner.decreaseWind() is invoked.");
		int currentWind = this.AC.getWindLevel();
		currentWind -= 1;
		this.AC.setWindLevel(currentWind < AirConditioner.lowestWindLevel ? AirConditioner.lowestWindLevel : currentWind);
		return this.AC.getWindLevel();
	}

	@Override
	public int cooling() throws BusException {
		System.out.println("AirConditioner.cooling() is invoked.");
		this.AC.setCondition(AirConditioner.CoolingCondition);
		return 0;
	}

	@Override
	public int heating() throws BusException {
		System.out.println("AirConditioner.heating() is invoked.");
		this.AC.setCondition(AirConditioner.HeatingCondition);
		return 0;
	}
	
	@Override
    public int blowing() throws BusException {
        System.out.println("AirConditioner.blowing() is invoked.");
        this.AC.setCondition(AirConditioner.BlowingCondition);
        return 0;
    }

	@Override
	public int getWindLevel() throws BusException {
		System.out.println("AirConditioner.getWindLevel() is invoked.");
		return this.AC.getWindLevel();
	}

	@Override
	public int getOnOff() throws BusException {
		System.out.println("AirConditioner.getOnOff() is invoked.");
		return this.AC.getOnOff();
	}

	@Override
	public String getCondition() throws BusException {
		System.out.println("AirConditioner.getCondition() is invoked.");
		return this.AC.getCondition();
	}
}
