package be.tomcools.javaboost.commands;

import static be.tomcools.javaboost.commands.HexEncoder.toHex;
import static be.tomcools.javaboost.commands.HexEncoder.toLittleEndian;

/**
 * Beware, pure magic!
 * See:
 * https://github.com/JorgePe/BOOSTreveng/blob/master/Motors.md
 */
public class CommandEncoder {

    /**
     * Turn a motor for a specific time
     *
     * @param port        Motor port - possible string values: `A`, `B`, `AB`, `C`, `D`.
     * @param miliseconds Time to run in miliseconds
     * @param dutyCycle   [dutyCycle=100] motor power percentage from `-100` to `100`. If a negative value is given rotation is counterclockwise.
     */
    public String encodeMotorTime(Motor port, int miliseconds, int dutyCycle) {
        return String.format("0C0081%s1109%s%s647F03", port.getCode(), toLittleEndian(toHex(miliseconds, 2)), convertDutyCycle(dutyCycle)).toUpperCase();
    }

    /**
     * Turn A and B motor for a specific time at 2 different speeds
     *
     * @param miliseconds Time to run in miliseconds
     * @param dutyCycleA  [dutyCycle=100] motor power percentage from `-100` to `100`. If a negative value is given rotation is counterclockwise.
     * @param dutyCycleB  [dutyCycle=100] motor power percentage from `-100` to `100`. If a negative value is given rotation is counterclockwise.
     */
    public String encodeMotorTimeMulti(int miliseconds, int dutyCycleA, int dutyCycleB) {
        return String.format("0D0081%s110A%s%s%s647F03", Motor.AB.getCode(), toLittleEndian(toHex(miliseconds, 2)), convertDutyCycle(dutyCycleA), convertDutyCycle(dutyCycleB)).toUpperCase();
    }

    /**
     * Turn a motor by specific angle
     *
     * @param port      Motor port - possible string values: `A`, `B`, `AB`, `C`, `D`.
     * @param angle     angle - degrees to turn from `0` to `2147483647`
     * @param dutyCycle [dutyCycle=100] motor power percentage from `-100` to `100`. If a negative value is given rotation is counterclockwise.
     */
    public String encodeMotorAngle(Motor port, int angle, int dutyCycle) {
        validateInput(angle, dutyCycle);
        return String.format("0E0081%s110B%s%s647F03", port.getCode(), toLittleEndian(toHex(angle, 4)), convertDutyCycle(dutyCycle)).toUpperCase();
    }

    /**
     * Change the color of the led.
     *
     * @param ledColor The color which will be sended to the LED.
     * @return The formated command.
     */
    public String changeLed(String ledColor) {
        return String.format("080081321151000%s", ledColor).toUpperCase();
    }

    /**
     * Turn A and B motor by an angle at 2 different speeds
     *
     * @param angle      angle - degrees to turn from `0` to `2147483647`
     * @param dutyCycleA [dutyCycle=100] motor power percentage from `-100` to `100`. If a negative value is given rotation is counterclockwise.
     * @param dutyCycleB [dutyCycle=100] motor power percentage from `-100` to `100`. If a negative value is given rotation is counterclockwise.
     */
    public String encodeMotorAngleMulti(int angle, int dutyCycleA, int dutyCycleB) {
        validateInput(angle, dutyCycleA, dutyCycleB);
        return String.format("0F0081%s110C%s%s%s647F03", Motor.AB.getCode(), toLittleEndian(toHex(angle, 4)), convertDutyCycle(dutyCycleA), convertDutyCycle(dutyCycleB)).toUpperCase();
    }

    private void validateInput(int angle, int... dutyCycles) {
        if (angle < 0) {
            throw new IllegalArgumentException("Angle must be within range `0 <-> 2147483647`, but was " + angle);
        }
        for (int dutyCycle : dutyCycles) {
            if (dutyCycle < -100 || dutyCycle > 100) {
                throw new IllegalArgumentException("DutyCycle must be within range `-100 <-> 100`, but was " + dutyCycle);
            }
        }
    }

    /*
        Converts the dutycycle (speed of execution) into a hexadecimal number of 1 byte (2 hex characters)
        Since the valid range for duty cycles is -100 <-> +100, negative numbers are not used in hexadecimal.
        Instead, it uses the remaining numbers in the byte (254 -> 155 in decimal = duty cycle -1 -> -100)
     */
    private String convertDutyCycle(int dutyCycle) {
        int intCycle;
        if (dutyCycle >= 0) {
            intCycle = dutyCycle;
        } else {
            intCycle = 255 - Math.abs(dutyCycle);
        }
        return toHex(intCycle, 1);
    }
}
