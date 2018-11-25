package dao;

import java.util.List;

public interface GeneticDAO<T> {
    // Добавление кортежа в таблицу
    public T create(T object);
    // Обновление данных кортежа таблицы
    public void update(T object);
    // Удаление кортежа из таблицы
    public T delete(T object);
    // Поиск кортежа по id
    public T getById(long id);
    // Поиск всех кортежей из таблицы
    public List<T> getAll();
}
