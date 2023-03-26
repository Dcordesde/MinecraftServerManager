package de.david.serverManagerBackend.handler;

import java.io.IOException;
import java.net.ServerSocket;

public class ClientConnectionsHandler {

    public static void startSocket(){

        try {
            ServerSocket serverSocket = new ServerSocket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
