package de.david.serverManagerBackend.handler;

import de.david.serverManagerBackend.Main;

import java.io.File;

public class SpigotHandler {


    public static boolean installSpigotServer(String name,String screenName,String serverDirectory,String version,String type,String port,String ram,String date){

        Runnable rn = () -> {
            System.out.println("Thread Id: " + Thread.currentThread().getId());


            //TODO : -Server ordner erstellen
            //TODO : -Spigot datei moven
            if(!moveSpigotFile(serverDirectory, version)){
                System.out.println("Server Jar konnte nicht verschoben werden");
                return;
            }

            //TODO : -plugin ordner erstellen
            //TODO : -Spigotmanager copieren
            if(!copySpigotPlugin( serverDirectory+"plugins/", version)){
                System.out.println("Spigot Plugins konnten nicht verschoben werden");
                return;
            }

            //TODO : -starupfile
            if(!FilesHandler.writeFile("start.sh" , serverDirectory, new String[]{"#!/bin/bash", "cd " + serverDirectory, "screen -dmS " + screenName + " java -Xms" + ram + " -Xmx" + ram +
                    " -XX:+AlwaysPreTouch -XX:+DisableExplicitGC -XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -XX:MaxGCPauseMillis=45 -XX:TargetSurvivorRatio=90 -XX:G1NewSizePercent=50 -XX:G1MaxNewSizePercent=80 -XX:InitiatingHeapOccupancyPercent=10 -XX:G1MixedGCLiveThresholdPercent=50"
                    + " -jar spigot-" + version + ".jar "}, true, "Spigot Startfile" )){
                System.out.println("Spigot Startfile konnte nicht beschrieben werden");
                return;
            }

            //TODO : -eula
            if(!FilesHandler.writeFile("eula.txt",serverDirectory, new String[]{"eula=true"}, false, "Eula file" )){
                System.out.println("Eula file konnte nicht beschrieben werden");
                return;
            }

            //TODO : -config
            if(!FilesHandler.writeFile("server.properties",serverDirectory, new String[]{"server-port=" + port}, false, "Properties file" )){
                System.out.println("Properties file konnte nicht beschrieben werden");
                return;
            }

            //TODO : -starten
            ConsoleHandler.runCommand(" ./" + serverDirectory + "/start.sh");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(!ConsoleHandler.testForRunningScreen(screenName)){
                System.out.println(screenName + " konnte nicht gestartet werden");
                return;
            }

            //TODO : -stoppen



            System.out.println("Der Spigot Server läuft.");
            Main.setRunningProcess(false);
            Main.sendWaitingMessage();
        };
        Thread Spigothandler = new Thread(rn);
        Spigothandler.start();
        Main.setRunningProcess(true);
        return true;
    }


    public static boolean moveSpigotFile(String serverDirectory, String version){
        File pth = new File(serverDirectory);
        if(pth.getParentFile().getParentFile() != null && !pth.getParentFile().getParentFile().exists()) {
            if (!pth.getParentFile().getParentFile().mkdir()) {
                return false;
            }
        }
        if(pth.getParentFile() != null && !pth.getParentFile().exists()) {
            if (!pth.getParentFile().mkdir()) {
                return false;
            }
        }
        if (!pth.exists()) {
            if (!pth.mkdir()) {
                return false;
            }
        }

        String moveFileError = ConsoleHandler.executeCommandAndReturnError("mv " + "buildtools/" + "spigot-" + version + ".jar " + serverDirectory);
        //System.out.println("mv " + "buildtools/" + "spigot-" + version + ".jar " + serverDirectory);
        if (moveFileError == null) {
            return false;
        }
        if (!moveFileError.contains("No such file or directory")) {
            System.out.println("Server Jar wurde verschoben");
            return true;
        }
        else {
            return false;
        }
    }


    public static boolean copySpigotPlugin(String pluginDirectory, String version){
        File pth = new File(pluginDirectory);
        if(!pth.exists()){
            if(!pth.mkdir()){
                return false;
            }
        }
        String copyFileError = ConsoleHandler.executeCommandAndReturnError("cp -r " + "library/" + "ServerManagerSpigot.txt " + pluginDirectory); //TODO: change from txt to jar
        //System.out.println("cp -r " + "library/" + "ServerManagerSpigot.txt " + pluginDirectory);
        if (copyFileError == null) {
            return false;
        }
        if (!copyFileError.contains("No such file or directory")) {
            System.out.println("Spigot Plugins wurde verschoben");
            return true;
        }
        else {
            return false;
        }
    }


}
