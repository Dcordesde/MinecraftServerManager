package de.david.serverManagerBackend.handler;

import de.david.serverManagerBackend.Main;

import java.io.File;

public class BuildtoolsHandler{

   /* public static void test1(){
        CheckForBuildtoolsRunnable bt1 = new CheckForBuildtoolsRunnable("");
        CheckForBuildtoolsRunnable bt2 = new CheckForBuildtoolsRunnable("");

        Thread t1 = new Thread(bt1);
        Thread t2 = new Thread(bt2);

        t1.start();
        t2.start();

    }*/

    public static boolean startBuildtools(String name,String screenName,String directory,String version,String type,String port,String ram,String date){

        Runnable rn = () -> {
            System.out.println("Thread Id: " + Thread.currentThread().getId());

            if(!BuildtoolsHandler.downloadBuildtoolsFile("buildtools/")){
                System.out.println("Buildtools konnte nicht Heruntergeladen werden");
                return;
            }

            /*if(!FilesHandler.writeStartFile("buildtools/", "java -jar BuildTools.jar --rev " + version)){
                System.out.println("Startfile konnte nicht beschrieben werden");
                return;
            }*/
            if(!FilesHandler.writeFile("start.sh","buildtools/", new String[]{"#!/bin/bash", "cd buildtools/", "java -jar BuildTools.jar --rev " + version}, true, "Buildtools Startfile" )){
                System.out.println("Startfile konnte nicht beschrieben werden");
                return;
            }
            //            printWriter.println("#!/bin/bash");
            //            printWriter.println("cd " + path);
            //            printWriter.println(startCommand); "java -jar BuildTools.jar --rev " + version
            ConsoleHandler.runCommand("screen -dmS buildTools buildtools/start.sh");
            if(!ConsoleHandler.testForRunningScreen("buildTools")){
                System.out.println("Buildtools konnte nicht gestartet werden");
                return;
            }

            System.out.println("Auf Buildtools warten..");
            int debug = 0;
            while (ConsoleHandler.testForRunningScreen("buildTools")){
                try {
                    Thread.sleep(10000);
                    debug = debug + 10;
                    System.out.println("Debug: " + debug + "sec");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Buildtools ist beendet");

            if(!ConsoleHandler.executeCommandAndReturnInputStream("ls buildtools/").contains("spigot-" + version + ".jar")){  //TODO: check for spigot file
                System.out.println("Spigot Datei exisitert nicht");
                return;
            }
            System.out.println("Die Spigot Datei wurde erfolgreich Heruntergeladen");


            Main.setRunningProcess(false);
            Main.sendWaitingMessage();
            if (!SpigotHandler.installSpigotServer(name,screenName,directory,version,type,port,ram,date)) {
                Main.setLoop(false);
                // install test2 test2 spigot/test2/ 1.19.3 spigot 25565 1G
            }
            //System.out.println("Yamma");
        };
        //System.out.println("Alpha");
        Thread buildtools = new Thread(rn);
        //System.out.println("Beta");
        buildtools.start();
        Main.setRunningProcess(true);
        //System.out.println("Gamma");
        return true;
    }


    public static boolean downloadBuildtoolsFile(String path) {
        /*try {*/
            File pth = new File(path);
            if(pth.getParentFile() != null && !pth.getParentFile().exists()) {
                if (!pth.getParentFile().mkdir()) {
                    return false;
                }
            }
            if(!pth.exists()){
                if(!pth.mkdir()){
                    return false;
                }
            }
            String wgetBuild = ConsoleHandler.executeCommandAndReturnError("wget -O " + path + "BuildTools.jar " +
                    "https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar");
            if (wgetBuild == null) {
                return false;
            }
            if (wgetBuild.contains("‘" + path + "BuildTools.jar’ saved")) {
                System.out.println("Buildtools file wurde heruntergeladen");
                return true;
            }
            else {
                return false;
            }
        /*}catch (IOException e){
            e.printStackTrace();
            return false;
        }*/
    }
}

