package de.david.serverManagerBackend.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnectionsHandler {

    public static void startSocket(){

        try {
            ServerSocket serverSocket = new ServerSocket(25500);
            waitForNewConnection(serverSocket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void waitForNewConnection(ServerSocket serverSocket){
        Runnable rn = () -> {
            try {
                Socket s = serverSocket.accept();
                System.out.println("Client connected");
                //s.
                InputStreamReader in = new InputStreamReader(s.getInputStream());
                BufferedReader rd = new BufferedReader(in);

                while (rd.ready()){

                }

                System.out.println(rd.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Thread newClientConnection = new Thread(rn);
        newClientConnection.start();
    }

}
