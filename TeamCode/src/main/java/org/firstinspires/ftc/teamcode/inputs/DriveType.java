package org.firstinspires.ftc.teamcode.inputs;

public class DriveType {
    public String driveMode(int mode){
        String normal = "normal drive (like mecanum";
        String meta = "meta drive";
        String tank = "janky tank drive controls";

        if (mode == 0)
            return normal;
        else if (mode == 1)
            return meta;
        else
            return tank;
    }

    public DriveType.Types switchDrive(int counter){
        int mode = counter % 3;

        if (mode == 0)
            return Types.NORMAL;
        else if (mode == 1)
            return Types.META;
        else
            return Types.JANKTANK;
    }

    public enum Types{
        JANKTANK,
        NORMAL,
        META
    }
}

