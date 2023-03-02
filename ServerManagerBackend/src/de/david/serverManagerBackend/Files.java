package de.david.serverManagerBackend;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Files {

    private static File json_file;
    private static ArrayList<ServerObject> servObjList;

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
                        System.out.println("Json File Saved");
                        return true;
                    }
                    else {
                        System.out.println("Die serverManager-servers.json konnte nicht Beschrieben werden");
                        return false;
                    }
                }
                else {
                    System.out.println("Die serverManager-servers.json konnte nicht Beschrieben werden");
                    return false;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else return false;
    }
}
