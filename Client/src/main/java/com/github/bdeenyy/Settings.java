package com.github.bdeenyy;

import java.io.*;

public class Settings {
    static String systemUserName = System.getProperty("user.name");
    static final String MAIN_DIR = getOSMainDir();
    static final int DEFAULT_PORT = 4321;
    public static int usersPort;
    public static final String DEFAULT_HOST = "127.0.0.1";
    public static String userHost;
    static final String SETTINGS_PATH = MAIN_DIR + "//Settings.txt";
    static final boolean APP_END_SETTINGS = false;

    public static void applySettings() {
        File settings = new File(SETTINGS_PATH);
        if (!settings.exists()) {
            try {
                createDir();
                settings.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            try (FileWriter fileWriter = new FileWriter(SETTINGS_PATH, APP_END_SETTINGS)) {
                fileWriter.write(Integer.toString(DEFAULT_PORT) + " " + DEFAULT_HOST);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            usersPort = DEFAULT_PORT;
            userHost = DEFAULT_HOST;
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(SETTINGS_PATH))) {
                String inputData = reader.readLine();
                usersPort = Integer.parseInt(inputData);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void createDir() throws IOException {
        File dir = new File(MAIN_DIR);
        if (dir.mkdir()) {
            String message = "Создана системная папка " + MAIN_DIR;
            System.out.println(message);
            Logger.getLogger().log(message);
        }
    }
    public static String getOSMainDir() {
        // определение операционной системы с помощью системного свойства `os.name`
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "C:\\Users\\" + systemUserName + "\\Documents\\Online chat server";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return "/Home/" + systemUserName + "/Documents/Online chat server";
        } else if (os.contains("mac")) {
            return "/Users/" + systemUserName + "/Documents/Online chat server";
        }
        return null;
    }
}
