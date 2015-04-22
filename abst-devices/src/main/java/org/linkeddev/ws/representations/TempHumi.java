package org.linkeddev.ws.representations;

public class TempHumi {
	private final int temp;
	private final int humi;
	
	public TempHumi(int temperature, int humidity) {
		this.temp = temperature;
		this.humi = humidity;
	}

	public int getTemp() {
		return temp;
	}

	public int getHumi() {
		return humi;
	}
}
