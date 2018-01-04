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

    public String encodeMotorTime(Motor port, int seconds, int dutyCycle) {
        if (port == Motor.AB) {
            return encodeMotorTimeMulti(Motor.AB, seconds, dutyCycle);
        }
        return String.format("0E0081%s110B%s%s647F03", port.getCode(), toLittleEndianHex(seconds * 1000), convertDutyCycle(dutyCycle));


    private String encodeMotorTimeMulti(Motor port, int seconds, int dutyCycle) {
        String dutyCicles = convertDutyCycle(dutyCycle);
        return String.format("0F0081%s110C%s%s%s647F03", port.getCode(), toLittleEndianHex(seconds * 1000), dutyCicles, dutyCicles);
    }
    */

    /**
     * Turn a motor by specific angle
     *
     * @param {string|number} port possible string values: `A`, `B`, `AB`, `C`, `D`.
     * @param {number}        angle - degrees to turn from `0` to `2147483647`
     * @param {number}        [dutyCycle=100] motor power percentage from `-100` to `100`. If a negative value is given
     *                        rotation is counterclockwise.
     */
    public String encodeMotorAngle(Motor port, int angle, int dutyCycle) {
        if (port == Motor.AB) {
            return encodeMotorAngleMulti(Motor.AB, angle, dutyCycle);
        }
        return String.format("0E0081%s110B%s%s647F03", port.getCode(), toLittleEndian(toHex(angle,8)), convertDutyCycle(dutyCycle)).toUpperCase();
    }

    private String encodeMotorAngleMulti(Motor port, int angle, int dutyCycle) {
        String dutyCicles = convertDutyCycle(dutyCycle);
        return String.format("0F0081%s110C%s%s%s647F03", port.getCode(), toLittleEndian(toHex(angle,8)), dutyCicles, dutyCicles).toUpperCase();
    }

    private String convertDutyCycle(int dutyCycle) {
        if (dutyCycle >= 0) {
            return toHex(dutyCycle,1);
        } else {
            int posCycle = Math.abs(dutyCycle);
            return "-" + toHexString(posCycle);
        }
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
