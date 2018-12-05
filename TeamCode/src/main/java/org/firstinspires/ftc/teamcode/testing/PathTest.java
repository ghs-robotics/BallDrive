package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.core.path.PathData;
import org.firstinspires.ftc.teamcode.core.path.PathFactory;
import org.firstinspires.ftc.teamcode.core.path.PathState;

@TeleOp(name="Path Test", group="teleop")
public class PathTest extends OpMode {

    private DcMotorEx motor;
    private double startTime;
    PathData data;

    @Override
    public void init() {
        data = new PathFactory(0, 0, 3000, 0, 1000, 300, .1).data;
        motor = (DcMotorEx) hardwareMap.dcMotor.get("motorStrafe");
        for (PathState state : data.states) {
            telemetry.log().add(state.time + " " + state.pos + " " + state.vel + " " + state.acc);
        }
    }

    @Override
    public void start() {
        startTime = getTime();
    }


    @Override
    public void loop() {
        double currTime = getTime() - startTime;

        PathState nextState = data.getForTime(currTime);

        motor.setVelocity(nextState.vel, AngleUnit.DEGREES);

        telemetry.log().add(currTime + " " + data.states.size() + " " + nextState.vel);
    }

    private double getTime() {
        return System.currentTimeMillis() / 1000.0;
    }
}
