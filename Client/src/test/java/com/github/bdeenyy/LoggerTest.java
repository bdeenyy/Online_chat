package com.github.bdeenyy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


class LoggerTest {

    @Test
    public void logTest() throws IOException {
        String testMessage = "testmessage";
        Logger.getLogger().log(testMessage);
        boolean testResult = false;
        Scanner scanner = new Scanner(new File(Logger.getLogger().getLOG_PATH()));
        while (scanner.hasNext()) {
            if (scanner.nextLine().contains(testMessage)) {
                testResult = true;
            }
        }
        Assertions.assertTrue(testResult);
    }

}