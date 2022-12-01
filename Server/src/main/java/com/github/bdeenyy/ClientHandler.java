package com.github.bdeenyy;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Server server;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private static final String HOST = "localhost";
    private static final int PORT = 3443;
    private Socket clientSocket = null;
    private static int clientsCount = 0;

    public ClientHandler(Socket socket, Server server) {
        try{
            clientsCount++;
            this.server = server;
            this.clientSocket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMgs(String mgs) {
    }

    @Override
    public void run() {
        try{
            while (true){
                server.sendMessageToAllClients("Новый участник вошел в чат!");
                server.sendMessageToAllClients("Клиентов в чате" + clientsCount);
                break;
            }
            while (true){
                if (inMessage.hasNext()){
                    String clientMessage = inMessage.nextLine();
                    if (clientMessage.equalsIgnoreCase("/exit")){
                        break;
                    }
                    System.out.println(clientMessage);
                    server.sendMessageToAllClients(clientMessage);
                }
                Thread.sleep(100);
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            this.close();
        }
    }

    private void close() {
        server.removeClient(this);
        clientsCount--;
        server.sendMessageToAllClients("Клиентов в чате" + clientsCount);
    }

    public void sendMsg (String msg){
        try{
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
