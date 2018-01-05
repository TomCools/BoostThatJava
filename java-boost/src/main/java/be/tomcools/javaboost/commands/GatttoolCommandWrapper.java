package be.tomcools.javaboost.commands;

import be.tomcools.javaboost.Config;

import java.io.IOException;
import java.util.concurrent.Executors;

public class GatttoolCommandWrapper {
    private CommandEncoder encoder = new CommandEncoder();

    public void motorAngle(Motor port, int angle, int dutyCycle) {
        this.executeCommand(encoder.encodeMotorAngle(port, angle, dutyCycle));
    }

    public void motorAngleMulti(int angle, int dutyCycleA, int dutyCycleB) {
        this.executeCommand(encoder.encodeMotorAngleMulti(angle, dutyCycleA, dutyCycleB));
    }

    public void motorTime(Motor port, int seconds, int dutyCycle) {
        // this.executeCommand(encoder.encodeMotorTime(port, seconds, dutyCycle));
    }

    public void startKeepAlive() {
        Executors.newSingleThreadExecutor().submit((Runnable) () -> {
            while (true) {
                executeCommand("060001010200");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
        });

    }

    private void executeCommand(String encodedCommandHex) {
        String command = String.format("gatttool -i %s -b %s --char-write-req --handle=%s --value=%s",
                Config.INTERFACE, Config.DEVICE_ID, Config.HANDLE, encodedCommandHex);
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
