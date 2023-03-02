package de.david.serverManagerBackend;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Files {

    private static File json_file;
    private static ArrayList<ServerObject> servObjList;

    private static File errorLogFile;
    private static ArrayList<String> errors;

    public static boolean createDefaultJson(){
        json_file = new File("./serverManager-servers.json");
        if(!json_file.exists()){
            try{
                json_file.createNewFile();
                //TODO: Test entfernen
                servObjList = new ArrayList<>();
                /*servObjList.add(new ServerObject("testname", "testname",
                        "/home/wwwasd", "1.19.2", "spigot", "25565",
                        "01.01.2022"));*/
                Gson gson = new Gson();
                Writer writer = new FileWriter(json_file,false);
                gson.toJson(servObjList, writer);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        else return false;
    }


    public static ArrayList<ServerObject> loadJson(){
        createDefaultJson();
        if (json_file.exists()) {
            try {
                Gson gson = new Gson();
                Reader reader = new FileReader(json_file);
                ServerObject[] servObjL = gson.fromJson(reader, ServerObject[].class);
                if(servObjL == null){
                    servObjList = new ArrayList<>();
                    //servObjList.add(new de.david.serverManager.ServerObject("TestServer"))
                }
                else {
                    servObjList = new ArrayList<>(Arrays.asList(servObjL));
                }

                reader.close();
                return servObjList;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else return null;
    }


    public static boolean saveJson(){
        if (json_file.exists()) {
            try{
                if(json_file.delete() &&  json_file.createNewFile()) {
                    if (json_file.exists()) {
                        Gson gson = new Gson();
                        Writer writer = new FileWriter(json_file, false);
                        gson.toJson(Main.getServerList(), writer);
                        writer.flush();
                        writer.close();
                        System.out.println("[" + Main.getTime() + " Info]: Json File wurde gespeichert");
                        return true;
                    }
                    else {
                        System.out.println("[" + Main.getTime() + " Error]: Die serverManager-servers.json konnte nicht Beschrieben werden");
                        return false;
                    }
                }
                else {
                    System.out.println("[" + Main.getTime() + " Error]: Die serverManager-servers.json konnte nicht Beschrieben werden");
                    return false;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else return false;
    }


    public static boolean createLogFile(){
        errorLogFile = new File("./ErrorLog.txt");
        try {
            if (errorLogFile.createNewFile()) {
                FileWriter fileWriter = new FileWriter(errorLogFile);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                //printWriter.println(""); //TODO:
                printWriter.close();
                //System.out.println("[" + Main.getTime() + " Info]: Errorlog.txt wurde erstellt"); //TODO: Nachrichten
                return true;
            }
            else return false;
        } catch(IOException e){
            errorLogFile = null;
            throw new RuntimeException(e);
        }
    }
    public static boolean saveLogFile(ArrayList<String> newErrors){
        errorLogFile = new File("./ErrorLog.txt");
        if(errorLogFile.exists()) {
            if(newErrors != null) {
                try {
                    FileWriter fileWriter = new FileWriter(errorLogFile);
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    for (String s : newErrors) {
                        printWriter.write(s);
                    }
                    printWriter.close();
                    System.out.println("[" + Main.getTime() + " Info]: Die Fehlermeldung wurde in die ErrorLog.txt geschrieben");
                    return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return false;
        }
        errors = null;
        return false;
    }


    public static boolean addErrorLine(String s){
        if(!s.equals("") && errors != null){
            errors.add(s);
            return saveLogFile(errors);
        }
        return false;
    }

}
