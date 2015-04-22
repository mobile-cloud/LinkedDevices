package org.linkeddev.abstdev.client;

import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusListener;
import org.alljoyn.bus.Mutable;
import org.alljoyn.bus.ProxyBusObject;
import org.alljoyn.bus.SessionListener;
import org.alljoyn.bus.SessionOpts;
import org.alljoyn.bus.Status;
import org.linkeddev.abstdev.ITempAndRH;
import org.linkeddev.abstdev.constant.*;

public class TempAndRHClient {
	static {
		System.loadLibrary(NativeLib.ALLJOYN_JAVA);
	}

	static BusAttachment mBus;

	private static ProxyBusObject mProxyObj;
	private static ITempAndRH mTempAndRHInterface;

	private static boolean isJoined = false;

	static class MyBusListener extends BusListener {
		public void foundAdvertisedName(String name, short transport, String namePrefix) {
			System.out.println(String.format("BusListener.foundAdvertisedName(%s, %d, %s)", name, transport, namePrefix));
			short contactPort = ContactPort.TEMP_AND_RH;
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

			mProxyObj = mBus.getProxyBusObject(WellKnownName.ABST_DEV, ObjPath.TEMP_AND_RH, sessionId.value, new Class<?>[] { ITempAndRH.class });

			mTempAndRHInterface = mProxyObj.getInterface(ITempAndRH.class);
			isJoined = true;

		}

		public void nameOwnerChanged(String busName, String previousOwner, String newOwner) {
			if (WellKnownName.ABST_DEV.equals(busName)) {
				System.out.println("BusAttachement.nameOwnerChagned(" + busName + ", " + previousOwner + ", " + newOwner + ")");
			}
		}

	}

	public static void main(String[] args) {
		mBus = new BusAttachment(AppName.TEMP_AND_RH, BusAttachment.RemoteMessage.Receive);

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
			while(true){
				System.out.println("Temp: " + mTempAndRHInterface.getTemp());
				System.out.println("RH: " + mTempAndRHInterface.getRH());
				Thread.sleep(2000);
			}
		} catch (BusException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
