package org.firstinspires.ftc.teamcode.core.vuforia;

import org.firstinspires.ftc.teamcode.core.structure.Registry;
import org.firstinspires.ftc.teamcode.core.structure.SensorManager;
import org.opencv.core.Mat;

import java.util.Vector;

public class VuforiaFacade {
    private SensorManager x, y, z, pitch, roll, heading;
    private VuforiaEncapsulator vuforia;

    public VuforiaFacade() {
        this.x = Registry.getSensorManagerByName("VuforiaX");
        this.y = Registry.getSensorManagerByName("VuforiaY");
        this.z = Registry.getSensorManagerByName("VuforiaZ");
        this.pitch = Registry.getSensorManagerByName("VuforiaPitch");
        this.roll = Registry.getSensorManagerByName("VuforiaRoll");
        this.heading = Registry.getSensorManagerByName("VuforiaHeading");
        this.vuforia = Registry.getVuforia();
    }

    public boolean isTargetVisible() {
        return vuforia.isTargetVisible();
    }

    public Mat getFrame() {
        return vuforia.getFrame();
    }

    public double getX() {
        return x.getCM();
    }

    public double getY() {
        return y.getCM();
    }

    public double getZ() {
        return z.getCM();
    }

    public Vector<Double> getTranslation() {
        Vector<Double> pos = new Vector<>();
        pos.addElement(getX());
        pos.addElement(getY());
        pos.addElement(getZ());
        return pos;
    }

    public double getRoll() {
        return roll.getCM();
    }

    public double getPitch() {
        return pitch.getCM();
    }

    public double getHeading() {
        return heading.getCM();
    }

    public Vector<Double> getRotation() {
        Vector<Double> rot = new Vector<>();
        rot.addElement(getRoll());
        rot.addElement(getPitch());
        rot.addElement(getHeading());
        return rot;
    }
}
