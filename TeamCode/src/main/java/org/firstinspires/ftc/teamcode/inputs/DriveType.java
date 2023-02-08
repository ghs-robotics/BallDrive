package org.firstinspires.ftc.teamcode.inputs;

public class DriveType {
    public String getDriveModeName(int mode){
        String standard = "Standard Drive";
        String meta = "Meta Drive";
        String tank = "Tank Drive";

        if (mode == 0)
            return standard;
        else if (mode == 1)
            return meta;
        else
            return tank;
    }

    public DriveType.Types switchDrive(int counter){
        int mode = counter % 3;

        if (mode == 0)
            return Types.STANDARD;
        else if (mode == 1)
            return Types.META;
        else
            return Types.TANK;
    }

    public enum Types{
        STANDARD,
        META,
        TANK
    }
}

