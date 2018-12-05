package org.firstinspires.ftc.teamcode.core.structure;

public interface SensorInterface {
    void init();
    void update();
    double getCMValue();
    double getRawValue();
    String getConfigName();
}
