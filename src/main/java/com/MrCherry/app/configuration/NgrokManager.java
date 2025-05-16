package com.MrCherry.app.configuration;



import java.io.IOException;

public class NgrokManager {
    //URL
    public static String notificationMP;

    public static void startNgrok() throws IOException {

        try{
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("ngrok","http","8080");
            Process process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
