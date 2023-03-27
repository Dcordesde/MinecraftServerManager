package de.david.servermanagerspigot;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public final class Main extends JavaPlugin {

    private static Socket clientSocket;

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            System.out.println("connecting to serverManger");
            clientSocket = new Socket("localhost", 255);
            System.out.println("connected");
            PrintWriter pr = new PrintWriter(clientSocket.getOutputStream());
            pr.println("server online");
            pr.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
