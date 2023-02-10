package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class Gyro {

    IMU gyro;
    IMU.Parameters hubOrientationParams;
    Orientation orientation;
    IMU.Parameters quarternion;
    YawPitchRollAngles robotOrientation;

    double Yaw;
    double Pitch;
    double Roll;



    public Gyro (HardwareMap hardwareMap) {
        gyro = hardwareMap.get(IMU.class, "imu");
        hubOrientationParams = new IMU.Parameters(
            new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
            )
        );
    }

    public void init(){
        gyro.initialize(hubOrientationParams);

        robotOrientation = gyro.getRobotYawPitchRollAngles();

        Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
        Pitch = robotOrientation.getPitch(AngleUnit.DEGREES);
        Roll = robotOrientation.getRoll(AngleUnit.DEGREES);
    }
//
//    public double orientationFirstAng(){
//        return orientation.firstAngle;
//    }
//
//    public double orientationSecAng(){
//        return orientation.secondAngle;
//    }
//
//    public double orientationThirdAng(){
//        return orientation.thirdAngle;
//    }

    public double getYaw(){
        return Yaw;
    }

    public double getPitch(){
        return Pitch;
    }

    public double getRoll(){
        return Roll;
    }

}
