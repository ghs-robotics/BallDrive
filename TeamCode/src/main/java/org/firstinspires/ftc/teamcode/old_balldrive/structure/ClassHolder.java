package org.firstinspires.ftc.teamcode.old_balldrive.structure;

import org.firstinspires.ftc.teamcode.old_balldrive.OpModeExtended;
import org.majora320.tealisp.evaluator.JavaInterface;

import java.util.Map;

public abstract class ClassHolder {
    public OpModeExtended context;

    public ClassHolder(OpModeExtended context) {
        this.context = context;
    }

    public abstract Map<String, Subsystem> getSubsystems();
    public abstract Map<String, JavaInterface> getInterfaces();
}
