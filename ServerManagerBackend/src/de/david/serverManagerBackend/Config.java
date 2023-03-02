package de.david.serverManagerBackend;

import com.google.gson.Gson;

import java.io.*;
import java.util.Properties;

public class Config {


    private static File config;
    private static File json;
    private static FileInputStream fileInputStream;
    private static FileOutputStream fileOutputStream;
    private static Properties props;

    private static Gson Gson;






    public static boolean createDefaultgson(){
        json = new File("./serverManager-config.json");
        if(!json.exists()) {
            try {
                Gson gson = new Gson();
                json.createNewFile();
                Writer writer = new FileWriter(json,false);
                gson.toJson(writer);
                writer.flush();
                writer.close();

            } catch (IOException e) {
                json = null;
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public static Gson loadgson(){
        //createDefaultgson();
        if(json != null){
            try {
                fileInputStream = new FileInputStream(json);
                Gson gson = new Gson();
                gson.toJson(fileInputStream);
                fileInputStream.close();
                //System.out.println(props.getProperty("test"));
                return gson;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else return null;
    }

    public static boolean createDefualtFile(){
        config = new File("./serverManager-config.propeties");
        if(!config.exists()) {
            try {
                config.createNewFile() ;
                FileWriter fileWriter = new FileWriter(config);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                //printWriter.println(""); //TODO:
                printWriter.close();
                System.out.println("Created default config");
                return true;
            } catch (IOException e) {
                config = null;
                throw new RuntimeException(e);
            }
        }
        return false;
    }
    public static Properties loadConfig(){
        //createDefualtFile();
        if(config != null){
            try {
                fileInputStream = new FileInputStream(config);
                props = new Properties();
                props.load(fileInputStream);
                fileInputStream.close();
                //System.out.println(props.getProperty("test"));
                return props;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else return null;
    }
    public static void saveConfig(Properties props){
        config = new File("./serverManager-config.propeties");
        if(config.exists()) {
            try {
                if(config.delete() &&  config.createNewFile()) {
                    fileOutputStream = new FileOutputStream(config);
                    props.store(fileOutputStream, "");
                    fileOutputStream.close();
                    System.out.println("Saved config");
                }
                else {
                    System.out.println("Die de.david.serverManager.Config konnte nicht Beschrieben werden");
                }
            } catch (IOException e) {
                config = null;
                throw new RuntimeException(e);
            }
        }
    }

}


