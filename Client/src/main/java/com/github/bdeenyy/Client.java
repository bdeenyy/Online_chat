package com.github.bdeenyy;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3443;
    private Socket clientSocket;
    private Scanner inMessage;
    private PrintWriter outMessage;
    private String clientName = "";

    public String getClientName() {
        return this.clientName;
    }

    public Client(){
        try {
            clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
            System.out.println("Соединение установлено!");
            inMessage = new Scanner(clientSocket.getInputStream());
            outMessage = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
