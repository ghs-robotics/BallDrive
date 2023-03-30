package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.robot.Robot;

public class Controls {
    Robot robot;

    public Controls(Robot robot){
        this.robot = robot;
    }

    public void metaDrive (Gamepad gpad1){
        double y = gpad1.left_stick_y;
        double x = gpad1.left_stick_x;
        double angle = robot.gyro.getHeading(AngleUnit.RADIANS);

        double newY = y * Math.cos(angle) + x * Math.sin(angle);
        double newX = y * Math.sin(angle) - x * Math.cos(angle);

        robot.drive.calculateDrivePowers(newY, newX, gpad1.right_stick_x);
    }

    public void resetGyro (Gamepad gpad1){
        if (gpad1.right_bumper && gpad1.left_bumper)
            robot.gyro.reset();
    }
}
