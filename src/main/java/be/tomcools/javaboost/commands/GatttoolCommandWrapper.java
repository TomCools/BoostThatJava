package be.tomcools.javaboost.commands;

import be.tomcools.javaboost.Config;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executors;

/**
 * Wraps the GATTT command line tool into a Java Class.
 */
public class GatttoolCommandWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(GatttoolCommandWrapper.class);
    private static boolean isKeepingAlive = false;
    private static final int WAITINGTIME = 1000;
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

    public void motorTimeMulti(int miliseconds, int dutyCycleA, int dutyCycleB) {
        this.executeCommand(encoder.encodeMotorTimeMulti(miliseconds, dutyCycleA, dutyCycleB));
    }

    public void led(String colour) {
        this.executeCommand(encoder.changeLed(colour));
    }

    public boolean isIsKeepingAlive() {
        return isKeepingAlive;
    }

    /**
     * Lego boost hub will shutdown if it does not get a connection every so often.
     * This method starts a thread to ping the connection every second.
     */
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

    /**
     * Executes a bluetooth command using a command line tool installed on the host os.
     */
    private void executeCommand(String encodedCommandHex) {
        Config config = Config.getConfig();
        String command = String.format("gatttool -i %s -b %s --char-write-req --handle=%s --value=%s",
                config.getBluetoothInterface(), config.getDeviceID(), config.getHandle(), encodedCommandHex);
        try {
            Runtime.getRuntime().exec(command);
            Thread.sleep(WAITINGTIME);
        } catch (IOException e) {
            LOG.error("Failed to execute command: " + command, e);
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
