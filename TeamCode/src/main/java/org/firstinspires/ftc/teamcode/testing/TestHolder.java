package org.firstinspires.ftc.teamcode.testing;

import org.firstinspires.ftc.teamcode.core.ClassHolder;
import org.firstinspires.ftc.teamcode.core.OpModeExtended;
import org.firstinspires.ftc.teamcode.core.SensorInterface;
import org.firstinspires.ftc.teamcode.core.SensorManager;
import org.firstinspires.ftc.teamcode.core.Subsystem;

import org.firstinspires.ftc.teamcode.core.tealisp.TealispSensorInterface;
import org.firstinspires.ftc.teamcode.core.tealisp.TealispSubsystemInterface;
import org.majora320.tealisp.evaluator.JavaInterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestHolder extends ClassHolder {

    public TestHolder(OpModeExtended context) {
        super(context);
    }

    public Map<String, Subsystem> getSubsystems() {
        Map<String, Subsystem> result = new HashMap<>();

        result.put("fakeLoggingSubsystem", new FakeLoggingSubsystem(context));
        result.put("testServo", new TestServo(context, "servo"));

        return result;
    }

    public Map<String, SensorManager> getSensors() {
        Map<String, SensorManager> result = new HashMap<>();

        result.put("fakeTimeSensor",
                new SensorManager(
                context, new FakeTimerSensor(context.hardwareMap, "")));

        return result;
    }

    @Override
    public Map<String, JavaInterface> getInterfaces() {
        Map<String, JavaInterface> res = new HashMap<>();
        res.put("subsystems", new TealispSubsystemInterface());
        res.put("sensors", new TealispSensorInterface());
        return res;
    }
}
