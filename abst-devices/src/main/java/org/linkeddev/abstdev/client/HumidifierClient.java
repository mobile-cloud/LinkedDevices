package org.linkeddev.abstdev.client;

import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusListener;
import org.alljoyn.bus.Mutable;
import org.alljoyn.bus.ProxyBusObject;
import org.alljoyn.bus.SessionListener;
import org.alljoyn.bus.SessionOpts;
import org.alljoyn.bus.Status;
import org.linkeddev.abstdev.IHumidifier;
import org.linkeddev.abstdev.constant.AppName;
import org.linkeddev.abstdev.constant.ContactPort;
import org.linkeddev.abstdev.constant.NativeLib;
import org.linkeddev.abstdev.constant.ObjPath;
import org.linkeddev.abstdev.constant.WellKnownName;

public class HumidifierClient {
    static {
        System.loadLibrary(NativeLib.ALLJOYN_JAVA);
    }
    static BusAttachment mBus;

    private static ProxyBusObject mProxyObj;
    public static IHumidifier mHumidifier;

    private static boolean isJoined = false;

    static class MyBusListener extends BusListener {
        public void foundAdvertisedName(String name, short transport,
                String namePrefix) {
            System.out.println(String.format(
                    "BusListener.findAdvertisedName(%s,%d,%s)", name,
                    transport, namePrefix));
            short contactPort = ContactPort.HUMIDIFIER;
            SessionOpts sessionOpts = new SessionOpts();
            sessionOpts.traffic = SessionOpts.TRAFFIC_MESSAGES;
            sessionOpts.isMultipoint = false;
            sessionOpts.proximity = SessionOpts.PROXIMITY_ANY;
            sessionOpts.transports = SessionOpts.TRANSPORT_ANY;

            Mutable.IntegerValue sessionId = new Mutable.IntegerValue();

            mBus.enableConcurrentCallbacks();

            Status status = mBus.joinSession(name, contactPort, sessionId,
                    sessionOpts, new SessionListener());
            if (status != Status.OK) {
                return;
            }
            System.out.println(String.format(
                    "BusAttachement.joinSession successful sessionId = %d",
                    sessionId.value));

            mProxyObj = mBus.getProxyBusObject(WellKnownName.ABST_DEV,
                    ObjPath.HUMIDIFIER, sessionId.value,
                    new Class<?>[] { IHumidifier.class });

            mHumidifier = mProxyObj.getInterface(IHumidifier.class);
            isJoined = true;
        }

        public void nameOwnerChanged(String busName, String previousOwner,
                String newOwner) {
            if (WellKnownName.ABST_DEV.equals(busName)) {
                System.out.println("BusAttachement.nameOwnerChagned(" + busName
                        + ", " + previousOwner + ", " + newOwner + ")");
            }
        }
    }

    public static void run() {
        mBus = new BusAttachment(AppName.HUMIDIFIER,
                BusAttachment.RemoteMessage.Receive);

        BusListener listener = new MyBusListener();
        mBus.registerBusListener(listener);

        Status status = mBus.connect();
        if (status != Status.OK) {
            return;
        }

        System.out.println("BusAttachment.connect successful on "
                + System.getProperty("org.alljoyn.bus.address"));

        status = mBus.findAdvertisedName(WellKnownName.ABST_DEV);
        if (status != Status.OK) {
            return;
        }
        System.out.println("BusAttachment.findAdvertisedName successful "
                + WellKnownName.ABST_DEV);

        while (!isJoined) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Program interupted");
            }
        }
    }

    public static void main(String[] args) {
        mBus = new BusAttachment(AppName.HUMIDIFIER,
                BusAttachment.RemoteMessage.Receive);

        BusListener listener = new MyBusListener();
        mBus.registerBusListener(listener);

        Status status = mBus.connect();
        if (status != Status.OK) {
            return;
        }

        System.out.println("BusAttachment.connect successful on "
                + System.getProperty("org.alljoyn.bus.address"));

        status = mBus.findAdvertisedName(WellKnownName.ABST_DEV);
        if (status != Status.OK) {
            return;
        }
        System.out.println("BusAttachment.findAdvertisedName successful "
                + WellKnownName.ABST_DEV);

        while (!isJoined) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Program interupted");
            }
        }

        try {
            System.out.println(mHumidifier.turnOff());
            System.out.println(mHumidifier.turnOn());
            System.out.println(mHumidifier.getHumidity());
            System.out.println(mHumidifier.setHumidity(18));

        } catch (BusException e) {
            e.printStackTrace();
        }
    }

}
