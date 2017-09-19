Boost Device ID = 00:16:53:A4:CD:7E
Drive Forward Command = 0c018139110a00069B9B647f03

#Assuming Bluetooth device is hci1
gatttool -i hci1 -b 00:16:53:A4:CD:7E --char-write-req --handle=0x0e --value=0c018139110a00069B9B647f03





### Main resources
https://github.com/JorgePe/BOOSTreveng
https://github.com/pcborenstein/bluezDoc/wiki/hcitool-and-gatttool-example
https://software.intel.com/en-us/java-for-bluetooth-le-apps
https://github.com/jasongin/noble-uwp