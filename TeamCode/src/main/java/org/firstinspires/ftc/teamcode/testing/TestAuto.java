package org.firstinspires.ftc.teamcode.testing;

import android.os.Environment;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.structure.ClassHolder;
import org.firstinspires.ftc.teamcode.core.OpModeExtended;
import org.firstinspires.ftc.teamcode.core.structure.Registry;
import org.firstinspires.ftc.teamcode.core.structure.SensorManager;
import org.firstinspires.ftc.teamcode.core.structure.Subsystem;

import java.io.File;
import java.util.HashMap;

@Autonomous(name = "Test", group = "Tests")
public class TestAuto extends OpModeExtended {

    public InputControlManager getInputControlManager() {
        return new AICM();
    }

    public ClassHolder getClassHolder() {
        return new TestHolder(this);
    }

    public class AICM extends AutoInputControlManager {
        public void autoinit() {
            teaLispFile = new File(Environment.getExternalStorageDirectory() + "/test.tl");
            Subsystem subsystem = Registry.getSubsystemByName("fakeLoggingSubsystem");
            subsystem.setting("logMessages", new HashMap<String, String>());
        }

        public void autoupdate() {
            Subsystem subsystem = Registry.getSubsystemByName("fakeLoggingSubsystem");
            SensorManager sensorManager = Registry.getSensorManagerByName("fakeTimeSensor");

            //Map<String, String> logMessages =
            //        (Map<String, String>) subsystem.getSetting("logMessages");
            //logMessages.put("test time", "" + sensorManager.getCM() );

            Log.d("Tealisp", "Tealist global result: " + manager.getInterpreter().getGlobalResult());
        }
    }
}
