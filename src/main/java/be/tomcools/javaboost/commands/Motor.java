package be.tomcools.javaboost.commands;

public enum Motor {

    A("37"),
    B("38"),
    AB("39"),
    C("01"),
    D("02"),
    LED("32"),
    TILT("3A");

    private String code;

    Motor(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
