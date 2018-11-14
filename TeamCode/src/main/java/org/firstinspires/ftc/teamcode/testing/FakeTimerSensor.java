package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.SensorInterface;

public class FakeTimerSensor implements SensorInterface {
    HardwareMap hardwareMap;
    String configName;

    double lastValue;
    private long startTime;

    public FakeTimerSensor(HardwareMap hardwareMap, String configName) {
        this.hardwareMap = hardwareMap;
        this.configName = configName;
    }

    public void init() {
        startTime = System.nanoTime();
    }

    public void update() {
        lastValue = (System.nanoTime() - startTime) / 1_000_000_000d;
    }

    public double getCMValue() {
        return lastValue;
    }

    public double getRawValue() {
        return lastValue;
    }

    public String getConfigName() {
        return configName;
    }
}
