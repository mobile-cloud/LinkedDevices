package org.linkeddev.abstdev.client;

import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusListener;
import org.alljoyn.bus.Mutable;
import org.alljoyn.bus.ProxyBusObject;
import org.alljoyn.bus.SessionListener;
import org.alljoyn.bus.SessionOpts;
import org.alljoyn.bus.Status;
import org.linkeddev.abstdev.IMultiSensor;
import org.linkeddev.abstdev.constant.*;

public class MultiSensorClient {
    static { 
        System.loadLibrary("alljoyn_java");
    }
    
    static BusAttachment mBus;
    
    private static ProxyBusObject mProxyObj;
    private static IMultiSensor mIMultiSensor;
    
    private static boolean isJoined = false;
    
    static class MyBusListener extends BusListener {
        public void foundAdvertisedName(String name, short transport, String namePrefix) {
            System.out.println(String.format("BusListener.foundAdvertisedName(%s, %d, %s)", name, transport, namePrefix));
            short contactPort = ContactPort.MULTISENSOR;
            SessionOpts sessionOpts = new SessionOpts();
            sessionOpts.traffic = SessionOpts.TRAFFIC_MESSAGES;
            sessionOpts.isMultipoint = false;
            sessionOpts.proximity = SessionOpts.PROXIMITY_ANY;
            sessionOpts.transports = SessionOpts.TRANSPORT_ANY;
            
            Mutable.IntegerValue sessionId = new Mutable.IntegerValue();
            
            mBus.enableConcurrentCallbacks();
            
            Status status = mBus.joinSession(name, contactPort, sessionId, sessionOpts,    new SessionListener());
            if (status != Status.OK) {
                return;
            }
            System.out.println(String.format("BusAttachement.joinSession successful sessionId = %d", sessionId.value));
            
            mProxyObj =  mBus.getProxyBusObject(WellKnownName.ABST_DEV,
                                                ObjPath.MULTISENSOR,
                                                sessionId.value,
                                                new Class<?>[] { IMultiSensor.class});

            mIMultiSensor = mProxyObj.getInterface(IMultiSensor.class);
            isJoined = true;
            
        }
        public void nameOwnerChanged(String busName, String previousOwner, String newOwner){
            if (WellKnownName.ABST_DEV.equals(busName)) {
                System.out.println("BusAttachement.nameOwnerChagned(" + busName + ", " + previousOwner + ", " + newOwner+")");
            }
        }
        
    }


    public static void main(String[] args) {
        mBus = new BusAttachment("AppName", BusAttachment.RemoteMessage.Receive);
        
        BusListener listener = new MyBusListener();
        mBus.registerBusListener(listener);
        
        Status status = mBus.connect();
        if (status != Status.OK) {
            return;
        }
        
        System.out.println("BusAttachment.connect successful on " + System.getProperty("org.alljoyn.bus.address"));
        
        status = mBus.findAdvertisedName(WellKnownName.ABST_DEV);
        if (status != Status.OK) {
            return;
        }
        System.out.println("BusAttachment.findAdvertisedName successful " + WellKnownName.ABST_DEV);
        
        while(!isJoined) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Program interupted");
            }
        }
        

        try {
			//for test
			System.out.println("current sensor data:");
			System.out.println("DataLM35:"+			mIMultiSensor.getLM35Temp());
			System.out.println("DataDHT11_Humi:"+	mIMultiSensor.getDHT11Humi());
			System.out.println("DataDHT11_Temp:"+	mIMultiSensor.getDHT11Temp());
			System.out.println("DataDB:"+			mIMultiSensor.getDB());
			
        } catch (BusException e1) {
            e1.printStackTrace();
        }


    }
}