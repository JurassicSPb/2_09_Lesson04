package ru.levelp.dao;

/**
 * Created by Юрий on 21.09.2016.
 */
import ru.levelp.entities.Message;
import java.util.List;

/**
 * Методы, которые должна содержать любая база данных для хранения Message
 */
public interface MessageDAO {
    void add(Message message);

    Message get(int id);

    List<Message> getAll();

    Message remove(int id);
}