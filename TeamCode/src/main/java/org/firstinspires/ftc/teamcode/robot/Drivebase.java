package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.List;

public class Drivebase {
    public DcMotor leftMotor;
    public DcMotor rightMotor;
    public DcMotor backMotor;

    public List<DcMotor> motors;

    public Drivebase(HardwareMap hardwareMap, Telemetry telemetry){
        leftMotor = hardwareMap.get(DcMotor.class, "left");
        rightMotor = hardwareMap.get(DcMotor.class, "right");
        backMotor = hardwareMap.get(DcMotor.class, "back");

        motors = Arrays.asList(leftMotor, rightMotor, backMotor);

        for (int i = 0; i < motors.size(); i++)
            motors.get(i).setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        telemetry.update();
    }

    //regular controls in theory
    public void sendDrivePowers(double y, double x, double rot){
        if (Math.abs(rot) > 0.01)
            setDrivePowers(y, y, x);
        else
            setDrivePowers(rot, -rot, x);
    }

    //potential improved version of the one above
    public void calculateDrivePowers(double y, double x, double rot){
        rot = -rot;
        double l = y + rot;
        double r = y - rot;
        double b = x;

        setDrivePowers(l, r, b);
    }

    public void setDrivePowers(double l, double r, double b){
        leftMotor.setPower(l);
        rightMotor.setPower(r);
        backMotor.setPower(b);
    }

}
