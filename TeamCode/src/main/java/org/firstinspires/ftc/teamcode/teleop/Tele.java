//package org.firstinspires.ftc.teamcode.teleop;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.inputs.DriveType;
//import org.firstinspires.ftc.teamcode.robot.Drivebase;
//import org.firstinspires.ftc.teamcode.robot.Gyro;
//import org.firstinspires.ftc.teamcode.robot.Robot;
//
//@TeleOp
//public class Tele extends LinearOpMode {
//
//    Robot robot;
//    DriveType dT;
//    Gyro gyro;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        robot = new Robot(hardwareMap, telemetry);
//        dT = new DriveType();
//        gyro = new Gyro(hardwareMap);
//
//        int counter = 0;
//
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//
//        gyro.init();
//        waitForStart();
//        while (opModeIsActive()){
//            double triggers = gamepad1.right_trigger -gamepad1.left_trigger;
//            int driveMode = counter % 3;
//
//            if (gamepad1.left_bumper && gamepad1.right_bumper)
//                counter++;
//
//            if (driveMode == 0)
//                robot.drive.calculateDrivePowers(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
//            else if (driveMode == 1)
//                robot.drive.metaDrive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
//            else
//                robot.drive.setDrivePowers(gamepad1.left_stick_y, gamepad1.right_stick_y, triggers);
//
//
//            telemetry.addData("mode", dT.driveMode(driveMode));
//            telemetry.addLine();
////
////            telemetry.addData("1", gyro.orientationFirstAng());
////            telemetry.addData("1", gyro.orientationSecAng());
////            telemetry.addData("1", gyro.orientationThirdAng());
//
//            telemetry.addData("yaw", gyro.getYaw());
//            telemetry.addData("pitch", gyro.getPitch());
//            telemetry.addData("roll", gyro.getRoll());
//            telemetry.update();
//        }
//    }
//}
