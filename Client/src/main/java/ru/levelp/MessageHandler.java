package ru.levelp;

/**
 * Created by Юрий on 21.09.2016.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageHandler extends Thread {
    private BufferedReader reader;
    private boolean alive = true;

    public MessageHandler(Socket socket) {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (alive) {
                String message = reader.readLine();
                if (message == null) {
                    break;
                }
                System.out.println(message);
            }
            stopHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void stopHandler() {
        if (alive && isAlive()) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            alive = false;
        }
    }
}