package org.firstinspires.ftc.teamcode.old_balldrive.structure;

import org.firstinspires.ftc.teamcode.old_balldrive.DriveSubsystem;
import org.firstinspires.ftc.teamcode.old_balldrive.OpModeExtended;
import org.majora320.tealisp.evaluator.JavaInterface;

import java.util.HashMap;
import java.util.Map;

public class Holder extends ClassHolder {
    public Holder(OpModeExtended context) {
        super(context);
    }

    public Map<String, Subsystem> getSubsystems() {
        Map<String, Subsystem> result = new HashMap<>();

        result.put("driveSubsystem", new DriveSubsystem(context));
        return result;
    }

    @Override
    public Map<String, JavaInterface> getInterfaces() {
        Map<String, JavaInterface> result = new HashMap<>();

        result.put("main", new MainInterface());

        return result;
    }
}
