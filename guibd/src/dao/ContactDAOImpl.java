
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.DbWork;
import domain.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mushroomer
 */
public class ContactDAOImpl implements GeneticDAO<Contact> {

    @Override
    public Contact create(Contact object) {
        try {
            // Запрос следующего свободного id в последовательности
            final String SQL_SELECT_ID = "SELECT NEXTVAL('contact_seq') AS id";
            // Запрос вставки нового кортежа
            final String SQL_INSERT = "INSERT INTO contact(id, fullname, lastname, firstname, inblacklist) VALUES (?, ?, ?, ?, ?)";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            Statement statement = connection.createStatement();
            
            // Получаем следующий свободный id в последовательности
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ID);
            long id = -1;
            if (resultSet.next()) {
                id = resultSet.getLong("id");
            }
            
            // Устанавливаем значения в запрос
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, object.getFullName());
            preparedStatement.setString(3, object.getLastName());
            preparedStatement.setString(4, object.getFirstName());
            preparedStatement.setBoolean(5, object.isInBlackList());
            
            // Выполняем запрос
            preparedStatement.executeUpdate();
            
            // Возвращаем объект вставки
            object.setId(id);
            return object;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void update(Contact object) {
        try {
            // Запрос обновления данных кортежа
            final String SQL_UPDATE = "UPDATE contact SET fullname = ?, lastname = ?, firstname = ?, inblacklist = ? WHERE id = ?";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            
            // Устанавливаем значение в запрос
            preparedStatement.setLong(5, object.getId());
            preparedStatement.setString(1, object.getFullName());
            preparedStatement.setString(2, object.getLastName());
            preparedStatement.setString(3, object.getFirstName());
            preparedStatement.setBoolean(4, object.isInBlackList());
            
            // Выполняем запрос
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public Contact delete(Contact object) {
        try {
            // Запрос на удаление кортежа
            final String SQL_DELETE = "DELETE FROM contact WHERE id = ?";

            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);

            // Устанавливаем значение в запрос
            preparedStatement.setLong(1, object.getId());

            // Выполняем запрос
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return object;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Contact getById(long id) {
        try {
            // Запрос поиска кортежа по id
            final String SQL_SELECT_BY_ID = "SELECT * FROM contact WHERE id = ?";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            
            // Устанавливаем значение в запрос
            preparedStatement.setLong(1, id);
            
            // Выполняем запрос
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // Чтение результатов запроса
            Contact contact = null;
            if (resultSet.next()) {
                String fullName = resultSet.getString("fullname");
                String lastName = resultSet.getString("lastname");
                String firstName = resultSet.getString("firstname");
                Boolean inBlackList = resultSet.getBoolean("inblacklist");
                
                contact = new Contact(id, fullName, lastName, firstName, inBlackList);
            }
            
            // Возвращаем результат
            return contact;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public List<Contact> getAll() {
        try {
            // Запрос поиска всех кортежей
            final String SQL_SELECT = "SELECT * FROM contact";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            Statement statement = connection.createStatement();
            
            // Выполняем запрос
            ResultSet resultSet = statement.executeQuery(SQL_SELECT);
            
            // Чтение результатов поиска
            ArrayList<Contact> contacts = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String fullName = resultSet.getString("fullname");
                String lastName = resultSet.getString("lastname");
                String firstName = resultSet.getString("firstname");
                Boolean inBlackList = resultSet.getBoolean("inblacklist");
                
                contacts.add(new Contact(id, fullName, lastName, firstName, inBlackList));
            }
            
            // Возвращаем результат
            return contacts;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
    
}
