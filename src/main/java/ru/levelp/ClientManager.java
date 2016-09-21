package ru.levelp;

/**
 * Created by Юрий on 21.09.2016.
 */

import ru.levelp.entities.Message;

import java.net.Socket;
import java.util.ArrayList;

/*
Одиночка - Singleton
 */
public class ClientManager {
    private static ClientManager instance = new ClientManager();
    private ArrayList<ClientWorker> clients = new ArrayList<>();
    private Message message;

    private ClientManager() {
    }

    public static ClientManager getInstance() {
//        if (instance == null) {
//            //1 поток здесь
//            //2 тоже тут
//            instance = new ClientManager();
//        }
        return instance;
    }

    /**
     * Событие подключения клиента
     *
     * @param socket - сокет клиента
     */
    public void onClientConnected(final Socket socket) {
        Thread thread = new Thread(() -> {
            ClientWorker client = new ClientWorker(socket);
            client.login();
        });
//        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Событие окончания авторизации
     *
     * @param client авторизованный клиент
     */
    public void onClientSignedIn(ClientWorker client) {
        clients.add(client);
        System.out.println("ClientWorker " + client.getUsername() + " connected");
        client.startMessaging();

    }

    /**
     * Событие отключения клиента
     *
     * @param client отключаемый клиент
     */
    public void onClientDisconnected(ClientWorker client) {
        if (clients.remove(client)) {
            System.out.println("ClientWorker " + client.getUsername() + " out");
        }
    }

    /**
     * Рассылка сообщений клиентам
     *
     * @param message  отправляемое сообщение //TODO: type -> Message
     * @param receiver username получателя. Если null - рассылка всем
     */
    public void sendMessage(Message message, Message receiver) {
        if (receiver == null && message != null) {
            for (ClientWorker client : clients) {
                client.sendMessage(message);
            }
        }
        else {
            for (ClientWorker client : clients){
                if (receiver.getReceiver().equals(receiver) && message != null){
                    client.sendMessage(message);
                }
            }
        }
    }
    public boolean hasClient(String username) {
        for (ClientWorker c : clients) {
            if (c.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}