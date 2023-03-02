package de.david.serverManagerBackend.handler;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHandler {

    public static boolean testForRunningScreen(String stringName){
        try {
            Process process = Runtime.getRuntime().exec("screen -ls " + stringName);
            //System.out.println("Test for Running Screen Debug:" + stringName);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = in.readLine()) != null){
                content.append(line);
            }
            //System.out.println("Test for Running Screen Debug:" + content);
            return content.toString().contains("There is a screen on");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


    public static String executeCommandAndReturnError(String cmd){
        try {
            Process process2 = Runtime.getRuntime().exec(cmd);
            BufferedReader rd = new BufferedReader(new InputStreamReader(process2.getErrorStream()));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = rd.readLine()) != null){
                content.append(line);
            }
            //System.out.println(content);
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    public static String executeCommandAndReturnInputStream(String cmd){
        try {
            Process process2 = Runtime.getRuntime().exec(cmd);
            BufferedReader rd = new BufferedReader(new InputStreamReader(process2.getInputStream()));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = rd.readLine()) != null){
                content.append(line);
            }
            //System.out.println(content);
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }



    public static void runCommand(String cmd){
        //String cmd = "screen -dmS test1";
        try {
            Process pr = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void test(){

        //System.out.println( "Input " +executeCommandAndReturnInputStream("mv buildtools/spigot-1.19.3.jar test187/"));

        //System.out.println( "Error " +executeCommandAndReturnError("mv buildtools/spigot-1.19.3.3.jar test187/"));
        //SpigotHandler.installSpigotServer("test3", "test3", "test2/spigot/test3/", "1.19.3", "spigot", "25565", "1G", LocalDate.now().toString());
       /* try {
                        Process process = Runtime.getRuntime().exec("wget -O buildtools/BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar");

            InputStreamReader rd = new InputStreamReader(process.getInputStream());
            BufferedReader br = new BufferedReader(rd);

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String l1 = null;
            String l2 = null;
            String l = null;
            System.out.println("asdad");
            while ((l = in.readLine()) != null){
                System.out.println(l);
                if(l1 == null) l1 = l;
                else if(l2 == null) l2 = l;
            }
            if(l1.contains("There is a screen on")){
                System.out.println("Screen is running");
            }
            else System.out.println("Screen isn't running");


            String s = null;
            while ((s = in.readLine()) != null){
                if(s.contains("There is a screen on")){
                    System.out.println("Hier:");
                    System.out.println(s);
                    return;
                }
                else System.out.println(s);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }









}
