package be.tomcools.javaboost.commands;

/*
@deprecated
 */
public enum CommandCodes {
    BT_SCAN("sudo hcitool lescan"),
    BT_CONNECT("sudo hcitool lecc 00:16:53:A3:63:CA");

    private String command;

    CommandCodes(String command) {
        this.command = command;
    }
}
