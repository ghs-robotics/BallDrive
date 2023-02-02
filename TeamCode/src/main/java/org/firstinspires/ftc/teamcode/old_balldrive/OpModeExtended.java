package org.firstinspires.ftc.teamcode.old_balldrive;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.old_balldrive.structure.Registry;
import org.majora320.tealisp.evaluator.LispException;

import java.io.File;
import org.firstinspires.ftc.teamcode.old_balldrive.iobuiltin.GamepadExtended;
import org.firstinspires.ftc.teamcode.old_balldrive.structure.ClassHolder;
import org.firstinspires.ftc.teamcode.old_balldrive.tealisp.TealispFileManager;
import org.firstinspires.ftc.teamcode.old_balldrive.tealisp.TealispSubsystemInterface;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

public abstract class OpModeExtended extends OpMode {
    public InputControlManager inputControlManager;
    public ClassHolder classHolder;
    public GamepadExtended gamepadExtended1;
    public GamepadExtended gamepadExtended2;

    public abstract InputControlManager getInputControlManager();
    public abstract ClassHolder getClassHolder();

    public final void init() {
        Log.i("team-code", "--------framework init start--------");
        try {
            this.inputControlManager = getInputControlManager();
            this.classHolder = getClassHolder();
            classHolder.getInterfaces().put("TealispSubsystemInterface", new TealispSubsystemInterface());

            this.gamepadExtended1 = new GamepadExtended(gamepad1);
            this.gamepadExtended2 = new GamepadExtended(gamepad2);
            gamepadExtended1.update();
            gamepadExtended2.update();

            Registry.grabData(classHolder);
            Registry.initSubsystems();
            inputControlManager.init();
        } catch (Exception e) {
            Log.e("team-code-log-error", getStackTrace(e));
        }
        Log.i("team-code", "--------framework init end----------");
    }

    public final void start() {
        Log.i("team-code", "--------main loop start--------");
    }

    public final void loop() {
        try {
            gamepadExtended1.update();
            gamepadExtended2.update();
            Registry.updateSubsystemData();
            inputControlManager.update();
            Registry.updateSubsystemActuators();
        } catch (Exception e) {
            Log.e("team-code-log-error", getStackTrace(e));
        }
    }

    public void stop() {
        Log.i("team-code", "--------main loop end--------");
        //LogRecorder.writeLog();
    }

    public interface InputControlManager {
        void init();
        void update();
    }

    public abstract class AutoInputControlManager implements InputControlManager {
        protected File teaLispFile = null;
        protected TealispFileManager manager;

        public final void init() {
            autoinit();
            manager = new TealispFileManager(teaLispFile, false, Registry.getInterfaces());

            try {
                manager.getInterpreter().getRuntime().callFunction("init");
            } catch (LispException e) {
                Log.e("team-code-log-error", "Exception in Tealisp init function.", e);
                System.exit(1);
            }
        }
        public final void update() {
            autoupdate();

            try {
                manager.getInterpreter().getRuntime().callFunction("update");
            } catch (LispException e) {
                Log.e("team-code-log-error", "Exception in Tealisp update function.", e);
                System.exit(1);
            }
        }

        public abstract void autoinit();
        public abstract void autoupdate();
    }

    public abstract class TeleInputControlManager implements InputControlManager {
        public final void init() {
            teleinit();
        }

        public final void update() {
            teleupdate();
        }

        public abstract void teleinit();
        public abstract void teleupdate();
    }
}
