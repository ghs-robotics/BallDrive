package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.Gyro;

public class GyroTestTele extends LinearOpMode {
    Gyro gyro;


    @Override
    public void runOpMode() throws InterruptedException {
        gyro = new Gyro(hardwareMap);
        gyro.init();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()){
//            telemetry.addData("1", gyro.orientationFirstAng());
//            telemetry.addData("1", gyro.orientationSecAng());
//            telemetry.addData("1", gyro.orientationThirdAng());

            telemetry.addData("yaw", gyro.getYaw());
            telemetry.addData("pitch", gyro.getPitch());
            telemetry.addData("roll", gyro.getRoll());
            telemetry.update();
        }
    }
}
