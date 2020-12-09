package com.javarush.task.task30.task3008.client;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class ClientGuiModel {
    private final Set <String> allUserNames = new TreeSet<String>();
    private String newMessage;

    public Set<String> getAllUserNames() {
        return Collections.unmodifiableSet(allUserNames);
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    public void addUser(String user){
        allUserNames.add(user);
    }

    public void deleteUser(String user){
        allUserNames.remove(user);
    }
}
