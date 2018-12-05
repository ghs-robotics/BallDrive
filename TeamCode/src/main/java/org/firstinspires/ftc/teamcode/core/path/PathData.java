package org.firstinspires.ftc.teamcode.core.path;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class PathData {
    public ArrayList<PathState> states;
    private double timestep;
    public Telemetry telemetry;

    PathData(double timestep) {
        this(new ArrayList<>(), timestep);
    }

    private PathData(ArrayList<PathState> states, double timestep) {
        this.states = states;
        this.timestep = timestep;
    }

    public PathState getForTime(double time) {
        int index = (int) (time / timestep);
        if (states.size() == 0) return new PathState(0, 0, 0, 0);
        return index >= states.size() ? states.get(states.size() - 1) : states.get(index);
    }
}