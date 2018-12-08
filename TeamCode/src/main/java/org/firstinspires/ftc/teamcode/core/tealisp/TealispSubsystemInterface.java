package org.firstinspires.ftc.teamcode.core.tealisp;

import org.firstinspires.ftc.teamcode.core.structure.Registry;
import org.majora320.tealisp.evaluator.JavaInterface;
import org.majora320.tealisp.evaluator.LispException;
import org.majora320.tealisp.evaluator.LispObject;
import org.majora320.tealisp.evaluator.StackFrame;

public class TealispSubsystemInterface extends JavaInterface {
    @Override
    public boolean isSupportedFunction(String function) {
        return function.equals("subsystem-setting") || function.equals("subsystem-getsetting");
    }

    @Override
    public LispObject runFunction(String name, LispObject[] params, StackFrame frame) throws LispException {
        switch (name) {
            case "subsystem-setting":
                checkParams("subsystem-setting", params, new Class[] { LispObject.String.class, LispObject.String.class, LispObject.class }, false);

                String subsystemName = ((LispObject.String) params[0]).getValue();
                String subsystemKey = ((LispObject.String) params[1]).getValue();
                Object subsystemValue = params[2].getValue();

                Registry.getSubsystemByName(subsystemName).setting(subsystemKey, subsystemValue);
                return new LispObject.Void();
            case "subsystem-getsetting":
                checkParams("subsystem-getsetting", params, new Class[]{ LispObject.String.class, LispObject.String.class }, false);

                subsystemName = ((LispObject.String) params[0]).getValue();
                subsystemKey = ((LispObject.String) params[1]).getValue();

                try {
                    Object value = Registry.getSubsystemByName(subsystemName)
                            .getSetting(subsystemKey);
                    return LispObject.fromJavaObject(value);
                } catch (ClassNotFoundException e) {
                    return new LispObject.Boolean(false);
                }
        }

        return null;
    }
}
