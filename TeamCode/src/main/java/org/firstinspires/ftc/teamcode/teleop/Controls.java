package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.Robot;

public class Controls {
    Robot robot;

    public Controls(HardwareMap hardwareMap, Telemetry telemetry){
        robot = new Robot(hardwareMap, telemetry);
    }

    public void metaDrive (Gamepad gpad1){
        double y = gpad1.left_stick_y;
        double x = gpad1.left_stick_x;
        double angle = robot.gyro.getAngleRad();

        double newY = y * Math.cos(angle) + x * Math.sin(angle);
        double newX = y * Math.sin(angle) - x * Math.cos(angle);

        robot.drive.calculateDrivePowers(newY, newX, gpad1.right_stick_x);
    }
}
