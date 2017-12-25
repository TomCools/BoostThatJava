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
     * @param {string|number} port possible string values: `A`, `B`, `AB`, `C`, `D`.
     * @param {number} time - seconds
     * @param {number} [dutyCycle=100] motor power percentage from `-100` to `100`. If a negative value is given
     * rotation is counterclockwise.
     */
    public String encodeMotorTime(Motor port, int seconds, int dutyCycle) {
        if(port == Motor.AB) {
            return encodeMotorTimeMulti(Motor.AB, seconds, dutyCycle);
        }
        return String.format("0E0081%s110B%s%s647F03", port.getCode(),toLittleEndianHex(seconds*1000, 2), toHexString(dutyCycle));
    }

    private String encodeMotorTimeMulti(Motor port, int seconds, int dutyCycle) {
        String dutyCicles = toHexString(dutyCycle);
        return String.format("0F0081%s110C%s%s%s647F03", port.getCode(),toLittleEndianHex(seconds*1000, 2), dutyCicles, dutyCicles);
    }

    /**
     * Turn a motor by specific angle
     * @param {string|number} port possible string values: `A`, `B`, `AB`, `C`, `D`.
     * @param {number} angle - degrees to turn from `0` to `2147483647`
     * @param {number} [dutyCycle=100] motor power percentage from `-100` to `100`. If a negative value is given
     * rotation is counterclockwise.
     */
    public String encodeMotorAngle(Motor port, int angle, int dutyCycle) {
        if(port == Motor.AB) {
            return encodeMotorAngleMulti(Motor.AB, angle, dutyCycle);
        }
        return String.format("0E0081%s110B%s%s647F03", port.getCode(),toLittleEndianHex(angle,4), toHexString(dutyCycle));
    }

    private String encodeMotorAngleMulti(Motor port, int angle, int dutyCycle) {
        String dutyCicles = toHexString(dutyCycle);
        return String.format("0F0081%s110C%s%s%s647F03", port.getCode(),toLittleEndianHex(angle,4), dutyCicles, dutyCicles);
    }

    private String toLittleEndianHex(int angle, int amountOfBytes) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(amountOfBytes).order(ByteOrder.LITTLE_ENDIAN).putInt(angle);
        StringBuilder sb = new StringBuilder();
        for (byte b : byteBuffer.array()) {
            sb.append(this.toHexPrefix((int) (b & 0xff)));
        }
        return sb.toString();
    }

    private String toHexPrefix(int i) {
        String hexString = toHexString(i);
        return ("00" + hexString).substring(hexString.length());
    }
}
