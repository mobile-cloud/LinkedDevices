package org.linkeddev.ws.representations;

public class Humidifier {
    public static final int On = 1;
    public static final int Off = 0;
    public static final int defaultHumidity = 50;
    
    private int onOff;
    private int humidity;
    
    public Humidifier() {
        this.onOff = Humidifier.Off;
        this.humidity = Humidifier.defaultHumidity;
    }
    
    public Humidifier(int onOff, int humidity) {
        this.onOff = onOff;
        this.humidity = humidity;
    }

    public int getOnOff() {
        return onOff;
    }

    public void setOnOff(int onOff) {
        this.onOff = onOff;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
