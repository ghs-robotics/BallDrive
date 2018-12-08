package org.firstinspires.ftc.teamcode.opmodes;

import android.util.Log;

import org.firstinspires.ftc.teamcode.balldrive.DriveSubsystem;
import org.firstinspires.ftc.teamcode.balldrive.LiftSubsystem;
import org.majora320.tealisp.evaluator.JavaInterface;
import org.majora320.tealisp.evaluator.LispException;
import org.majora320.tealisp.evaluator.LispObject;
import org.majora320.tealisp.evaluator.LispObject.Number;
import org.majora320.tealisp.evaluator.StackFrame;

import org.firstinspires.ftc.teamcode.balldrive.LiftSubsystem.ReleaseStage;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainInterface extends JavaInterface {
    @Override
    public boolean isSupportedFunction(String function) {
        //Log.i("team-code", "Function: " + function);
        return "drive".equals(function) || "log".equals(function) || "path".equals(function) || "drop".equals(function);
    }

    private DriveSubsystem drive;
    private LiftSubsystem lift;

    public void setDrive(DriveSubsystem drive) {
        this.drive = drive;
    }

    public void setLift(LiftSubsystem lift) {
        this.lift = lift;
    }

    /**
     * Calls Java functions in auto
     * @param name The name of the function to call
     * @param params The parameters for that function
     * @param frame The function stack
     * @return If the function has been completed
     * @throws LispException If the parameters given are not of the correct type
     */
    @Override
    public LispObject runFunction(String name, LispObject[] params, StackFrame frame) throws LispException {
        //Log.i("team-code", "name: " + name);
        switch(name) {
            case "drive":
                return driveFun(params);
            case "log":
                return logFun(params);
            case "path":
                return pathFun(params);
            case "drop":
                return dropFun(params);
            default:
                return null;
        }
    }

    private LispObject dropFun(LispObject[] params) throws LispException {
        //checkParams("drop", params, new Class[] {}, false);
        ReleaseStage currMode = (ReleaseStage) lift.getSetting("releaseMode");
        if (currMode.equals(ReleaseStage.CLOSED)) {
            lift.setting("releaseMode", ReleaseStage.SLIDE_OPEN);
            lift.setting("power", -1);
            lift.setting("powerTime", 1);
        } else if (currMode.equals(ReleaseStage.OPEN)) {
            return new LispObject.Boolean(true);
        }
        return new LispObject.Boolean(false);
    }

    /**
     * Follows a path specified by the parameters
     * @param params Controls for each path, in sets of seven
     *               One set: only strafe motor || two sets: left and right || three sets: all
     *
     *               (0) Initial position (1) Initial velocity (2) Final position (3) Final velocity
     *               (4) Maximum velocity (5) Maximum acceleration (6) Time step (.1 recommended)
     * @return Whether the path has been completed
     * @throws LispException If the parameters aren't all doubles
     */
    private LispObject pathFun(LispObject[] params) throws LispException {
        checkParams("path", params,
                new Class[]{ Number.class, Number.class, Number.class, Number.class, Number.class, Number.class, Number.class, },
                true);
        double[] processedS;
        double[] processedL;
        double[] processedR;

        Log.i("team-code", "length: " + params.length);

        if (params.length == 7) { //Assume that 7 path parameters are for the strafe motor
            processedS = getAsDoubles(params);
            processedL = zero();
            processedR = zero();
        } else if (params.length == 14) { //Assume that 14 path parameters are for left and right
            processedS = zero();
            processedL = getAsDoubles(Arrays.copyOfRange(params, 0, 7));
            processedR = getAsDoubles(Arrays.copyOfRange(params, 7, 14));
        } else { //Assume any other set of path parameters is to control all motors
            processedS = getAsDoubles(Arrays.copyOfRange(params, 0, 7));
            processedL = getAsDoubles(Arrays.copyOfRange(params, 7, 14));
            processedR = getAsDoubles(Arrays.copyOfRange(params, 14, 21));
        }

        switch ((DriveSubsystem.Mode) drive.getSetting("mode")) {
            case AUTO_IDLE: //Set paths for each motor
                drive.setting("mode", DriveSubsystem.Mode.PATH_INIT);
                drive.setting("sPathVars",  processedS);
                drive.setting("lPathVars", processedL);
                drive.setting("rPathVars", processedR);
                Log.i("team-code", processedS + " finalPos");
                return new LispObject.Boolean(false);
            case PATH_STOP:
                return new LispObject.Boolean(true);
            default:
                return new LispObject.Boolean(false);
        }
    }

    /**
     * Used to set a nonexistent path for a motor
     * @return Six zeros as a double array
     */
    private double[] zero() {
        return new double[]{0, 0, 0, 0, 0, 0, 0.1};
    }

    private LispObject logFun(LispObject[] params) throws LispException {
        checkParams("log", params, new Class[]{ LispObject.class }, false);
        Log.i("team-code", params[0].toString());
        return new LispObject.Void();
    }

    private LispObject driveFun(LispObject[] params) throws LispException {
        checkParams("drive", params,
                new Class[]{ Number.class, Number.class, Number.class, Number.class}, false);

        double[] processed = getAsDoubles(params);

        switch ((DriveSubsystem.Mode) drive.getSetting("mode")) {
            case AUTO_IDLE:
                drive.setting("mode", DriveSubsystem.Mode.AUTO_LRS_INIT);
                drive.setting("autoL", processed[0]);
                drive.setting("autoR", processed[1]);
                drive.setting("autoS", processed[2]);
                drive.setting("autoT", processed[3]);
                return new LispObject.Boolean(false);
            case AUTO_LRS_STOP:
                return new LispObject.Boolean(true);
            default:
                return new LispObject.Boolean(false);

        }
    }

    /**
     * Loops through an array of LispObjects of type Number and turns them into doubles
     * @param params An unprocessed array of LispObject.Number's
     * @return The processed array of doubles
     */
    private double[] getAsDoubles(LispObject[] params) {
        double[] processed = new double[params.length];
        for (int i = 0; i < params.length; i++) {
            LispObject param = params[i];
            if (param instanceof LispObject.Integer) {
                processed[i] = ((LispObject.Integer) param).getValue();
            } else if (param instanceof LispObject.Double) {
                processed[i] = ((LispObject.Double) param).getValue();
            }
        }
        return processed;
    }
}
