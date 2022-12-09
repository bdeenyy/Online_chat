package com.github.bdeenyy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class AnswerThread extends Thread {
    private final Socket socket;

    public AnswerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            Logger logger = Logger.getLogger();
            while (!isInterrupted()) {
                logger.log(in.readLine());
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
