package ru.levelp;

/**
 * Created by Юрий on 21.09.2016.
 */

import ru.levelp.entities.Message;

import java.util.ArrayList;

public class SendingThread extends Thread {
    //private volatile ArrayList<String> queue; //TODO: <Message>
    private volatile ArrayList<Message> queue;
    private boolean alive = true;

    public SendingThread() {
        queue = new ArrayList<>();
    }

    @Override
    public void run() {
        while (alive) {
            if (queue.isEmpty()) {
                Thread.yield();
            } else if (alive){
//                ClientManager.getInstance().sendMessage(queue.get(0).getReceiver()+":"+queue.get(0).getMessage(), null);
                ClientManager.getInstance().sendMessage(queue.get(0), null);
                queue.remove(0);
            }
        }
    }

    //TODO: message -> type Message
    //main-thread
    public void addMessage(Message message) {
        queue.add(message);
    }

    public void stopSending() {
        alive = false;
    }
}