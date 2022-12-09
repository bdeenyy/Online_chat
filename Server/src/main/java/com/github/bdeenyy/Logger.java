package com.github.bdeenyy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Logger {
    private static Logger instance = null;
    private String userName;
    private final String LOG_PATH;
    private final boolean APP_END;

    public String getLOG_PATH() {
        return LOG_PATH;
    }

    private Logger() throws IOException {
        this.userName = System.getProperty("user.name");
        this.LOG_PATH = Settings.MAIN_DIR + "//file.log";
        this.APP_END = true;
        Settings.createDir();
    }

    public static Logger getLogger() throws IOException {
        if (instance == null) instance = new Logger();
        return instance;
    }

    public void log(String text) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(LOG_PATH, APP_END)) {
            synchronized (this) {
                DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
                LocalDateTime time = LocalDateTime.now();
                String textLineBreaked = formatter.format(time) + " " + text + "\n";
                System.out.println(textLineBreaked);
                byte[] buffer = textLineBreaked.getBytes();
                fileOutputStream.write(buffer, 0, buffer.length);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
