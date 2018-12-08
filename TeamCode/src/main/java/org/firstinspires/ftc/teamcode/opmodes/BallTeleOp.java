package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.balldrive.DriveSubsystem;
import org.firstinspires.ftc.teamcode.balldrive.LiftSubsystem;
import org.firstinspires.ftc.teamcode.core.structure.ClassHolder;
import org.firstinspires.ftc.teamcode.core.iobuiltin.GamepadExtended;
import org.firstinspires.ftc.teamcode.core.OpModeExtended;
import org.firstinspires.ftc.teamcode.core.structure.Registry;
import org.firstinspires.ftc.teamcode.core.structure.Subsystem;

@TeleOp(name = "bla Teleop", group = "tele")
public class BallTeleOp extends OpModeExtended {

    public OpModeExtended.InputControlManager getInputControlManager() {
        return new BallTeleOp.TICM();
    }

    public ClassHolder getClassHolder() {
        return new Holder(this);
    }

    public class TICM extends OpModeExtended.TeleInputControlManager {
        Subsystem drive, lift;
        public void teleinit() {
            drive = Registry.getSubsystemByName("driveSubsystem");
            lift = Registry.getSubsystemByName("liftSubsystem");

            drive.setting("mode", DriveSubsystem.Mode.MANUAL_LRS);
            lift.setting("releaseMode", LiftSubsystem.ReleaseStage.LOWER);
            lift.setting("powerTime", 0);
            lift.setting("power", 0);
        }
        public void teleupdate() {
            switchDriveMode();
            lift();
            pawl();
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

        private void lift() {
            if (gamepadExtended1.left_trigger > GamepadExtended.DEADZONE) {
                lift.setting("power", -gamepadExtended1.left_trigger);
            } else if (gamepadExtended1.right_trigger > GamepadExtended.DEADZONE) {
                lift.setting("power", gamepadExtended1.right_trigger);
            } else {
                lift.setting("power", 0);
            }
        }

        private void pawl() {
            if (gamepadExtended1.y.equals(GamepadExtended.ButtonState.UPPING)) {
                lift.setting("toggle", true);
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
