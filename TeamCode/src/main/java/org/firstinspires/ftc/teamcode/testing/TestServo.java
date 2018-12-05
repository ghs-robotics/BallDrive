package org.firstinspires.ftc.teamcode.testing;

import android.util.Log;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.core.OpModeExtended;
import org.firstinspires.ftc.teamcode.core.structure.Setting;
import org.firstinspires.ftc.teamcode.core.structure.Subsystem;

public class TestServo extends Subsystem {
    @Setting
    public double position;

    private Servo servo;
    public TestServo(OpModeExtended context, String name) {
        super(context);
        servo = context.hardwareMap.servo.get(name);
    }

    @Override
    public void init() {

    }

    @Override
    public void updateData() {

    }

    @Override
    public void updateActuators() {
        Log.e("foo", "" + position);
        servo.setPosition(0);
    }
}
