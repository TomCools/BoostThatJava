Boost Device ID = 00:16:53:A4:CD:7E
Drive Forward Command / Vernie Backward command = 0c018139110a00069B9B647f03

#Assuming Bluetooth device is hci1
sudo hcitool lescan
gatttool -i hci0 -b 00:16:53:A3:63:CA --char-write-req --handle=0x0e --value=0c018139110a00069B9B647f03

#gatttool interactive mode
connect 00:16:53:A3:63:CA
char-write-req 0x0e 0c018139110a00069B9B647f

#Vernie
Forward 1 Second: 0c0081391109e80364647f03
Spin head for 1 second: 0c0081011109e80364647f03 (motor c)
                  0c0081021109e80364647f03
Backward 1 Second: 0c018139110a00069B9B647f03
360 degree (aprox), clockwize: 0d008139110ae803649b647f03
Smal step forward: 0e008139110b5a0000000e647f03
short turn clockwize: 0f008139110c5a0000000ef1647f03

Spin a lot to the left: 0c018139110a80099B64647f03
Trigger notification: 060001010200

0c018139110a00069B9B647f03
### Main resources
https://github.com/JorgePe/BOOSTreveng
https://github.com/pcborenstein/bluezDoc/wiki/hcitool-and-gatttool-example
https://software.intel.com/en-us/java-for-bluetooth-le-apps
https://github.com/jasongin/noble-uwp