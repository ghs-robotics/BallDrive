package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.stat.regression.RegressionResults;

import java.io.File;
import java.util.ArrayList;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.balldrive.DriveSubsystem;
import org.firstinspires.ftc.teamcode.balldrive.LiftSubsystem;
import org.firstinspires.ftc.teamcode.core.structure.ClassHolder;
import org.firstinspires.ftc.teamcode.core.OpModeExtended;
import org.firstinspires.ftc.teamcode.core.structure.Registry;
import org.firstinspires.ftc.teamcode.opmodes.Holder;


@Autonomous(name = "SensorStdDevTest", group = "tele")
public class SensorStdDevTest extends OpModeExtended {

    public OpModeExtended.InputControlManager getInputControlManager() {
        return new org.firstinspires.ftc.teamcode.core.SensorStdDevTest.AICM();
    }

    public ClassHolder getClassHolder() {
        return new Holder(this);
    }

    public class AICM extends OpModeExtended.AutoInputControlManager {
        DriveSubsystem drive;



        static final int NUM_VOLTAGES = 100;
        AnalogInput ultrasonic;
        double[] voltageList;
        double[][] x;
        ElapsedTime timer = new ElapsedTime();
        SimpleRegression regression;
        int i;

        @Override
        public void autoinit() {
            teaLispFile = new File("./storage/emulated/0/bluetooth/one-motion.tl");
            ultrasonic = hardwareMap.analogInput.get("ultra");
            timer.reset();
            drive = (DriveSubsystem) Registry.getSubsystemByName("driveSubsystem");
            drive.setting("mode", DriveSubsystem.Mode.MANUAL_LRS);
            SimpleRegression regression = new SimpleRegression();
            voltageList = new double[100];
            x = new double[100][0];
            telemetry.addData("poop:", 3);

        }

        @Override
        public void autoupdate() {
            drive.setting("mode", DriveSubsystem.Mode.MANUAL_LRS);
            drive.setting("manualL", 1.0);
            drive.setting("manualR", 1.0);
            double currentVoltage = ultrasonic.getVoltage();
            telemetry.addData("Voltage", currentVoltage);
            telemetry.addData("aaron", i);

            if (i < 100) {
                voltageList[i] = currentVoltage;
                x[i][0] = timer.milliseconds();
            } else {
                regression.addObservations(x, voltageList);
                RegressionResults lol = regression.regress();
                telemetry.addData("hi brenda:", Math.sqrt(lol.getErrorSumSquares() / lol.getN()));
                telemetry.addData("aaron", lol.getRSquared());
            }
            i++;

            telemetry.update();
        }
    }
}

