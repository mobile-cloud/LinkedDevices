package org.linkeddev.abstdev.impl;

import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.BusListener;
import org.alljoyn.bus.Mutable;
import org.alljoyn.bus.SessionOpts;
import org.alljoyn.bus.SessionPortListener;
import org.alljoyn.bus.Status;
import org.linkeddev.abstdev.constant.*;

public class AbstDevHumidifier {
	static { 
        System.loadLibrary(NativeLib.ALLJOYN_JAVA);
    }
	static boolean sessionEstablished = false;
    static int sessionId;
    private static class HumidifierBusListener extends BusListener {
    	public void nameOwnerChanged(String busName, String previousOwner, String newOwner) {
            if (WellKnownName.ABST_DEV.equals(busName)) {
                System.out.println("BusAttachement.nameOwnerChanged(" + busName + ", " + previousOwner + ", " + newOwner+")");
            }
        }
    }
    public static void main(String[] args){
    	BusAttachment mBus = new BusAttachment(AppName.HUMIDIFIER, BusAttachment.RemoteMessage.Receive);

    	HumidifierImpl myHumidifier = new HumidifierImpl();

        Status status = mBus.registerBusObject(myHumidifier, ObjPath.HUMIDIFIER);
        if (status != Status.OK) {            
            return;
        }
        System.out.println("BusAttachment.registerBusObject successful");
        
        BusListener listener = new HumidifierBusListener();
        mBus.registerBusListener(listener);

        status = mBus.connect();
        if (status != Status.OK) {
            return;
        }
        System.out.println("BusAttachment.connect successful on " + System.getProperty("org.alljoyn.bus.address"));        

        Mutable.ShortValue contactPort = new Mutable.ShortValue(ContactPort.HUMIDIFIER);
        SessionOpts sessionOpts = new SessionOpts();
        sessionOpts.traffic = SessionOpts.TRAFFIC_MESSAGES;
        sessionOpts.isMultipoint = false;
        sessionOpts.proximity = SessionOpts.PROXIMITY_ANY;
        sessionOpts.transports = SessionOpts.TRANSPORT_ANY;

        status = mBus.bindSessionPort(contactPort, sessionOpts, 
                new SessionPortListener() {
            public boolean acceptSessionJoiner(short sessionPort, String joiner, SessionOpts sessionOpts) {
                System.out.println("SessionPortListener.acceptSessionJoiner called");
                if (sessionPort == ContactPort.HUMIDIFIER) {
                    return true;
                } else {
                    return false;
                }
            }
            public void sessionJoined(short sessionPort, int id, String joiner) {
                System.out.println(String.format("SessionPortListener.sessionJoined(%d, %d, %s)", sessionPort, id, joiner));
                sessionId = id;
                sessionEstablished = true;
            }
        });
        
        
        if (status != Status.OK) {
            return;
        }
        System.out.println("BusAttachment.bindSessionPort successful");

        int flags = 0; //do not use any request name flags
        status = mBus.requestName(WellKnownName.ABST_DEV, flags);
        if (status != Status.OK) {
            return;
        }
        System.out.println("BusAttachment.request "+WellKnownName.ABST_DEV+" successful");

        status = mBus.advertiseName(WellKnownName.ABST_DEV, SessionOpts.TRANSPORT_ANY);
        if (status != Status.OK) {
            System.out.println("Status = " + status);
            mBus.releaseName(WellKnownName.ABST_DEV);
            return;
        }
        System.out.println("BusAttachment.advertiseName "+WellKnownName.ABST_DEV+" successful");

        while (!sessionEstablished) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thead Exception caught");
                e.printStackTrace();
            }
        }
        System.out.println("BusAttachment session established");

        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("Thead Exception caught");
                e.printStackTrace();
            }
        }

    }
    
}
