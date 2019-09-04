package com.yangyh.generator.jdbc;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @description:
 * @author: yangyh
 * @create: 2019-05-15 17:19
 */
public class ConnectionManager {

    static Connection conn = null;

    public static Connection getConnection(String dbName) {
        if (conn != null) {
            return conn;
        }
        Configuration jdbcProperties = null;
        try {
            jdbcProperties = new PropertiesConfiguration("conf/jdbc.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        String driverName = jdbcProperties.getString(dbName + ".jdbc.driver");
        String url = jdbcProperties.getString(dbName + ".jdbc.url");
        String userName = jdbcProperties.getString(dbName + ".jdbc.username");
        String password = jdbcProperties.getString(dbName + ".jdbc.password");

        try {
            Class.forName(driverName).getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
