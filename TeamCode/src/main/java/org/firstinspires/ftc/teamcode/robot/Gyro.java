package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;

public class Gyro {

    private IMU gyro;
    private IMU.Parameters hubOrientationParams;
    private Orientation orientation;
    private IMU.Parameters quarternion;



    public Gyro () {
        hubOrientationParams = new IMU.Parameters(
            new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
            )
        );

        orientation = new Orientation(
                AxesReference.INTRINSIC,
                AxesOrder.ZYX,
                AngleUnit.DEGREES,
                90,
                0,
                -45,
                0  // acquisitionTime, not used
        );

        quarternion = new IMU.Parameters(
            new RevHubOrientationOnRobot(
                new Quaternion(
                        1.0f, // w
                        0.0f, // x
                        0.0f, // y
                        0.0f, // z
                        0     // acquisitionTime
                )
            )
        );
    }

    public void init(){
        gyro.initialize(hubOrientationParams);
    }

    public double getAngleRad(){
        return 0; //orientation.;
    }

}
