package be.tomcools.javaboost.commands;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tomco on 25/12/2017.
 */
public class GatttoolCommandWrapperTest {
    @Test
    public void quicky() {
        new GatttoolCommandWrapper().motorAngle(Motor.A, 900, 100);
    }

    @Test
    public void quickyTime() {
        //new GatttoolCommandWrapper().motorTime(Motor.A, 1, 100);
        new GatttoolCommandWrapper().motorTime(Motor.AB, 1, -100);
    }
}