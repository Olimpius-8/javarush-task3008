package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket){
            this.socket = socket;
        }

        private void serverMainLoop(Connection connection, String username) throws IOException, ClassNotFoundException {
            while (true){
                Message message = connection.receive();
                if (message.getType()==MessageType.TEXT) {
                    message = new Message(MessageType.TEXT, username + ": " + message.getData());
                    sendBroadcastMessage(message);
                }
                else
                    ConsoleHelper.writeMessage("Error");
            }
        }

        private void notifyUsers (Connection connection, String userName) throws IOException {
            for (String name: connectionMap.keySet()) {
                if (!name.equals(userName))
                    connection.send(new Message(MessageType.USER_ADDED, name));
            }
        }

        private String serverHandshake (Connection connection) throws IOException, ClassNotFoundException {
            Message answer;
            while (true) {
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message message=connection.receive();
                if (message.getType().equals(MessageType.USER_NAME) &&
                        !message.getData().isEmpty() &&
                        !connectionMap.containsKey(message.getData()))
                {
                    connectionMap.put(connection.receive().getData(), connection);
                    connection.send(new Message(MessageType.NAME_ACCEPTED));
                    return connection.receive().getData();
                }
            }
        }
    }

    public static void sendBroadcastMessage(Message message){
        try{
            for (Map.Entry<String, Connection> pair : connectionMap.entrySet())
                pair.getValue().send(message);
        }catch (IOException e){
            System.out.println("Message is not send");
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
