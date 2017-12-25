package be.tomcools.javaboost.commands;

import be.tomcools.javaboost.models.Device;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class HcitoolCommandWrapper implements BluetoothActions {
    @Override
    public List<Device> scanLowEnergyDevices() {
        String devices = this.scanForDevices(3);
        System.out.println(devices);
        return null;
    }

    private String scanForDevices(int scanTimeInSeconds) {
        System.out.println("Scanning for devices...");
        ProcessBuilder ps = new ProcessBuilder("sudo", "hcitool", "lescan");
        ps.redirectErrorStream(true);
        StringBuilder sb = new StringBuilder();
        Future<?> future = Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Process pr = ps.start();
                    BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println("Read line: " + line);
                        sb.append(line).append(System.lineSeparator());
                    }
                    System.out.println("Waiting for process.");
                    pr.waitFor(scanTimeInSeconds, TimeUnit.SECONDS);
                    pr.destroy();
                    System.out.println("ok!");

                    in.close();
                } catch (Exception ex) {
                    throw new IllegalStateException(ex);
                }
            }
        });
        try {
            future.wait(3000);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }

        return sb.toString();
    }
}
