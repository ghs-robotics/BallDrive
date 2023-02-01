package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Drivebase;

@TeleOp
public class Tele extends LinearOpMode {

    Drivebase robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Drivebase(hardwareMap, telemetry);

        //left stick y controls forward backward. Orientation on controller: |
        //left stick x controls strafing sideways. Orientation on controller: -
        //right stick x controls rotation in place. Orientation on controller: -
        while (opModeIsActive()){
            robot.calculateDrivePowers(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
    }
}
