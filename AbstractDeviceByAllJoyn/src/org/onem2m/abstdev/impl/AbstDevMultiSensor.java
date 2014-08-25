package org.onem2m.abstdev.impl;

/*
 * Copyright (c) 2010-2011, AllSeen Alliance. All rights reserved.
 *
 *    Permission to use, copy, modify, and/or distribute this software for any
 *    purpose with or without fee is hereby granted, provided that the above
 *    copyright notice and this permission notice appear in all copies.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 *    WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 *    MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 *    ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 *    WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 *    ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 *    OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusListener;
import org.alljoyn.bus.BusObject;
import org.alljoyn.bus.Mutable;
import org.alljoyn.bus.SessionOpts;
import org.alljoyn.bus.SessionPortListener;
import org.alljoyn.bus.Status;
import org.alljoyn.bus.annotation.BusMethod;

import serial.SerialMultiSensor;

import org.onem2m.abstdev.IMultiSensor;
import org.onem2m.abstdev.constant.*;
public class AbstDevMultiSensor {
	//////////////
	static SerialMultiSensor sms;
	///////////////
    static { 
        System.loadLibrary("alljoyn_java");
    }

    static boolean sessionEstablished = false;
    static int sessionId;
    
    public static class IMultiSensorService implements IMultiSensor, BusObject {	
		@Override
		public String getLM35Temp() throws BusException { 
			return sms.getLM35Temp();
		}

		@Override
		public String getDHT11Temp() throws BusException {
			return sms.getDHT11Temp();
		}

		@Override
		public String getDHT11Humi() throws BusException {
			return sms.getDHT11Humi();
		}

		@Override
		public String getDB() throws BusException {
			return sms.getDB();
		}
    }
    

    private static class MyBusListener extends BusListener {
        public void nameOwnerChanged(String busName, String previousOwner, String newOwner) {
            if (WellKnownName.ABST_DEV.equals(busName)) {
                System.out.println("BusAttachement.nameOwnerChanged(" + busName + ", " + previousOwner + ", " + newOwner);
            }
        }
    }

    public static void main(String[] args) {
    	/////////////////////////
    	/*
    	 * serial part
    	 */
		sms = new SerialMultiSensor();
		sms.initialize();
		//////////////////////////////////////
        BusAttachment mBus;
        mBus = new BusAttachment("AppName", BusAttachment.RemoteMessage.Receive);

        Status status;

        IMultiSensorService myIMultiSensorService = new IMultiSensorService();

        status = mBus.registerBusObject(myIMultiSensorService, ObjPath.MULTISENSOR);
        if (status != Status.OK) {            
            return;
        }
        System.out.println("BusAttachment.registerBusObject successful");

        BusListener listener = new MyBusListener();
        mBus.registerBusListener(listener);

        status = mBus.connect();
        if (status != Status.OK) {

            return;
        }
        System.out.println("BusAttachment.connect successful on " + System.getProperty("org.alljoyn.bus.address"));        

        Mutable.ShortValue contactPort = new Mutable.ShortValue(ContactPort.MULTISENSOR);

        SessionOpts sessionOpts = new SessionOpts();
        sessionOpts.traffic = SessionOpts.TRAFFIC_MESSAGES;
        sessionOpts.isMultipoint = false;
        sessionOpts.proximity = SessionOpts.PROXIMITY_ANY;
        sessionOpts.transports = SessionOpts.TRANSPORT_ANY;

        status = mBus.bindSessionPort(contactPort, sessionOpts, 
                new SessionPortListener() {
            public boolean acceptSessionJoiner(short sessionPort, String joiner, SessionOpts sessionOpts) {
                System.out.println("SessionPortListener.acceptSessionJoiner called");
                if (sessionPort ==ContactPort.MULTISENSOR) {
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
        System.out.println("BusAttachment.request '"+WellKnownName.ABST_DEV+"' successful");

        status = mBus.advertiseName(WellKnownName.ABST_DEV, SessionOpts.TRANSPORT_ANY);
        if (status != Status.OK) {
            System.out.println("Status = " + status);
            mBus.releaseName(WellKnownName.ABST_DEV);
            return;
        }
        System.out.println("BusAttachment.advertiseName '"+WellKnownName.ABST_DEV+"' successful");

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
