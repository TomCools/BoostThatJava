package be.tomcools.javaboost.commands;

import be.tomcools.javaboost.Config;
import be.tomcools.javaboost.MainVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executors;

public class GatttoolCommandWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(GatttoolCommandWrapper.class);
    private static boolean isKeepingAlive = false;
    private CommandEncoder encoder = new CommandEncoder();

    public void motorAngle(Motor port, int angle, int dutyCycle) {
        this.executeCommand(encoder.encodeMotorAngle(port, angle, dutyCycle));
    }

    public void motorAngleMulti(int angle, int dutyCycleA, int dutyCycleB) {
        this.executeCommand(encoder.encodeMotorAngleMulti(angle, dutyCycleA, dutyCycleB));
    }

    public void motorTime(Motor port, int seconds, int dutyCycle) {
        this.executeCommand(encoder.encodeMotorTime(port, seconds, dutyCycle));
    }

    public boolean isIsKeepingAlive() {
        return isKeepingAlive;
    }

    public void startKeepAlive() {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                isKeepingAlive = true;
                while (true) {
                    executeCommand("060001010200");
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                isKeepingAlive = false;
            }
        });

    }

    private void executeCommand(String encodedCommandHex) {
        Config config = Config.getConfig();
        String command = String.format("gatttool -i %s -b %s --char-write-req --handle=%s --value=%s",
                config.getBluetoothInterface(), config.getDeviceID(), config.getHandle(), encodedCommandHex);
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            LOG.error("Failed to execute command: " + command, e);
            throw new RuntimeException(e);
        }
    }
}
