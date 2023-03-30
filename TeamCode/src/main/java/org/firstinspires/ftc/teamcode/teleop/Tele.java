package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp
public class Tele extends LinearOpMode {
    Robot robot;
    Controls controls;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap, telemetry);
        controls = new Controls(robot);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.gyro.reset();
        waitForStart();

        while (opModeIsActive()){

            controls.metaDrive(gamepad1);
            controls.resetGyro(gamepad1);

            telemetry.addData("1", robot.gyro.getHeading(AngleUnit.DEGREES));
            telemetry.addData("2", robot.gyro.getSecond(AngleUnit.DEGREES));
            telemetry.addData("3", robot.gyro.getThird(AngleUnit.DEGREES));
            telemetry.update();
        }
    }
}
