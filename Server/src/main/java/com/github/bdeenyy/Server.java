package com.github.bdeenyy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class Server {
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public Server() {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            Logger logger = Logger.getLogger();
            serverSocket = new ServerSocket(Settings.getSettings());
            logger.log("Сервер запущен.");
            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                Logger.getLogger().log("Сервер остановлен");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendMessageToAllClients(String message) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        LocalDateTime time = LocalDateTime.now();
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMsg(formatter.format(time) + " " + message);
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}