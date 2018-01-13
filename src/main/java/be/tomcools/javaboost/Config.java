package be.tomcools.javaboost;

/**
 * Created by tomco on 25/10/2017.
 */
public class Config {
    //Bluetooth Handle for LEGO BOOST. Is the same for every lego boost out there.
    private static final String HANDLE = "0x0e";
    //Interface depends on the machine this code is running on
    private String bluetoothInterface = "hci0";
    //DeviceID is specific for our own Lego Boost.
    private String deviceID = "00:16:53:A3:63:CA";


    private static Config CONFIG = new Config();

    public static void setCONFIG(Config CONFIG) {
        Config.CONFIG = CONFIG;
    }

    public static Config getConfig() {
        return CONFIG;
    }

    public String getBluetoothInterface() {
        return bluetoothInterface;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getHandle() {
        return HANDLE;
    }
}
