package org.firstinspires.ftc.teamcode.old_balldrive.structure;

import android.util.Log;

import org.majora320.tealisp.evaluator.JavaInterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



public class Registry {
    private static Map<String, org.firstinspires.ftc.teamcode.old_balldrive.structure.SensorManager> sensors = new HashMap<>();
    private static Map<String, Subsystem> subsystems = new HashMap<>();
    private static Map<String, JavaInterface> interfaces = new HashMap<>();
    public static void grabData(ClassHolder classHolder) {
        if (classHolder == null) {
            Log.i("team-code", "classHolder null: sensors and subsystems made null");
            sensors = null;
            subsystems = null;
            return;
        }

        subsystems.putAll(classHolder.getSubsystems());
        interfaces.putAll(classHolder.getInterfaces());
    }

    public static void addSensorManager(String name, org.firstinspires.ftc.teamcode.old_balldrive.structure.SensorManager sensor) {
        Log.d("team-code", "adding SensorManager " + name
                + (sensor == null ? " (null)" : " (nonnull)"));
        if (sensors == null)
            Log.w("team-code", "sensors null, but attempt made to add SensorManager");
        sensors.put(name, sensor);
    }

    public static void addSubsystem(String name, Subsystem subsystem) {
        Log.d("team-code", "adding Subsystem " + name
                + (subsystem == null ? " (null)": " (nonnull)"));
        if (subsystems == null)
            Log.w("team-code", "subsystems null, but attempt made to add Subsystem");
        subsystems.put(name, subsystem);
    }

    public static void addInterface(String name, JavaInterface iface) {
        Log.d("team-code", "adding Interface "
                + (iface == null ? " (null)": " (nonnull)"));
        if (interfaces == null)
            Log.w("team-code", "interfaces null, but attempt made to add Interface");
        interfaces.put(name, iface);
    }

    public static Subsystem getSubsystemByName(String name) {
        if (subsystems == null) {
            Log.w("team-code-spam", "attempt to fetch Subsystem " + name
                    + " but subsystems is null");
        }
        Subsystem result = subsystems.get(name);
        if (result == null)
            Log.w("team-code-spam", "get by name: could not find Subsystem named " + name);
        return result;
    }

    public static JavaInterface getInterfaceByName(String name) {
        if (interfaces == null) {
            Log.w("team-code", "attempt to fetch JavaInterface" + name
                    + "but interfaces is null");
        }
        JavaInterface result = interfaces.get(name);
        if (result == null)
            Log.w("team-code", "get by name: could not find JavaInterface named " + name);
        return result;
    }

    public static Set<JavaInterface> getInterfaces() {
        if (interfaces == null) {
            Log.w("team-code", "attempt to fetch interfaces, but it is null");
        }
        return new HashSet<>(interfaces.values());
    }

    public static void initSubsystems() {
        if (subsystems == null)
            return;
        for (Subsystem subsystem : subsystems.values()) {
            subsystem.registerSettings();
            subsystem.init();
        }
    }

    public static void updateSubsystemData() {
        if (subsystems == null)
            return;
        for (Subsystem subsystem : subsystems.values()) {
            subsystem.updateData();
        }
    }

    public static void updateSubsystemActuators() {
        if (subsystems == null)
            return;
        for (Subsystem subsystem : subsystems.values()) {
            subsystem.updateActuators();
        }
    }
}
