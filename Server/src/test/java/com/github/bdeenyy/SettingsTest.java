package com.github.bdeenyy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.github.bdeenyy.Settings.SETTINGS_PATH;
import static org.junit.jupiter.api.Assertions.*;

class SettingsTest {

    @Test
    void getSettingsTest() throws IOException {
        int portFromTxt = 0;
        int portFromSettingsClass = Settings.getSettings();
        try(BufferedReader reader = new BufferedReader(new FileReader(SETTINGS_PATH))){
            portFromTxt = Integer.parseInt(reader.readLine());
        } catch (IOException e){
            e.printStackTrace();
        }
        Assertions.assertTrue(portFromTxt == portFromSettingsClass);
    }
}