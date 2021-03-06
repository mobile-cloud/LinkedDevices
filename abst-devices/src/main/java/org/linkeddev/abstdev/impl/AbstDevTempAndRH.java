package org.linkeddev.abstdev.impl;

import java.util.Timer;
import java.util.TimerTask;

import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.BusListener;
import org.alljoyn.bus.Mutable;
import org.alljoyn.bus.SessionOpts;
import org.alljoyn.bus.SessionPortListener;
import org.alljoyn.bus.Status;
import org.linkeddev.abstdev.constant.*;

public class AbstDevTempAndRH {
	static {
		System.loadLibrary(NativeLib.ALLJOYN_JAVA);
	}
	private static boolean sessionEstablished = false;
	private static int sessionId;

	private static class TempAndRHBusListener extends BusListener {
		public void nameOwnerChanged(String busName, String previousOwner,
				String newOwner) {
			if (WellKnownName.ABST_DEV.equals(busName)) {
				System.out.println("BusAttachement.nameOwnerChanged(" + busName
						+ ", " + previousOwner + ", " + newOwner + ")");
			}
		}
	}

	public static void main(String[] args) {
		BusAttachment mBus = new BusAttachment(AppName.TEMP_AND_RH,
				BusAttachment.RemoteMessage.Receive);

		final TempAndRHImpl myTempAndRH = new TempAndRHImpl();

		Status status = mBus
				.registerBusObject(myTempAndRH, ObjPath.TEMP_AND_RH);
		if (status != Status.OK) {
			return;
		}
		System.out.println("BusAttachment.registerBusObject successful");

		BusListener listener = new TempAndRHBusListener();
		mBus.registerBusListener(listener);

		status = mBus.connect();
		if (status != Status.OK) {
			return;
		}
		System.out.println("BusAttachment.connect successful on "
				+ System.getProperty("org.alljoyn.bus.address"));

		Mutable.ShortValue contactPort = new Mutable.ShortValue(
				ContactPort.TEMP_AND_RH);

		SessionOpts sessionOpts = new SessionOpts();
		sessionOpts.traffic = SessionOpts.TRAFFIC_MESSAGES;
		sessionOpts.isMultipoint = false;
		sessionOpts.proximity = SessionOpts.PROXIMITY_ANY;
		sessionOpts.transports = SessionOpts.TRANSPORT_ANY;

		status = mBus.bindSessionPort(contactPort, sessionOpts,
				new SessionPortListener() {
					public boolean acceptSessionJoiner(short sessionPort,
							String joiner, SessionOpts sessionOpts) {
						System.out
								.println("SessionPortListener.acceptSessionJoiner called");
						if (sessionPort == ContactPort.TEMP_AND_RH) {
							return true;
						} else {
							return false;
						}
					}

					public void sessionJoined(short sessionPort, int id,
							String joiner) {
						System.out.println(String
								.format("SessionPortListener.sessionJoined(%d, %d, %s)",
										sessionPort, id, joiner));
						sessionId = id;
						sessionEstablished = true;
					}
				});
		if (status != Status.OK) {
			return;
		}
		System.out.println("BusAttachment.bindSessionPort successful");

		int flags = 0; // do not use any request name flags
		status = mBus.requestName(WellKnownName.ABST_DEV, flags);
		if (status != Status.OK) {
			return;
		}
		System.out.println("BusAttachment.request " + WellKnownName.ABST_DEV
				+ " successful");

		status = mBus.advertiseName(WellKnownName.ABST_DEV,
				SessionOpts.TRANSPORT_ANY);
		if (status != Status.OK) {
			System.out.println("Status = " + status);
			mBus.releaseName(WellKnownName.ABST_DEV);
			return;
		}
		System.out.println("BusAttachment.advertiseName "
				+ WellKnownName.ABST_DEV + " successful");

		while (!sessionEstablished) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("Thead Exception caught");
				e.printStackTrace();
			}
		}
		System.out.println("BusAttachment session established");

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				myTempAndRH.bufferTempAndRH();
			}
		}, 0, 5000); // Time interval of updating buffer (5 seconds)

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
