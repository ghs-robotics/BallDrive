package org.firstinspires.ftc.teamcode.balldrive.structure;

public interface SensorInterface {
    void init();
    void update();
    double getCMValue();
    double getRawValue();
    String getConfigName();
}
