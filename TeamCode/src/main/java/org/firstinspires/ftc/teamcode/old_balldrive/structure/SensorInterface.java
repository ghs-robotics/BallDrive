package org.firstinspires.ftc.teamcode.old_balldrive.structure;

public interface SensorInterface {
    void init();
    void update();
    double getCMValue();
    double getRawValue();
    String getConfigName();
}
