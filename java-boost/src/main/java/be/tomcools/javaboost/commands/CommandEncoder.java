package be.tomcools.javaboost.commands;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import static java.lang.Integer.toHexString;

/**
 * Beware, pure magic!
 */
public class CommandEncoder {

    /**
     * Turn a motor for a specific time
     *
     * @param {string|number} port possible string values: `A`, `B`, `AB`, `C`, `D`.
     * @param {number}        time - seconds
     * @param {number}        [dutyCycle=100] motor power percentage from `-100` to `100`. If a negative value is given
     *                        rotation is counterclockwise.
     *
     */
    public String encodeMotorTime(Motor port, int miliseconds, int dutyCycle) {
        return String.format("0C0081%s1109%s%s647F03", port.getCode(), toLittleEndian(toHex(miliseconds, 4)), convertDutyCycle(dutyCycle)).toUpperCase();
    }

    public String encodeMotorTimeMulti(int miliseconds, int dutyCycleA, int dutyCycleB) {
        return String.format("0D0081%s110A%s%s%s647F03", Motor.AB.getCode(), toLittleEndian(toHex(miliseconds, 4)), convertDutyCycle(dutyCycleA), convertDutyCycle(dutyCycleB)).toUpperCase();
    }

    /**
     * Turn a motor by specific angle
     *
     * @param {string|number} port possible string values: `A`, `B`, `AB`, `C`, `D`.
     * @param {number}        angle - degrees to turn from `0` to `2147483647`
     * @param {number}        [dutyCycle=100] motor power percentage from `-100` to `100`. If a negative value is given
     *                        rotation is counterclockwise.
     */
    public String encodeMotorAngle(Motor port, int angle, int dutyCycle) {
        validateInput(angle, dutyCycle);
        return String.format("0E0081%s110B%s%s647F03", port.getCode(), toLittleEndian(toHex(angle,8)), convertDutyCycle(dutyCycle)).toUpperCase();
    }

    public String encodeMotorAngleMulti(int angle, int dutyCycleA, int dutyCycleB) {
        validateInput(angle, dutyCycleA, dutyCycleB);
        return String.format("0F0081%s110C%s%s%s647F03", Motor.AB.getCode(), toLittleEndian(toHex(angle,8)), convertDutyCycle(dutyCycleA), convertDutyCycle(dutyCycleB)).toUpperCase();
    }

    private void validateInput(int angle, int... dutyCycles) {
        if(angle < 0) {
            throw new IllegalArgumentException("Angle must be within range `0 <-> 2147483647`, but was " + angle);
        }
        for (int dutyCycle : dutyCycles) {
            if(dutyCycle < -100 || dutyCycle > 100) {
                throw new IllegalArgumentException("DutyCycle must be within range `-100 <-> 100`, but was " + dutyCycle);
            }
        }
    }

    private String convertDutyCycle(int dutyCycle) {
        int intCycle;
        if (dutyCycle >= 0) {
            intCycle = dutyCycle;
        } else {
            intCycle = 255 - Math.abs(dutyCycle);
        }
        return toHex(intCycle,2);
    }

    private String toLittleEndian(String hex) {
        if(hex.length() % 2 != 0) {
            System.out.println("Not even number.. eek...");
            return hex;
        } else {
            int parts = hex.length()/2;
            String[] p = new String[parts];
            char[] charArray = hex.toCharArray();
            for(int i = 0; i < hex.length()/2;i++) {
                p[i]= "" + charArray[i*2] + charArray[i*2+1];
            }
            StringBuilder sb = new StringBuilder();
            for(int i = p.length -1; i >= 0;i--) {
                sb.append(p[i]);
            }
            return sb.toString();
        }
    }

    private String toHex(int i, int spaces) {
        String hexString = toHexString(i);
        if(hexString.length() > spaces) {
            System.out.println("EEK!!!!");
        }
        while(hexString.length() < spaces) {
            hexString = "0" + hexString;
        }
        return hexString;
    }
}
