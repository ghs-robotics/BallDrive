package org.firstinspires.ftc.teamcode.testing;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.OpModeExtended;
import org.firstinspires.ftc.teamcode.core.Setting;
import org.firstinspires.ftc.teamcode.core.Subsystem;

import java.util.Map;

public class FakeLoggingSubsystem extends Subsystem {
    Telemetry telemetry;
    @Setting
    public Map<String, String> logMessages;

    public FakeLoggingSubsystem(OpModeExtended context) {
        super(context);
    }

    public void init() {
        this.telemetry = context.telemetry;
    }

    public void updateData() {

    }

    public void updateActuators() {
        for (Map.Entry<String, String> entry : logMessages.entrySet()) {
            telemetry.clearAll();
            telemetry.log().add("log: " + entry.getKey() + ": " + entry.getValue());
            telemetry.update();
        }
    }
}
