package org.firstinspires.ftc.teamcode.balldrive.structure;

import org.firstinspires.ftc.teamcode.balldrive.DriveSubsystem;
import org.firstinspires.ftc.teamcode.balldrive.iobuiltin.RevGyro;
import org.firstinspires.ftc.teamcode.balldrive.structure.ClassHolder;
import org.firstinspires.ftc.teamcode.balldrive.OpModeExtended;
import org.firstinspires.ftc.teamcode.balldrive.structure.MainInterface;
import org.firstinspires.ftc.teamcode.balldrive.structure.Subsystem;
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
