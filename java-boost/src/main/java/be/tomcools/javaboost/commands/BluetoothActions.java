package be.tomcools.javaboost.commands;

import be.tomcools.javaboost.models.Device;

import java.util.List;

public interface BluetoothActions {

    List<Device> scanLowEnergyDevices();
}
