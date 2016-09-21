package ru.levelp;

/**
 * Created by Юрий on 21.09.2016.
 */
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientExample {
    private static final String IP = "127.0.0.1"; //localhost
    private static final int PORT = 8080;

    public void start() {
        try {
            Socket socket = new Socket(IP, PORT);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            //Сначала отправляем логин!!!
            MessageHandler handler = new MessageHandler(socket);
            handler.start();
            System.out.println("Введите свой логин");
            System.out.println("Введите сообщение в формате @name@message для отправки личных сообщений");
            Message m = new Message();
            Gson gson = new Gson();
            while(true) {
                String message = consoleReader.readLine();
                if (message == null) {
                    break;
                }
                if (m.getSender()==null){
                    m.setSender(message);
                }
                else if (message.startsWith("@")){
                    String [] part = message.split("@");
                    String part1 = part[1];
                    String part2 = part[2];
                    m.setReceiver(part1);
                    m.setMessage(part2);
                }
                else {
                    m.setMessage(message);
                }
                String json = gson.toJson(m);
                writer.println(json);
                writer.flush();
                if (message.equals("exit")) {
                    break;
                }
            }
            handler.stopHandler();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
