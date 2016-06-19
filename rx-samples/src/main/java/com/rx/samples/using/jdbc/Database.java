package com.rx.samples.using.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection createConnection() {
        try {
            return DriverManager.getConnection("jdbc:derby:gabe:rx");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConnectionSubscription createSubscription() {
        return new ConnectionSubscription(createConnection());
    }
}
