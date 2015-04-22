package org.linkeddev.abstdev.impl;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;
import org.linkeddev.abstdev.ITempAndRH;
import org.linkeddev.abstdev.constant.NativeLib;

class TempAndRHImpl implements ITempAndRH, BusObject{
	static{
		System.loadLibrary(NativeLib.TEMP_AND_RH);
	}
	
	private int buffTemp;
	private int buffRH;
	
	public TempAndRHImpl(){
		buffTemp=-99;
		buffRH=-99;
	}
	
	@Override
	public int getTemp() throws BusException {
		return buffTemp;
	}

	@Override
	public int getRH() throws BusException {
		return buffRH;
	}
	
	public void bufferTempAndRH(){
		buffTemp=getTempNative();
		buffRH=getRHNative();
	}
	
	private native int getTempNative();
	private native int getRHNative();
}
