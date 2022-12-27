package com.github.bdeenyy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


class SettingsTest {

    @Test
    void applySettingsTestPort() {
        int testPort = 0;
        Settings.applySettings();
        try (BufferedReader reader = new BufferedReader(new FileReader(Settings.SETTINGS_PATH))){
            String[] inputData = reader.readLine().split(" ");
            testPort = Integer.parseInt(inputData[0]);
        } catch (IOException e){
            e.printStackTrace();
        }
        boolean isPortTheSame = testPort == Settings.usersPort;
        Assertions.assertTrue(isPortTheSame);
    }

}