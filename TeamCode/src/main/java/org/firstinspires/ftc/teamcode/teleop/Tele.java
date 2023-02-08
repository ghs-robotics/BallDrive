package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.inputs.DriveType;
import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp
public class Tele extends LinearOpMode {

    Robot robot;
    DriveType dT;

    int driveMode;

    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();

        while (opModeIsActive()){
            DriveMode();
            Telemetry();
        }
    }

    private void Init() {
        robot = new Robot(hardwareMap, telemetry);
        dT = new DriveType();

        driveMode = 0;

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }
    private void DriveMode(){
        //Change drive mode
        if (gamepad1.left_bumper && gamepad1.right_bumper) driveMode = (driveMode + 1) % 3;

        switch (driveMode){
            case 0://Standard Drive
                robot.drive.calculateDrivePowers(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
                break;

            case 1://Meta Drive
                robot.drive.metaDrive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
                break;

            case 2:// Tank Drive
                //rotation magnitude / speed and direction
                double rotMag = gamepad1.right_trigger - gamepad1.left_trigger;

                robot.drive.setDrivePowers(gamepad1.left_stick_y, gamepad1.right_stick_y, rotMag);
                break;
        }
    }
    private void Telemetry() {
        telemetry.addData("mode", dT.driveMode(driveMode));
        telemetry.update();
    }
}
