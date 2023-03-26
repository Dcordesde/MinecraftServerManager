package de.david.serverManagerBackend;

import de.david.serverManagerBackend.handler.BuildtoolsHandler;
import de.david.serverManagerBackend.handler.ConsoleHandler;
import de.david.serverManagerBackend.handler.SpigotHandler;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //static Properties config;
    //static Gson gson;
    private static ArrayList<ServerObject> servObjList;
    private static boolean loop;
    private static boolean runningProcess;

    public static void main(String[] args) {



        //config = de.david.serverManager.Config.loadConfig();
        //gson = de.david.serverManager.Config.loadgson();
        //de.david.serverManager.Config.saveConfig(config);
        Files.createLogFile();
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
            //System.out.println(LocalDateTime.now());
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
                        /*DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("HH:mm:ss");
                        ZonedDateTime time = Instant.now().atZone(ZoneId.of("Europe/Berlin"));
                        System.out.println(time.format(dtformat));*/
                        runInstallMethods(words);
                       /* if (!BuildtoolsHandler.startBuildtools(words[1], words[2],
                                words[3], words[4], words[5],
                                words[6], words[7], LocalDate.now().toString())) {
                            loop = false;
                            // install test2 test2 spigot/test2/ 1.19.3 spigot 25565 1G
                        }*/
                        //System.out.println(words[1]+ words[2]+
                        //        words[3]+ words[4]+ words[5]+ words[6]);
                    }
                    else {
                        /*System.out.println("DEin befehl :")
                        for(String s : words){
                            System.out.println(s);
                        }*/
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

    private static void runInstallMethods(String[] words){
        // install test2 test2 spigot/test2/ 1.19.3 spigot 25565 1G
        if(words[5].equalsIgnoreCase("spigot")){
            //if(words[6].)
            Runnable rn = () -> {
                System.out.println("[" + Main.getTime() + " Debug]: Install Spigot Thread Id: " + Thread.currentThread().getId());
                if (!BuildtoolsHandler.startBuildtools(words[1], words[2],
                        words[3], words[4], words[5],
                        words[6], words[7], LocalDate.now().toString())) {
                    loop = false;

                }
                if (!SpigotHandler.installSpigotServer(words[1], words[2],
                        words[3], words[4], words[5],
                        words[6], words[7], LocalDate.now().toString())) {
                    loop = false;
                    // install test2 test2 spigot/test2/ 1.19.3 spigot 25565 1G
                }
                Main.setRunningProcess(false);
                Main.sendWaitingMessage();
            };
            Thread installSpigot = new Thread(rn);
            //System.out.println("Beta");
            installSpigot.start();
            Main.setRunningProcess(true);
        }
        else System.out.println("Use: install [name] [screenName] [path (´spigot/test2/´)] [version (´1.19.3´)] [type (´spigot´)] [port (´25565´)] [ram (´2G´)]");

    }


    public static String getTime(){
        DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("HH:mm:ss");
        ZonedDateTime time = Instant.now().atZone(ZoneId.of("Europe/Berlin"));
        return time.format(dtformat);
    }

    public static void sendWaitingMessage(){
        System.out.println("[" + Main.getTime() + " Info]: Waiting for Input... (/help)");
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