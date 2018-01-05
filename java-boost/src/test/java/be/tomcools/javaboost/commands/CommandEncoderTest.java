package be.tomcools.javaboost.commands;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by tomco on 23/10/2017.
 */
public class CommandEncoderTest {

    @Test
    public void ab_90deg_15percentdc() {
        CommandEncoder encoder = new CommandEncoder();

        String hexString = Integer.toHexString(90);
        System.out.println(hexString);
        hexString = Integer.toHexString(360);
        System.out.println(hexString);

        String angle = encoder.encodeMotorAngle(Motor.AB, 90, 15);

        assertThat(angle,is("0e 00 81 39 11 0b 5a 00 00 00 0e 64 7f 03".replace(" ","").trim().toUpperCase()));
    }

    @Test
    public void turn() {
        CommandEncoder encoder = new CommandEncoder();

        String angle = encoder.encodeMotorAngle(Motor.A, 900, 100);

        System.out.println(angle);
    }

    @Test
    public void turnHeadAndFire() {
        CommandEncoder encoder = new CommandEncoder();

        String angle = encoder.encodeMotorAngle(Motor.C, 90, 50);
        System.out.println(angle);
        angle = encoder.encodeMotorAngle(Motor.C, 300, 10);
        System.out.println(angle);
        angle = encoder.encodeMotorAngle(Motor.AB, 300, 50);
        System.out.println(angle);


    }

    @Test
    public void negativeNumber() {
        CommandEncoder encoder = new CommandEncoder();

        String angle = encoder.encodeMotorAngle(Motor.AB, 300, 9995);
        System.out.println(angle);
    }

}