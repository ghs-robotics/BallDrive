package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.stat.regression.RegressionResults;

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
        LiftSubsystem lift;

        static final int NUM_VOLTAGES = 100;
        AnalogInput ultrasonic;
        double[] voltageList;
        double sum;
        double mean;
        double stdDev;
        double[][] x;
        ElapsedTime timer = new ElapsedTime();
        SimpleRegression regression;
        int i;

        @Override
        public void autoinit() {
            ultrasonic = hardwareMap.analogInput.get("ultra");
            timer.reset();


            SimpleRegression regression = new SimpleRegression();
            drive.setting("autoL", 1.0);
            drive.setting("autoR", 1.0);


        }

        @Override
        public void autoupdate() {
            double currentVoltage = ultrasonic.getVoltage();


            if (voltageList.length < NUM_VOLTAGES) {
                voltageList[i] = currentVoltage;
                x[i][0] = timer.milliseconds();
                i++;
            } else {
                regression.addObservations(x, voltageList);
                RegressionResults lol = regression.regress();
                telemetry.addData("hi brenda:", Math.sqrt(lol.getErrorSumSquares() / lol.getN()));
                telemetry.addData("aaron:", lol.getAdjustedRSquared());
            }


            telemetry.addData("Voltage", currentVoltage);

            telemetry.update();
        }
    }
}

