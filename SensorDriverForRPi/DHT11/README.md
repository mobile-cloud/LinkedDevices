Abstract Device: Temp and RH
=========
Compile Java binding of bus methods
---------
> LinkedDevices/AbstractDeviceByAllJoyn/src $ make
Generate the JNI header file
---------
> LinkedDevices/SensorDriverForRPi/DHT11 $ javah -classpath ../../AbstractDeviceByAllJoyn/bin org.onem2m.abstdev.impl.TempAndRHImpl
Generate the shared library
---------
> LinkedDevices/SensorDriverForRPi/DHT11 $ gcc -fPIC -I /usr/lib/jvm/jdk-7-oracle-armhf/include/linux -I /usr/lib/jvm/jdk-7-oracle-armhf/include -shared -o libTempAndRH.so TempAndRH.c -lwiringPi
Switch to root and have fun!
---------
> LinkedDevices/SensorDriverForRPi/DHT11 $ su
> LinkedDevices/SensorDriverForRPi/DHT11 # export LD_LIBRARY_PATH=`pwd`:$AJ_ROOT/core/alljoyn/build/linux/arm/debug/dist/java/lib:$LD_LIBRARY_PATH
> LinkedDevices/SensorDriverForRPi/DHT11 # java -cp ../../AbstractDeviceByAllJoyn/bin:$CLASSPATH org.onem2m.abstdev.impl.AbstDevTempAndRH