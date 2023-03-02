package de.david.serverManagerBackend;

import de.david.serverManagerBackend.handler.BuildtoolsHandler;
import de.david.serverManagerBackend.handler.ConsoleHandler;
import de.david.serverManagerBackend.handler.SpigotHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    //static Properties config;
    //static Gson gson;
    private static ArrayList<ServerObject> servObjList;
    private static boolean loop;
    private static boolean runningProcess;

    public static void main(String[] args) throws IOException {


        //config = de.david.serverManager.Config.loadConfig();
        //gson = de.david.serverManager.Config.loadgson();
        //de.david.serverManager.Config.saveConfig(config);
        servObjList = Files.loadJson();

        /*System.out.println("Waiting for Client");
        ServerSocket ss = new ServerSocket(25565);
        Socket s = ss.accept();
        System.out.println("Client connected");
        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader rd = new BufferedReader(in);
        System.out.println(rd.readLine());*/
        // Reading data using readLine
        Scanner scn = new Scanner(System.in);
        //de.david.serverManager.Config.createDefaultgson();
        loop = true;
        runningProcess = false;
        System.out.println("install test2 test2 spigot/test2/ 1.19.3 spigot 25566 1G");
        sendWaitingMessage();
        while (loop){

            String line = scn.nextLine();
            String[] words = line.split(" ");
            //System.out.println(words[0] +" "+ words[1] +" "+ words[2]);
            switch (words[0].toLowerCase()) {
                case "end" -> {
                    Files.saveJson();
                    loop = false;
                }
                case "help" -> {
                    System.out.println("help");
                }
                case "install" -> {
                    //System.out.println("install");
                    if(words.length == 8) {
                        System.out.println(LocalDate.now().toString());
                        if (!BuildtoolsHandler.startBuildtools(words[1], words[2],
                                words[3], words[4], words[5],
                                words[6], words[7], LocalDate.now().toString())) {
                            loop = false;
                            // install test2 test2 spigot/test2/ 1.19.3 spigot 25565 1G
                        }
                        //System.out.println(words[1]+ words[2]+
                        //        words[3]+ words[4]+ words[5]+ words[6]);
                    }
                    else {
                        for(String s : words){
                            System.out.println(s);
                        }
                        System.out.println("Use: install [name] [screenName] [path (´spigot/test2/´)] [version (´1.19.3´)] [type (´spigot´)] [port (´25565´)] [ram (´2G´)]");
                    }

                    //System.out.println( "Xetron: " + runningProcess);
                }
                case "start" -> {
                    System.out.println("start");
                    //BuildtoolsHandler.test1();
                }
                case "stop" -> {
                    System.out.println("stop");
                }
                case "restart" -> {
                    System.out.println("restart");
                }
                case "test" -> {
                    ConsoleHandler.test();

                    System.out.println("test");

                    //ConsoleHandler.test();
                }
            }
            if(!runningProcess && loop){ sendWaitingMessage();}
            //getConfig().setProperty("input.last3", line + "1");
            //getConfig().setProperty("input.last2", line);
            //System.out.println("Input Time: " + (now-then) / 1000d);
            //System.out.println("Input was : " + line);
        }
    }

    public static void sendWaitingMessage(){
        System.out.println("Waiting for Input... (/help)");
    }

    public static ArrayList<ServerObject> getServerList() {
        return servObjList;
    }
    public static boolean getLoop() {
        return loop;
    }
    public static void setLoop(boolean loop) {
        Main.loop = loop;
    }

    public static boolean isRunningProcess() {
        return runningProcess;
    }

    public static void setRunningProcess(boolean runningProcess) {
        Main.runningProcess = runningProcess;
    }
}