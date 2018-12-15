package org.firstinspires.ftc.teamcode.balldrive;

import org.firstinspires.ftc.teamcode.core.structure.Subsystem;
import android.util.Log;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.OpModeExtended;
import org.firstinspires.ftc.teamcode.core.structure.Setting;
import org.firstinspires.ftc.teamcode.core.structure.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.balldrive.IntakeActuator;


public class IntakeSubsystem extends Subsystem {
    private OpModeExtended context;
    private IntakeActuator actuator;

    @Setting
    public double intakePower;

    @Setting
    public double slidePower;

    public IntakeSubsystem(OpModeExtended context) {
        super(context);
        this.context = context;
        this.actuator = new IntakeActuator(context);
    }

    @Override
    public void init() {
        actuator.init();
        intakePower = 0;
        slidePower = 0;
    }

    @Override
    public void updateActuators() {
        actuator.setIntake(intakePower);
        actuator.setSlide(slidePower);
    }

    @Override
    public void updateData() {

    }

}
