package org.firstinspires.ftc.teamcode.core.tealisp;

import android.util.Log;

import org.firstinspires.ftc.teamcode.core.Registry;
import org.majora320.tealisp.evaluator.JavaInterface;
import org.majora320.tealisp.evaluator.LispException;
import org.majora320.tealisp.evaluator.LispObject;
import org.majora320.tealisp.evaluator.StackFrame;

import java.util.Arrays;

public class TealispSensorInterface extends JavaInterface {
    @Override
    public boolean isSupportedFunction(String function) {
        return function.equals("sensor-getcm") || function.equals("sensor-getraw") || function.equals("sin");
    }

    @Override
    public LispObject runFunction(String name, LispObject[] params, StackFrame frame) throws LispException {
        switch (name) {
            case "sensor-getcm":
                checkParams("subsystem-getcm", params, new Class[] { LispObject.String.class }, false);

                String sensorName = ((LispObject.String) params[0]).getValue();
                return new LispObject.Double(Registry.getSensorManagerByName(sensorName).getCM());
            case "sensor-getraw":
                checkParams("subsystem-getsetting", params, new Class[]{ LispObject.String.class }, false);

                sensorName = ((LispObject.String) params[0]).getValue();
                return new LispObject.Double(Registry.getSensorManagerByName(sensorName).sensorInterface.getRawValue());
        }

        return null;
    }
}
