package org.firstinspires.ftc.teamcode.testing;
import org.firstinspires.ftc.teamcode.core.structure.ClassHolder;
import org.firstinspires.ftc.teamcode.core.OpModeExtended;
import org.firstinspires.ftc.teamcode.core.structure.SensorManager;
import org.firstinspires.ftc.teamcode.core.structure.Subsystem;

import org.firstinspires.ftc.teamcode.core.tealisp.TealispSensorInterface;
import org.firstinspires.ftc.teamcode.core.tealisp.TealispSubsystemInterface;
import org.majora320.tealisp.evaluator.JavaInterface;

import java.util.HashMap;
import java.util.Map;

public class TestHolder extends ClassHolder {

    public TestHolder(OpModeExtended context) {
        super(context);
    }

    public Map<String, Subsystem> getSubsystems() {
        Map<String, Subsystem> result = new HashMap<>();

        result.put("fakeLoggingSubsystem",                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      new FakeLoggingSubsystem(context));
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
