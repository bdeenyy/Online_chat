package com.github.bdeenyy;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public Client() throws IOException {
        Settings.applySettings();
        Socket socket = new Socket(Settings.userHost, Settings.usersPort);
        Logger logger = Logger.getLogger();
        AnswerThread answerThread = new AnswerThread(socket);
        answerThread.start();
        logger.log(" Установлено соединение с сервером");
        boolean autoFlush = true;
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), autoFlush);
        Scanner scanner = new Scanner(System.in);
        // First message - client's name
        String name;
        System.out.println("Введите Ваше имя в чате:");
        name = scanner.nextLine();
        logger.log("Пользователь вошёл в чат под именем " + name);
        out.println(name);
        while (true) {
            String input;
            System.out.println("Введите сообщение или /exit для выхода:");
            input = scanner.nextLine();
            out.println(input);
            logger.log(input);
            if ("/exit".equalsIgnoreCase(input)) {
                break;
            }
        }
        answerThread.interrupt();
        logger.log("Пользователь вышел из чата.");
        out.close();
        scanner.close();
        socket.close();
    }
}
