package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket){
            this.socket = socket;
        }


    }


    public static void main(String args[]) throws IOException{
        ConsoleHelper helper = new ConsoleHelper();
        try (ServerSocket serverSocket = new ServerSocket(helper.readInt())){
            System.out.println("Server is online");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Handler handler = new Handler(clientSocket);
                handler.start();
                clientSocket.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
