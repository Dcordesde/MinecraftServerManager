package de.david.serverManagerBackend.handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FilesHandler {

    public static boolean writeStartFile(String path, String startCommand){
        File file = new File(path + "start.sh");
        try {
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("#!/bin/bash");
            printWriter.println("cd " + path);
            printWriter.println(startCommand);
            printWriter.close();
            try {
                Runtime.getRuntime().exec("chmod +x " + file);
                System.out.println("Startfile wurde beschrieben");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeFile(String fileName, String path, String[] lines, boolean executable, String fileCallingName){
        File file = new File(path + fileName);
        try {
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (String s : lines){
                printWriter.println(s);
            }
            printWriter.close();
            if(executable) {
                try {
                    Runtime.getRuntime().exec("chmod +x " + file);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            System.out.println(fileCallingName + " wurde beschrieben");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static String getLineOfAFile(File file, String keyword){
        try {
            /*if(!file.exists()) {
                file.createNewFile();
            }*/
            Scanner scn = new Scanner(file);
            while (scn.hasNextLine()) {
                String line = scn.nextLine();
                if(line.contains(keyword)){
                    scn.close();
                    return line;
                }
            }

            scn.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }





}
