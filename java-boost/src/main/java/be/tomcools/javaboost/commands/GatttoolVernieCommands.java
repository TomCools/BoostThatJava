package be.tomcools.javaboost.commands;

public class GatttoolVernieCommands implements VernieCommands {
    GatttoolCommandWrapper commandWrapper = new GatttoolCommandWrapper();

    @Override
    public void moveForward(int distance, int power) {
        commandWrapper.motorAngle(Motor.AB, distance, power);
    }
}
