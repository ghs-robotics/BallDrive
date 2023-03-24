package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.Arrays;
import java.util.List;

public class Drivebase {
    public Gyro gyro;

    public DcMotor leftMotor;
    public DcMotor rightMotor;
    public DcMotor backMotor;

    public List<DcMotor> motors;

    public Drivebase(HardwareMap hardwareMap, Telemetry telemetry){
        leftMotor = hardwareMap.get(DcMotor.class, "left");
        rightMotor = hardwareMap.get(DcMotor.class, "right");
        backMotor = hardwareMap.get(DcMotor.class, "back");

        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        motors = Arrays.asList(leftMotor, rightMotor, backMotor);

        for (int i = 0; i < motors.size(); i++) {

            motors.get(i).setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        gyro = new Gyro(hardwareMap);

        telemetry.update();
    }

    public void metaDrive (double y, double x, double rot){
        double angle = -gyro.getFirst(AngleUnit.RADIANS);

        double newY = y * Math.cos(angle) - x * Math.sin(angle);
        double newX = y * Math.sin(angle) + x * Math.cos(angle);

        calculateDrivePowers(newY, newX, rot);
    }

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
