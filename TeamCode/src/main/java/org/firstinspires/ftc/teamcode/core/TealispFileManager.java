package org.firstinspires.ftc.teamcode.core;

import android.os.FileObserver;
import android.support.annotation.Nullable;
import android.util.Log;

import org.majora320.tealisp.evaluator.Interpreter;
import org.majora320.tealisp.evaluator.JavaInterface;
import org.majora320.tealisp.evaluator.JavaRegistry;
import org.majora320.tealisp.evaluator.LispException;
import org.majora320.tealisp.lexer.LexException;
import org.majora320.tealisp.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class TealispFileManager extends FileObserver {
    private File file;
    private Interpreter interpreter;
    private Set<JavaInterface> interfaces;

    public TealispFileManager(File file, boolean updateInflight, Set<JavaInterface> interfaces) {
        super(file.getPath());
        this.file = file;
        this.interfaces = interfaces;
        loadFile();

        if (updateInflight)
            startWatching();
    }

    private void loadFile() {
        try {
            JavaRegistry registry = new JavaRegistry();
            registry.registerInterfaces(interfaces);

            interpreter = new Interpreter(new FileReader(file), registry);
        } catch (LispException | ParseException | LexException | IOException e) {
            if (interpreter == null) {
                Log.e("team-code-log-error", "Error in TeaLisp file loading for file "
                        + file.getName() + " during initialization; exiting.", e);
                System.exit(1);
            } else {
                Log.e("team-code-log-error", "Error in Tealisp file loading for file"
                        + file.getName() + ", continuing with old file.", e);
            }
        }
    }

    @Override
    public void onEvent(int event, @Nullable String path) {
        if (event != MODIFY && event != CREATE && event != MOVED_TO)
            return;

        loadFile();
    }

    public Interpreter getInterpreter() {
        return interpreter;
    }
}
