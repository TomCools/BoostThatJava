package be.tomcools.javaboost.commands;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CommandEncoderTest {

    private CommandEncoder sut = new CommandEncoder();

    @Test
    public void ab_360deg_15percentdc() {
        String angle = sut.encodeMotorAngle(Motor.AB, 360, 15);

        assertEqual(angle,"0e 00 81 39 11 0b 68 01 00 00 0f 64 7f 03");
    }

    @Test
    public void ab_90deg_15percentdc() {
        String angle = sut.encodeMotorAngle(Motor.AB, 90, 15);

        assertEqual(angle,"0e 00 81 39 11 0b 5a 00 00 00 0f 64 7f 03");
    }

    @Test
    public void ab_90deg_50percentdc() {
        String angle = sut.encodeMotorAngle(Motor.AB, 90, 50);

        assertEqual(angle,"0e 00 81 39 11 0b 5a 00 00 00 32 64 7f 03");
    }

    @Test
    public void ab_90deg_100percentdc() {
        String angle = sut.encodeMotorAngle(Motor.AB, 90, 100);

        assertEqual(angle,"0e 00 81 39 11 0b 5a 00 00 00 64 64 7f 03");
    }

    @Test
    public void ab_90deg_50percentdccounterclock() {
        String angle = sut.encodeMotorAngle(Motor.AB, 90, -50);

        assertEqual(angle,"0e 00 81 39 11 0b 5a 00 00 00 CD 64 7f 03");
    }

    @Test
    public void ab_90deg_15percentdccounter() {
        String angle = sut.encodeMotorAngle(Motor.AB, 90, 15);

        assertEqual(angle,"0e 00 81 39 11 0b 5a 00 00 00 0f 64 7f 03");
    }

    @Test
    public void multi_90deg_15percentandcounter() {
        String angle = sut.encodeMotorAngleMulti(90, 15, -15);

        assertEqual(angle,"0f 00 81 39 11 0c 5a 00 00 00 0f f0 64 7f 03");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenNegativeAngle_exceptionIsThrown() {
        sut.encodeMotorAngleMulti(-90, 15, -15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenAngleTooLargeForSystem_exceptionIsThrown() { ;
        sut.encodeMotorAngleMulti(Integer.MAX_VALUE+1, 15, -15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenDutyCycleBelowMinus100Limit_exceptionIsThrown() {
        sut.encodeMotorAngleMulti(-90, -101, -15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenDutyCycleAbove100Limit_exceptionIsThrown() {
        sut.encodeMotorAngleMulti(-90, 101, -15);
    }


    /*TIME*/
    @Test
    public void ab_1seconds_100percentdc() {
        String angle = sut.encodeMotorTime(Motor.AB, 1000, 100);

        assertEqual(angle,"0c 00 81 39 11 09 e8 03 64 64 7f 03");
    }

    @Test
    public void multi_1seconds_100percentdcandcounter() {
        String angle = sut.encodeMotorTimeMulti(1000, 100, -100);

        assertEqual(angle,"0d 00 81 39 11 0a e8 03 64 9b 64 7f 03");
    }


    private void assertEqual(String actual, String expected) {
        assertThat(actual,is(expected.replace(" ","").trim().toUpperCase()));
    }
}