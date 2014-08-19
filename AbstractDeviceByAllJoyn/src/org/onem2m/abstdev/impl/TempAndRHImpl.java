package org.onem2m.abstdev.impl;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;
import org.onem2m.abstdev.ITempAndRH;
import org.onem2m.abstdev.constant.NativeLib;

class TempAndRHImpl implements ITempAndRH, BusObject{
	static{
		System.loadLibrary(NativeLib.TEMP_AND_RH);
	}
	@Override
	public int getTemp() throws BusException {
		return getTempNative();
	}

	@Override
	public int getRH() throws BusException {
		return getRHNative();
	}
	
	private native int getTempNative();
	private native int getRHNative();
	
}
