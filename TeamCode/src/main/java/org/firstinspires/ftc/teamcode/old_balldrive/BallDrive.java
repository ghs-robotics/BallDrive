package org.firstinspires.ftc.teamcode.old_balldrive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.old_balldrive.structure.ClassHolder;
import org.firstinspires.ftc.teamcode.old_balldrive.iobuiltin.GamepadExtended;
import org.firstinspires.ftc.teamcode.old_balldrive.structure.Registry;
import org.firstinspires.ftc.teamcode.old_balldrive.structure.Subsystem;

@TeleOp(name = "bla drive", group = "tele")
public class BallDrive extends OpModeExtended {

    public OpModeExtended.InputControlManager getInputControlManager() {
        return new BallDrive.TICM();
    }

    public ClassHolder getClassHolder() {
        return new org.firstinspires.ftc.teamcode.old_balldrive.structure.Holder(this);
    }

    public class TICM extends OpModeExtended.TeleInputControlManager {
        Subsystem drive;
        int i;
        public void teleinit() {
            drive = Registry.getSubsystemByName("driveSubsystem");

            drive.setting("mode", DriveSubsystem.Mode.MANUAL_LRS);
        }
        public void teleupdate() {
            switchDriveMode();
            drive();

        }

        private void switchDriveMode() {
            if (gamepadExtended1.a == GamepadExtended.ButtonState.DOWNING) {
                DriveSubsystem.Mode mode = (DriveSubsystem.Mode) drive.getSetting("mode");
                switch (mode) {
                    case MANUAL_XYR:
                        drive.setting("mode", DriveSubsystem.Mode.MANUAL_LRS);
                        break;
                    case MANUAL_LRS:
                        drive.setting("mode", DriveSubsystem.Mode.MANUAL_XYR);
                        break;
                }
            }
        }

        private void drive() {
            switch ((DriveSubsystem.Mode) drive.getSetting("mode")) {
                case MANUAL_LRS:
                    drive.setting("manualL", -gamepadExtended1.left_stick_y);
                    drive.setting("manualR", -gamepadExtended1.right_stick_y);
                    drive.setting("manualS", (gamepadExtended1.left_stick_x + gamepadExtended1.right_stick_x) / 2);
                    break;
                case MANUAL_XYR:
                    drive.setting("manualX", gamepadExtended1.left_stick_x);
                    drive.setting("manualY", -gamepadExtended1.left_stick_y);
                    drive.setting("manualR", -gamepadExtended1.right_stick_x);
                    break;
            }
        }
    }
}
