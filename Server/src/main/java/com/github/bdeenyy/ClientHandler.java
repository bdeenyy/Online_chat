package com.github.bdeenyy;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Server server;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = Settings.usersPort;
    private Socket clientSocket = null;
    private static int clients_count = 0;
    private String name;

    public ClientHandler(Socket socket, Server server) throws IOException {
        clients_count++;
        this.server = server;
        this.clientSocket = socket;
        this.outMessage = new PrintWriter(socket.getOutputStream());
        this.inMessage = new Scanner(socket.getInputStream());
        this.name = null;
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    name = inMessage.nextLine();
                    // первое сообщение от клиента должно быть его именем
                    String message = name + " вошёл в чат. Людей в чате = " + clients_count;
                    server.sendMessageToAllClients(message);
                    Logger.getLogger().log(message);
                    break;
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            while (true) {
                if (inMessage.hasNext()) {
                    String clientMessage = inMessage.nextLine();
                    if (clientMessage.equalsIgnoreCase("/exit")) {
                        clients_count--;
                        String message = (name + " " + " вышел из чата. Клиентов в чате: " + clients_count);
                        server.sendMessageToAllClients(message);
                        Logger.getLogger().log(message);
                        break;
                    }
                    try {
                        Logger.getLogger().log(name + ": " + clientMessage);
                        server.sendMessageToAllClients(name + ": " + clientMessage);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
    }

    public void sendMsg(String msg) {
        try {
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        server.removeClient(this);
    }
}