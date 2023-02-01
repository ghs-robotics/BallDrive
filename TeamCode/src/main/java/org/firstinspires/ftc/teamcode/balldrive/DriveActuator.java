package org.firstinspires.ftc.teamcode.balldrive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.balldrive.OpModeExtended;

public class DriveActuator {

    private DcMotorEx motorL, motorR, motorS;

    private OpModeExtended context;

    public DriveActuator(OpModeExtended context) {
        this.context = context;
    }

    public void init() {
        motorL = (DcMotorEx) context.hardwareMap.dcMotor.get("motorLF");
        motorR = (DcMotorEx) context.hardwareMap.dcMotor.get("motorRF");
        motorS = (DcMotorEx) context.hardwareMap.dcMotor.get("motorStrafe");

        resetEncoders();

        motorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorL.setDirection(DcMotorSimple.Direction.FORWARD);
        motorR.setDirection(DcMotorSimple.Direction.REVERSE);
        motorS.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void resetEncoders() {
        motorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void setPower(double l, double r, double s) {
        motorL.setPower(l);
        motorR.setPower(r);
        motorS.setPower(s);
    }

    public void setVelocity(double l, double r, double s) {
        motorL.setVelocity(l, AngleUnit.DEGREES);
        motorR.setVelocity(r, AngleUnit.DEGREES);
        motorS.setVelocity(s, AngleUnit.DEGREES);
    }

    public void printEncoders() {
        context.telemetry.addData("l encoder", motorL.getCurrentPosition());
        context.telemetry.addData("r encoder", motorR.getCurrentPosition());
        context.telemetry.addData("s encoder", motorS.getCurrentPosition());
    }
}
