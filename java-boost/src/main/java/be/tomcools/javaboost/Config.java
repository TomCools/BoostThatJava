package be.tomcools.javaboost;

/**
 * Created by tomco on 25/10/2017.
 */
public class Config {
    private String bluetoothInterface = "hci0";
    private String deviceID = "00:16:53:A3:63:CA";
    private String handle = "0x0e";

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
        return handle;
    }
}
