package org.firstinspires.ftc.teamcode.core.vuforia;

import org.firstinspires.ftc.teamcode.core.structure.SensorInterface;

public class VuforiaSensor implements SensorInterface {
    private VuforiaEncapsulator encapsulator;
    private int whichSensor;

    protected VuforiaSensor(VuforiaEncapsulator encapsulator, int whichSensor) {
        this.encapsulator = encapsulator;
        this.whichSensor = whichSensor;
    }

    @Override
    public void init() {
        encapsulator.init();
    }

    @Override
    public void update() {
        encapsulator.update();
    }

    @Override
    public double getCMValue() {
        switch (whichSensor) {
            case 0:
                return encapsulator.getX();
            case 1:
                return encapsulator.getY();
            case 2:
                return encapsulator.getZ();
            case 3:
                return encapsulator.getRoll();
            case 4:
                return encapsulator.getPitch();
            case 5:
                return encapsulator.getHeading();
            default:
                return -0.0;
        }
    }

    @Override
    public double getRawValue() {
        return getCMValue();
    }

    @Override
    public String getConfigName() {
        StringBuilder res = new StringBuilder("Vuforia");

        switch (whichSensor) {
            case 0:
                res.append("X");
                break;
            case 1:
                res.append("Y");
                break;
            case 2:
                res.append("Z");
                break;
            case 3:
                res.append("Roll");
                break;
            case 4:
                res.append("Pitch");
                break;
            case 5:
                res.append("Heading");
                break;
        }

        return res.toString();
    }
}