package com.javarush.task.task30.task3008.client;

public class BotClient extends Client{
    @Override
    protected SocketThread getSocketThread(){
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole(){
        return  false; // бот не отправляет сообщения
    }

    @Override
    protected String getUserName(){
        return ("date_bot_"+(int)(Math.random()*100)); //генерация имени бота
    }


    public static void main(String[] args) {
        Client client = new BotClient(); //запуск бота
        client.run();
    }

    public class BotSocketThread extends SocketThread{

    }
}
