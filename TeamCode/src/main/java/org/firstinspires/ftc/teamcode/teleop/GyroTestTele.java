package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.robot.Gyro;
import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp
public class GyroTestTele extends LinearOpMode {
    Gyro gyro;
    Robot robot;


    @Override
    public void runOpMode() throws InterruptedException {
        gyro = new Gyro(hardwareMap);
        robot = new Robot(hardwareMap, telemetry);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        gyro.reset();
        waitForStart();

        while (opModeIsActive()){

            robot.drive.metaDrive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

            if (gamepad1.right_bumper && gamepad1.left_bumper)
                gyro.reset();

            telemetry.addData("1", gyro.getFirst(AngleUnit.DEGREES));
            telemetry.addData("2", gyro.getSecond(AngleUnit.DEGREES));
            telemetry.addData("3", gyro.getThird(AngleUnit.DEGREES));
            telemetry.update();
        }
    }
}
