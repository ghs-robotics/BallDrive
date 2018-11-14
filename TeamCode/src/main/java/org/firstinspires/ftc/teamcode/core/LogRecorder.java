package org.firstinspires.ftc.teamcode.core;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

public class LogRecorder {
    public static void writeLog() {
        try {
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "team-code-logs");

            if(!dir.exists()) {
                dir.mkdirs();
            }

            int fileNumber = 0;
            File logOutput = new File(dir, "logcat-0000.txt");
            while (logOutput.exists()) {
                fileNumber++;
                logOutput = new File(dir,
                        "logcat-" + String.format("%04d", fileNumber) + ".txt");
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    Runtime.getRuntime().exec("logcat -d").getInputStream()));
            StringBuilder log = new StringBuilder();
            String line;

            while((line = bufferedReader.readLine()) != null) {
                if (line.contains("team-code")) {
                    log.append(line);
                    log.append("\n");
                }
            }

            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(logOutput));

            osw.write(log.toString());
            osw.flush();
            osw.close();

            Runtime.getRuntime().exec("logcat -c");
        } catch (Exception e) {
            Log.e("team-code-log-error", getStackTrace(e));
        }
    }
}
