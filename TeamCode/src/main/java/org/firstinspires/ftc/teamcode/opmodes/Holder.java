package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.balldrive.DriveSubsystem;
import org.firstinspires.ftc.teamcode.balldrive.LiftSubsystem;
import org.firstinspires.ftc.teamcode.core.iobuiltin.RevGyro;
import org.firstinspires.ftc.teamcode.core.structure.ClassHolder;
import org.firstinspires.ftc.teamcode.core.OpModeExtended;
import org.firstinspires.ftc.teamcode.core.structure.SensorManager;
import org.firstinspires.ftc.teamcode.core.structure.Subsystem;
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
        result.put("liftSubsystem", new LiftSubsystem(context));

        return result;
    }

    public Map<String, SensorManager> getSensors() {
        Map<String, SensorManager> result = new HashMap<>();

        result.put("gyro", new SensorManager(context, new RevGyro(context)));

        return result;
    }

    @Override
    public Map<String, JavaInterface> getInterfaces() {
        Map<String, JavaInterface> result = new HashMap<>();

        result.put("main", new MainInterface());

        return result;
    }
}
