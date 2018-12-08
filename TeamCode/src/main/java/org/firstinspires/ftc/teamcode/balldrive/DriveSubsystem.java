package org.firstinspires.ftc.teamcode.balldrive;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.OpModeExtended;
import org.firstinspires.ftc.teamcode.core.structure.Setting;
import org.firstinspires.ftc.teamcode.core.structure.Subsystem;
import org.firstinspires.ftc.teamcode.core.path.PathData;
import org.firstinspires.ftc.teamcode.core.path.PathFactory;
import org.firstinspires.ftc.teamcode.core.path.PathState;

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

    @Setting
    public PathData pathS;
    @Setting
    public PathData pathL;
    @Setting
    public PathData pathR;

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
            case PATH_INIT: //Runs once to calculate a path for the robot
                startTime = currTime();

                PathFactory factoryS = new PathFactory(sPathVars);
                pathS = factoryS.data;
                PathFactory factoryL = new PathFactory(lPathVars);
                pathL = factoryL.data;
                PathFactory factoryR = new PathFactory(rPathVars);
                pathR = factoryR.data;

                mode = Mode.PATH;
                break;
            case PATH: //Runs every loop cycle to execute the path
                PathState nextStateS = pathS.getForTime(currTime() - startTime);
                PathState nextStateR = pathR.getForTime(currTime() - startTime);
                PathState nextStateL = pathL.getForTime(currTime() - startTime);

                if (nextStateS.equals(PathState.END_POINT) && nextStateL.equals(PathState.END_POINT) && nextStateR.equals(PathState.END_POINT)) {
                    mode = Mode.PATH_STOP;
                } else {
                    context.telemetry.addData("l input", nextStateL.vel);
                    context.telemetry.addData("r input", nextStateR.vel);
                    context.telemetry.addData("s input", nextStateS.vel);

                    lrsFromXYR(nextStateL.vel, nextStateR.vel, nextStateS.vel);
                    /*finalS = nextStateS.vel;
                    finalR = nextStateR.vel;
                    finalL = nextStateL.vel;*/
                }
                break;
            case PATH_STOP: //Runs once to stop the robot
                finalS = 0;
                finalR = 0;
                finalL = 0;
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
