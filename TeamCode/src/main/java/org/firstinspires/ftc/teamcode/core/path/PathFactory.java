package org.firstinspires.ftc.teamcode.core.path;

import android.graphics.Path;

import static java.lang.Math.abs;

public class PathFactory {
    public PathData data;

    double time;
    double pos;
    double vel;
    double acc;

    double i_pos, i_vel;
    double f_pos, f_vel;
    double max_vel, max_acc;

    public double timestep;

    public PathFactory(double[] pathVars) {
        this(pathVars[0], pathVars[1], pathVars[2], pathVars[3], pathVars[4], pathVars[5], pathVars[6]);
    }

    public PathFactory(double i_pos, double i_vel, double f_pos, double f_vel, double max_vel, double max_acc, double timestep) {
        this.i_pos = i_pos;
        this.i_vel = i_vel;
        this.f_pos = f_pos;
        this.f_vel = f_vel;
        this.max_vel = max_vel;
        this.max_acc = max_acc;
        this.timestep = timestep;
        data = new PathData(timestep);

        this.pos = i_pos;
        this.vel = i_vel;

        if (!(direction(i_pos, f_pos) == direction(0, vel) && canDo(f_vel, abs(f_pos - i_pos))))
            cancelVelocity(0);

        accelerateTowardsTarget();
        continueLinearly();
        cancelVelocity(f_vel);
        zeroPoint();
        stopPoint();
    }

    void cancelVelocity(double targ) {
        while (abs(vel - targ) > max_acc * timestep) {
            acc = max_acc * -direction(targ, vel);
            vel += acc * timestep;
            pos += vel * timestep;
            data.states.add(new PathState(time, pos, vel, acc));
            time += timestep;
        }
    }

    void accelerateTowardsTarget() {
        while (abs(vel) < max_vel && canDo(f_vel, abs(f_pos - pos))) {
            acc = max_acc * direction(pos, f_pos);
            vel += acc * timestep;
            pos += vel * timestep;
            data.states.add(new PathState(time, pos, vel, acc));
            time += timestep;
        }
    }

    void continueLinearly() {
        while (canDo(f_vel, abs(f_pos - pos))) {
            acc = 0;
            vel += acc * timestep;
            pos += vel * timestep;
            data.states.add(new PathState(time, pos, vel, acc));
            time += timestep;
        }
    }

    void zeroPoint() {
        acc = f_vel - vel;
        vel = 0;
        pos = vel + pos;
        data.states.add(new PathState(time, pos, vel, acc));
    }

    void stopPoint() {
        data.states.add(PathState.END_POINT);
    }

    double displacementUntilVelocity(double targvel) {
        //double time = 0;
        double pos = 0;
        double vel = this.vel;
        //double acc = max_acc * direction(vel, targvel);
        while (abs(targvel - vel) > max_acc * timestep) {
            acc = max_acc * direction(vel, targvel);
            vel += acc * timestep;
            pos += vel * timestep;
            //time += timestep;
        }
        return pos;
    }

    boolean canDo(double targvel, double distance) {
        return abs(displacementUntilVelocity(targvel)) < distance;
    }

    double direction(double a, double b) {
        return a <= b ? 1 : -1;
    }
}