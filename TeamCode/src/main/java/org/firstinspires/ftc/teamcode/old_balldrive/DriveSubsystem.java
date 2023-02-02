package org.firstinspires.ftc.teamcode.old_balldrive;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.old_balldrive.structure.Setting;
import org.firstinspires.ftc.teamcode.old_balldrive.structure.Subsystem;

public class DriveSubsystem extends Subsystem {
    private OpModeExtended context;

    private DriveActuator actuator;

    @Setting
    public double manualL;
    @Setting
    public double manualR;
    @Setting
    public double manualS;
    @Setting
    public double manualX;
    @Setting
    public double manualY;

    @Setting
    public double autoL;
    @Setting
    public double autoR;
    @Setting
    public double autoS;
    @Setting
    public double autoT;

    /** Paths:
     * (0) Initial position (1) Initial velocity (2) Final position (3) Final velocity
     * (4) Maximum velocity (5) Maximum acceleration (6) Time step (.1 recommended)
     */
    @Setting
    public double[] sPathVars;
    @Setting
    public double[] rPathVars;
    @Setting
    public double[] lPathVars;

    private double finalL, finalR, finalS;

    public enum Mode {
        MANUAL_XYR, MANUAL_LRS,
        AUTO_LRS_INIT, AUTO_LRS, AUTO_LRS_STOP,
        PATH_INIT, PATH, PATH_STOP,
        AUTO_IDLE}

    @Setting
    public Mode mode;

    @Setting
    public ElapsedTime timer;

    private double startTime;

    @Setting
    public boolean encoderPrint;

    public DriveSubsystem(OpModeExtended context) {
        super(context);
        this.context = context;
        this.actuator = new DriveActuator(context);
        this.encoderPrint = false;
    }

    public void init() {
        manualL = 0;
        manualR = 0;
        manualS = 0;
        manualX = 0;
        manualY = 0;
        finalL = 0;
        finalR = 0;
        finalS = 0;

        timer = new ElapsedTime();

        actuator.init();
    }

    public void updateData() {
        switch (mode) {
            case MANUAL_LRS:
                finalL = manualL;
                finalR = manualR;
                finalS = manualS;
                break;
            case MANUAL_XYR:
                lrsFromXYR(manualX, manualY, manualR);
                break;
            case AUTO_LRS_INIT:
                timer.reset();
                mode = Mode.AUTO_LRS;
                break;
            case AUTO_LRS:
                if (timer.seconds() <= autoT) {
                    finalL = autoL;
                    finalR = autoR;
                    finalS = autoS;
                } else {
                    mode = Mode.AUTO_LRS_STOP;
                }
                break;
            case AUTO_LRS_STOP:
                finalL = 0;
                finalR = 0;
                finalS = 0;
                mode = Mode.AUTO_IDLE;
                break;

        }
    }

    private void lrsFromXYR(double x, double y, double r) {
        finalL = y + r;
        finalR = y - r;
        finalS = x;
    }

    public void updateActuators() {
        context.telemetry.addData("l", finalL);
        context.telemetry.addData("r", finalR);
        context.telemetry.addData("s", finalS);
        context.telemetry.addData("mode", mode.name());

        if (mode.equals(Mode.PATH)) {
            actuator.setVelocity(finalL, finalR, finalS);
        } else {
            actuator.setPower(finalL, finalR, finalS);
        }

        if (encoderPrint) {
            actuator.printEncoders();
        }
    }

    private double currTime() {
        return System.currentTimeMillis() / 1000.0; //convert to seconds
    }
}
