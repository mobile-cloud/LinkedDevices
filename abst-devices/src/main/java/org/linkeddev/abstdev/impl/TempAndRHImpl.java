package org.linkeddev.abstdev.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;
import org.linkeddev.abstdev.ITempAndRH;
import org.linkeddev.abstdev.constant.NativeLib;

class TempAndRHImpl implements ITempAndRH, BusObject {
	static {
		System.loadLibrary(NativeLib.TEMP_AND_RH);
	}

	private int buffTemp;
	private int buffRH;

	public TempAndRHImpl() {
		buffTemp = -99;
		buffRH = -99;
	}

	@Override
	public int getTemp() throws BusException {
		return buffTemp;
	}

	@Override
	public int getRH() throws BusException {
		return buffRH;
	}

	public void bufferTempAndRH() {
		// buffTemp = getTempNative();
		// buffRH = getRHNative();
		int sensorType = 11;
		int gpio = 4;
		String AdafruitDHTPath = "/home/pi/DHT11/Adafruit_Python_DHT/examples/AdafruitDHT.py";
		try {
			Process p = Runtime.getRuntime().exec(
					"sudo python " + AdafruitDHTPath + " " + sensorType + " "
							+ gpio);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String pyOut = in.readLine();
			String[] pyOutArr = pyOut.split(",");
			try {
				this.buffTemp = Integer.parseInt(pyOutArr[0].split("=")[1]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			try {
				this.buffRH = Integer.parseInt(pyOutArr[1].split("=")[1]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// private native int getTempNative();
	// private native int getRHNative();

}
