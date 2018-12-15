package org.firstinspires.ftc.teamcode.balldrive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.core.OpModeExtended;

public class IntakeActuator {



        private DcMotorEx intake;
        private DcMotorEx slide;

        private OpModeExtended context;


        private double state;

        public IntakeActuator(OpModeExtended context) {
            this.context = context;
        }

        public void init() {
            intake = (DcMotorEx) context.hardwareMap.dcMotor.get("intake");
            intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            intake.setDirection(DcMotorSimple.Direction.FORWARD);

            slide = (DcMotorEx) context.hardwareMap.dcMotor.get("slide");
            slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slide.setDirection(DcMotorSimple.Direction.FORWARD);

        }


        public void setIntake(double power) {
            intake.setPower(power);
        }

        public void setSlide(double pos) {
            slide.setPower(pos);
        }
    }
