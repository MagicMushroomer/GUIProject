/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.DbWork;
import domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mushroomer
 */
public class PhoneDAOImpl implements GeneticDAO<Phone> {

    @Override
    public Phone create(Phone phone) {
        try {
            // Запрос следующего свободного id в последовательности
            final String SQL_SELECT_ID = "SELECT NEXTVAL('phone_seq') AS id";
            // Запрос вставки нового кортежа
            final String SQL_INSERT = "INSERT INTO phone(id, contact_id, phonetype_id, phonenumber) VALUES (?, ?, ?, ?)";
            // Запрос на поиск кортежа с таким же id
            final String SQL_SELECT_BY_ID = "SELECT id FROM contact WHERE id = ?";
            
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
            
            // Проверяем наличие такого же контакта в таблице
            PreparedStatement preparedStatement1 = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement1.setLong(1, phone.getContact().getId());
            if (!preparedStatement1.executeQuery().next()) {
                GeneticDAO contactDAO = new ContactDAOImpl();
                contactDAO.create(phone.getContact());
            }
            preparedStatement1.close();
            
            // Устанавливаем значения в запрос
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, phone.getContact().getId());
            preparedStatement.setLong(3, phone.getPhoneType().getId());
            preparedStatement.setString(4, phone.getPhoneNumber());
            
            // Выполняем запрос
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
            // Возвращаем объект вставки
            phone.setId(id);
            return phone;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void update(Phone phone) {
        try {
            // Запрос обновления данных кортежа
            final String SQL_UPDATE = "UPDATE phone SET contact_id = ?, phonetype_id = ?, phonenumber = ? WHERE id = ?";
            // Запрос на поиск кортежа с таким же id
            final String SQL_SELECT_BY_ID = "SELECT id FROM phone WHERE id = ?";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            
            // Проверяем наличие такого же контакта в таблице
            PreparedStatement preparedStatement1 = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement1.setLong(1, phone.getId());
            // Если такого кортежа нет, то создаем его
            if (!preparedStatement1.executeQuery().next()) {
                GeneticDAO phoneDAO = new PhoneDAOImpl();
                phoneDAO.create(phone);
            // Если такой кортеж есть, то обновляем его
            } else {
                // Устанавливаем значение в запрос
                preparedStatement.setLong(4, phone.getId());
                preparedStatement.setLong(1, phone.getContact().getId());
                preparedStatement.setLong(2, phone.getPhoneType().getId());
                preparedStatement.setString(3, phone.getPhoneNumber());
            
                // Выполняем запрос
                preparedStatement.executeUpdate();
            }
            preparedStatement1.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public Phone delete(Phone object) {
        try {
            // Запрос на удаление кортежа
            final String SQL_DELETE = "DELETE FROM phone WHERE id = ?";
            
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
    public Phone getById(long id) {
        try {
            // Запрос поиска кортежа по id
            final String SQL_SELECT_BY_ID = "SELECT contact.id AS contactid, contact.fullname AS contactfullname, contact.lastname AS contactlastname, contact.firstname AS contactfirstname, contact.inblacklist AS contactinblacklist, phone.id AS phoneid, dict_phonetype.id AS dict_phonetypeid, dict_phonetype.code AS dict_phonetypecode, dict_phonetype.name AS dict_phonetypename, dict_phonetype.fullname AS dict_phonetypefullname, phone.phonenumber AS phonephonenumber FROM contact INNER JOIN phone ON contact.id = contact_id INNER JOIN dict_phonetype ON phone.phonetype_id = dict_phonetype.id WHERE phone.id = ?";
            
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
            Phone phone = null;
            if (resultSet.next()) {
                long contactId = resultSet.getLong("contactid");
                String contactFullName = resultSet.getString("contactfullname");
                String contactLastName = resultSet.getString("contactlastname");
                String contactFirstName = resultSet.getString("contactfirstname");
                Boolean inBlackList = resultSet.getBoolean("contactinblacklist");
                long phoneId = resultSet.getLong("phoneid");
                long phoneTypeId = resultSet.getLong("dict_phonetypeid");
                long phoneTypeCode = resultSet.getLong("dict_phonetypecode");
                String phoneTypeShortName = resultSet.getString("dict_phoneTypename");
                String phoneTypeFullName = resultSet.getString("dict_phoneTypefullname");
                String phoneNumber = resultSet.getString("phonephonenumber");
                
                Contact contact = new Contact(contactId, contactFullName, contactLastName, contactFirstName, inBlackList);
                PhoneType phoneType = new PhoneType(phoneTypeId, phoneTypeCode, phoneTypeShortName, phoneTypeFullName);
                phone = new Phone(phoneId, contact, phoneType, phoneNumber);
            }
            resultSet.close();
            preparedStatement.close();
            // Возвращаем результат
            return phone;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public List<Phone> getAll() {
        try {
            // Запрос поиска всех кортежей
            final String SQL_SELECT = "SELECT contact.id AS contactid, contact.fullname AS contactfullname, contact.lastname AS contactlastname, contact.firstname AS contactfirstname, contact.inblacklist AS contactinblacklist, phone.id AS phoneid, dict_phonetype.id AS dict_phonetypeid, dict_phonetype.code AS dict_phonetypecode, dict_phonetype.name AS dict_phonetypename, dict_phonetype.fullname AS dict_phonetypefullname, phone.phonenumber AS phonephonenumber FROM contact INNER JOIN phone ON contact.id = contact_id INNER JOIN dict_phonetype ON phone.phonetype_id = dict_phonetype.id";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            Statement statement = connection.createStatement();
            
            // Выполняем запрос
            ResultSet resultSet = statement.executeQuery(SQL_SELECT);
            
            // Чтение результатов поиска
            ArrayList<Phone> phones = new ArrayList<>();
            while (resultSet.next()) {
                long contactId = resultSet.getLong("contactid");
                String contactFullName = resultSet.getString("contactfullname");
                String contactLastName = resultSet.getString("contactlastname");
                String contactFirstName = resultSet.getString("contactfirstname");
                Boolean inBlackList = resultSet.getBoolean("contactinblacklist");
                long phoneId = resultSet.getLong("phoneid");
                long phoneTypeId = resultSet.getLong("dict_phonetypeid");
                long phoneTypeCode = resultSet.getLong("dict_phonetypecode");
                String phoneTypeShortName = resultSet.getString("dict_phoneTypename");
                String phoneTypeFullName = resultSet.getString("dict_phoneTypefullname");
                String phoneNumber = resultSet.getString("phonephonenumber");
                
                Contact contact = new Contact(contactId, contactFullName, contactLastName, contactFirstName, inBlackList);
                PhoneType phoneType = new PhoneType(phoneTypeId, phoneTypeCode, phoneTypeShortName, phoneTypeFullName);
                Phone phone = new Phone(phoneId, contact, phoneType, phoneNumber);
                
                phones.add(phone);
            }

            statement.close();
            
            // Возвращаем результат
            return phones;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
    
}
