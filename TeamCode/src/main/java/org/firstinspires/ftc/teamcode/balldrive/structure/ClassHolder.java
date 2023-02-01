package org.firstinspires.ftc.teamcode.balldrive.structure;

import android.hardware.SensorManager;

import org.firstinspires.ftc.teamcode.balldrive.OpModeExtended;
import org.firstinspires.ftc.teamcode.balldrive.structure.Subsystem;
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
