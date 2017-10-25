package be.tomcools.javaboost.commands;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import static java.lang.Integer.toHexString;

/**
 * Created by tomco on 23/10/2017.
 */
public class CommandEncoder {

    /**
     * Turn a motor by specific angle
     * @param {string|number} port possible string values: `A`, `B`, `AB`, `C`, `D`.
     * @param {number} angle - degrees to turn from `0` to `2147483647`
     * @param {number} [dutyCycle=100] motor power percentage from `-100` to `100`. If a negative value is given
     * rotation is counterclockwise.
     */
    public String encodeMotorAngle(Motor port, int angle, int dutyCycle) {
        return String.format("0E0081%s110B%s%s647F03", port.getCode(),toLittleEndianHex(angle), toHexString(dutyCycle));
    }

    private String toLittleEndianHex(int angle) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(angle);
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
