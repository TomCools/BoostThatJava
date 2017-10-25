package be.tomcools.javaboost.commands;

import be.tomcools.javaboost.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GatttoolCommandWrapper {
    private CommandEncoder encoder = new CommandEncoder();

    /**
     * Turn a motor by specific angle
     * @param {string|number} port possible string values: `A`, `B`, `AB`, `C`, `D`.
     * @param {number} angle - degrees to turn from `0` to `2147483647`
     * @param {number} [dutyCycle=100] motor power percentage from `-100` to `100`. If a negative value is given
     * rotation is counterclockwise.
     */
    public void motorAngle(Motor port, int angle, int dutyCycle) {
        this.executeCommand(encoder.encodeMotorAngle(port, angle, dutyCycle));
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
