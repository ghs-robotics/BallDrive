package org.firstinspires.ftc.teamcode.core.structure;

import org.firstinspires.ftc.teamcode.core.OpModeExtended;
import org.majora320.tealisp.evaluator.JavaInterface;

import java.util.Map;

public abstract class ClassHolder {
    public OpModeExtended context;

    public ClassHolder(OpModeExtended context) {
        this.context = context;
    }

    public abstract Map<String, Subsystem> getSubsystems();
    public abstract Map<String, SensorManager> getSensors();
    public abstract Map<String, JavaInterface> getInterfaces();
}
