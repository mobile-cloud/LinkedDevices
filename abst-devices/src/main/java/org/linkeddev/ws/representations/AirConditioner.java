package org.linkeddev.ws.representations;

public class AirConditioner {
	public static final int On = 1;
	public static final int Off = 0;
	public static final int defaultTemp = 25;
	public static final int defaultWindLevel = 2;
	public static final int highestWindLevel = 3;
	public static final int lowestWindLevel = 1;
	public static final String defaultCondition = "COOLING";
	public static final String CoolingCondition = "COOLING";
	public static final String HeatingCondition = "HEATING";
	public static final String BlowingCondition = "BLOWING";

	private int onOff;
	private int temp;
	private int windLevel;
	private String condition;

	public AirConditioner() {
		this.onOff = AirConditioner.Off;
		this.temp = AirConditioner.defaultTemp;
		this.windLevel = AirConditioner.defaultWindLevel;
		this.condition = AirConditioner.defaultCondition;
	}
	
	public AirConditioner(int onOff, int temp, int windLevel, String condition) {
		this.onOff = onOff;
		this.temp = temp;
		this.windLevel = windLevel;
		this.condition = condition;
	}

	public int getOnOff() {
		return onOff;
	}

	public void setOnOff(int onOff) {
		this.onOff = onOff;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public int getWindLevel() {
		return windLevel;
	}

	public void setWindLevel(int windLevel) {
		this.windLevel = windLevel;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
}
