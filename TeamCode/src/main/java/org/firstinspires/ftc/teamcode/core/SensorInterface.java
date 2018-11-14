package org.firstinspires.ftc.teamcode.core;

public interface SensorInterface {
    void init();
    void update();
    double getCMValue();
    double getRawValue();
    String getConfigName();
}
