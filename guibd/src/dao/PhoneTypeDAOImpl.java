/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.DbWork;
import domain.PhoneType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mushroomer
 */
public class PhoneTypeDAOImpl implements GeneticDAO<PhoneType> {

    @Override
    public PhoneType create(PhoneType object) {
        try {
            // Запрос следующего свободного id в последовательности
            final String SQL_SELECT_ID = "SELECT NEXTVAL('phonetype_seq') AS id";
            // Запрос вставки нового кортежа
            final String SQL_INSERT = "INSERT INTO dict_phonetype(id, code, name, fullname) VALUES (?, ?, ?, ?)";
            
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
            preparedStatement.setLong(2, id);
            preparedStatement.setString(3, object.getShortName());
            preparedStatement.setString(4, object.getFullName());
            
            // Выполняем запрос
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
            // Возвращаем объект вставки
            object.setId(id);
            object.setCode(id);
            return object;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void update(PhoneType object) {
        try {
            // Запрос обновления данных кортежа
            final String SQL_UPDATE = "UPDATE dict_phonetype SET code = ?, name = ?, fullname = ? WHERE id = ?";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            
            // Устанавливаем значение в запрос
            preparedStatement.setLong(4, object.getId());
            preparedStatement.setLong(1, object.getCode());
            preparedStatement.setString(2, object.getShortName());
            preparedStatement.setString(3, object.getFullName());
            
            // Выполняем запрос
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public PhoneType delete(PhoneType object) {
        try {
            // Запрос на удаление кортежа
            final String SQL_DELETE = "DELETE FROM dict_phonetype WHERE id = ?";
            
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
    public PhoneType getById(long id) {
        try {
            // Запрос поиска кортежа по id
            final String SQL_SELECT_BY_ID = "SELECT * FROM dict_phonetype WHERE id = ?";
            
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
            PhoneType phoneType = null;
            if (resultSet.next()) {
                long code = resultSet.getLong("code");
                String shortName = resultSet.getString("name");
                String fullName = resultSet.getString("fullname");
                
                phoneType = new PhoneType(id, code, shortName, fullName);
            }

            preparedStatement.close();
            
            // Возвращаем результат
            return phoneType;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public List<PhoneType> getAll() {
        try {
            // Запрос поиска всех кортежей
            final String SQL_SELECT = "SELECT * FROM dict_phonetype";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            Statement statement = connection.createStatement();
            
            // Выполняем запрос
            ResultSet resultSet = statement.executeQuery(SQL_SELECT);
            
            // Чтение результатов поиска
            ArrayList<PhoneType> phoneTypes = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long code = resultSet.getLong("code");
                String shortName = resultSet.getString("name");
                String fullName = resultSet.getString("fullname");
                
                phoneTypes.add(new PhoneType(id, code, shortName, fullName));
            }

            statement.close();
            
            // Возвращаем результат
            return phoneTypes;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
    
}
