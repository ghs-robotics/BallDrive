package org.firstinspires.ftc.teamcode.balldrive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.core.OpModeExtended;

public class LiftActuator {

    private DcMotorEx lift;
    private Servo pawl;

    private OpModeExtended context;

    private static final double OPEN = 0.7;
    private static final double CLOSED = 0.81;

    private double state;

    public LiftActuator(OpModeExtended context) {
        this.context = context;
    }

    public void init() {
        lift = (DcMotorEx) context.hardwareMap.dcMotor.get("lift");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setDirection(DcMotorSimple.Direction.FORWARD);

        pawl = context.hardwareMap.servo.get("pawl");
        pawl.setDirection(Servo.Direction.FORWARD);
        close(); //TODO: CHANGE THIS BACK!
    }

    public void open() {
        setPawl(OPEN);
        state = OPEN;
    }

    public void close() {
        setPawl(CLOSED);
        state = CLOSED;
    }

    public void toggle() {
        if (state == OPEN) {
            close();
        } else if (state == CLOSED) {
            open();
        }
    }

    public void setLift(double power) {
        lift.setPower(power);
    }

    public void setPawl(double pos) {
        pawl.setPosition(pos);
    }
}
