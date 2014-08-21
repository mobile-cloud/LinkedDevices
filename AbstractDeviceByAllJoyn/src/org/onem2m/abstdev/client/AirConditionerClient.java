package org.onem2m.abstdev.client;

import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusListener;
import org.alljoyn.bus.Mutable;
import org.alljoyn.bus.ProxyBusObject;
import org.alljoyn.bus.SessionListener;
import org.alljoyn.bus.SessionOpts;
import org.alljoyn.bus.Status;
import org.onem2m.abstdev.IAirConditioner;
import org.onem2m.abstdev.ITempAndRH;
import org.onem2m.abstdev.constant.*;

public class AirConditionerClient {
	static {
		System.loadLibrary(NativeLib.ALLJOYN_JAVA);
	}
	
	static BusAttachment mBus;

	private static ProxyBusObject mProxyObj;
	private static IAirConditioner mACInterface;

	private static boolean isJoined = false;
	
	static class MyBusListener extends BusListener {
		public void foundAdvertisedName(String name, short transport, String namePrefix) {
			System.out.println(String.format("BusListener.foundAdvertisedName(%s, %d, %s)", name, transport, namePrefix));
			short contactPort = ContactPort.AIR_CONDITIONER;
			SessionOpts sessionOpts = new SessionOpts();
			sessionOpts.traffic = SessionOpts.TRAFFIC_MESSAGES;
			sessionOpts.isMultipoint = false;
			sessionOpts.proximity = SessionOpts.PROXIMITY_ANY;
			sessionOpts.transports = SessionOpts.TRANSPORT_ANY;

			Mutable.IntegerValue sessionId = new Mutable.IntegerValue();

			mBus.enableConcurrentCallbacks();

			Status status = mBus.joinSession(name, contactPort, sessionId, sessionOpts, new SessionListener());
			if (status != Status.OK) {
				return;
			}
			System.out.println(String.format("BusAttachement.joinSession successful sessionId = %d", sessionId.value));

			mProxyObj = mBus.getProxyBusObject(WellKnownName.ABST_DEV, ObjPath.AIR_CONDITIONER, sessionId.value, new Class<?>[] { IAirConditioner.class });

			mACInterface = mProxyObj.getInterface(IAirConditioner.class);
			isJoined = true;

		}

		public void nameOwnerChanged(String busName, String previousOwner, String newOwner) {
			if (WellKnownName.ABST_DEV.equals(busName)) {
				System.out.println("BusAttachement.nameOwnerChagned(" + busName + ", " + previousOwner + ", " + newOwner + ")");
			}
		}
	}
	
	public static void main(String[] args) {
		mBus = new BusAttachment(AppName.AIR_CONDITIONER, BusAttachment.RemoteMessage.Receive);
		
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
		
		while (!isJoined) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("Program interupted");
			}
		}
		
		try {
			System.out.println(mACInterface.turnOn());
			System.out.println(mACInterface.turnOff());
			System.out.println(mACInterface.getTemp());
			System.out.println(mACInterface.setTemp(26));
			System.out.println(mACInterface.increaseWind());
			System.out.println(mACInterface.decreaseWind());
		} catch (BusException e) {
			e.printStackTrace();
		}
	}

}
