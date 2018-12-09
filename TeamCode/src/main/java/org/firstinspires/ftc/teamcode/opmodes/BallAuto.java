package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.balldrive.DriveSubsystem;
import org.firstinspires.ftc.teamcode.balldrive.LiftSubsystem;
import org.firstinspires.ftc.teamcode.core.structure.ClassHolder;
import org.firstinspires.ftc.teamcode.core.OpModeExtended;
import org.firstinspires.ftc.teamcode.core.structure.Registry;

import java.io.File;

@Autonomous(name = "bla Auto", group = "tele")
public class BallAuto extends OpModeExtended {

    public OpModeExtended.InputControlManager getInputControlManager() {
        return new BallAuto.AICM();
    }

    public ClassHolder getClassHolder() {
        return new Holder(this);
    }

    public class AICM extends OpModeExtended.AutoInputControlManager {
        DriveSubsystem drive;
        LiftSubsystem lift;

        @Override
        public void autoinit() {
            teaLispFile = new File("./storage/emulated/0/bluetooth/ballauto.tl");
            drive = (DriveSubsystem) Registry.getSubsystemByName("driveSubsystem");
            drive.setting("mode", DriveSubsystem.Mode.AUTO_IDLE);
            lift = (LiftSubsystem) Registry.getSubsystemByName("liftSubsystem");

            MainInterface inter = (MainInterface) Registry.getInterfaceByName("main");
            inter.setDrive(drive);
            inter.setLift(lift);
        }

        @Override
        public void autoupdate() {

        }
    }
}
