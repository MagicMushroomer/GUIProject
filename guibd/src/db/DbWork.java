package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DbWork {
    
    private Connection connection;  // Состояние
    private String dbAddress;        // Адрес БД
    private int dbPort;             // Порт БД
    private String dbName;          // Имя БД
    private String userName;        // Имя пользователя БД
    private String userPassword;    // Пароль пользователя БД
    private static DbWork db;       // Статичный объект БД
    
    // Установить соединения
    private void connect() {
        try {
            readProperties();
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dad", userName, userPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Получить экзмемпляр объект БД
    public static DbWork getInstance() {
        if (db == null) {
            db = new DbWork();
        }
        return db;
    }
    
    // Получить состояние
    public Connection getConnection() {
        if (connection == null) {
            connect();
        }
        return connection;
    }
    
    // Прочитать свойства из файла и записать в поля
    private void readProperties() {
        Properties properties;
        try {
            File file = new File("data.properties");
            if (file.exists()) {
                properties = new Properties();
                properties.load(new FileInputStream(file));
                dbAddress = properties.getProperty("db_address");
                dbPort = Integer.parseInt(properties.getProperty("db_port"));
                dbName = properties.getProperty("db_name");
                userName = properties.getProperty("user_name");
                userPassword = properties.getProperty("user_password");
            }
        } catch (IOException | NumberFormatException e) {
            throw new Error(e);
        }
    }
    public Long generateId(String sequenceName){

        Long id = null;
        String sql = "select nextval( ? ) as id";

        try (PreparedStatement st = getConnection().prepareStatement(sql)){

            st.setString(1, sequenceName);

            ResultSet rs = st.executeQuery();

            if (rs.next()){
                id = rs.getLong("id");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
