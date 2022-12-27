package com.github.bdeenyy;

import java.io.*;

// При первом запуске программы будет создан файл с дефолтным портом.
// Если файл с настройками не существует, то мы создаём его и записываем в него
//   дефолтный порт. Если существует - считываем порт из него.
// В дальнейшем, если изменить порт вручную в  файле settings.txt, будет использоваться
//  тот порт, который установил пользователь.

public class Settings {
    static String systemUserName = System.getProperty("user.name");
    static final String MAIN_DIR = getOSMainDir();
    static final int DEFAULT_PORT = 4321;
    public static int usersPort;
    static final String SETTINGS_PATH = MAIN_DIR + "//Settings.txt";
    static final boolean APP_END_SETTINGS = false;

    public static int getSettings() {
        File settings = new File(SETTINGS_PATH);
        if (!settings.exists()) {
            try {
                createDir();
                if (settings.createNewFile()) {
                    System.out.println("Создан файл конфигурации сети.");
                }
                ;
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            try (FileWriter fileWriter = new FileWriter(SETTINGS_PATH, APP_END_SETTINGS)) {
                fileWriter.write(Integer.toString(DEFAULT_PORT));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            usersPort = DEFAULT_PORT;
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(SETTINGS_PATH))) {
                usersPort = Integer.parseInt(reader.readLine());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return usersPort;
    }

    public static void createDir() throws IOException {
        File dir = new File(MAIN_DIR);
        if (dir.mkdir()) {
            String message = "Создана системная папка " + MAIN_DIR;
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
