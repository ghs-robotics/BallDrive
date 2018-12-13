package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.stat.regression.RegressionResults;

import java.util.ArrayList;

@TeleOp(name="SensorStdDevTest", group="Iterative Opmode")
// This opmode gets the standard deviation of a sensor by
// constantly replacing voltages in a list consisting of 100 values
public class SensorStdDevTest extends OpMode {
    static final int NUM_VOLTAGES = 100;
    AnalogInput ultrasonic;
    double[] voltageList;
    double sum;
    double mean;
    double stdDev;
    double[][] x;
    SimpleRegression regression;
    int i;

    @Override
    public void init() {
        ultrasonic = hardwareMap.analogInput.get("ultra");


        SimpleRegression regression = new SimpleRegression();


    }

    @Override
    public void loop() {
        double currentVoltage = ultrasonic.getVoltage();


        if (voltageList.length < NUM_VOLTAGES) {
            voltageList[i] = currentVoltage;
            x[i][0] = i;
            i++;
        } else {
            regression.addObservations(x, voltageList);
            RegressionResults lol = regression.regress();
            telemetry.addData("hi brenda:", Math.sqrt(lol.getErrorSumSquares()/lol.getN()));
        }




            telemetry.addData("Voltage", currentVoltage);

            telemetry.update();
        }
    }


