package org.firstinspires.ftc.teamcode.core.structure;

import android.util.Log;

import org.firstinspires.ftc.teamcode.core.OpModeExtended;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class Subsystem {
    public OpModeExtended context;
    public Map<String, Field> settings = new HashMap<>();

    public Subsystem(OpModeExtended context) {
        this.context = context;
    }

    public abstract void init();
    public abstract void updateData();
    public abstract void updateActuators();


    public void registerSettings() {
        Class clazz = this.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            String name = field.getName();
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Setting) {
                    settings.put(name, field);
                }
            }
        }
    }

    public void setting(String name, Object object) {
        try {
            settings.get(name).set(this, object);
        } catch (IllegalAccessException e) {
            Log.w("team-code", "IllegalAccessException from Subsystem.setting", e);
        }
    }

    public Object getSetting(String name) {
        try {
            return settings.get(name).get(this);
        } catch (IllegalAccessException e) {
            Log.w("team-code", "IllegalAccessException from Subsystem.getSetting", e);
            return null;
        }
    }
}
