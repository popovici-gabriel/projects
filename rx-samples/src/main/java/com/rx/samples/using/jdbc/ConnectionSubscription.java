package com.rx.samples.using.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionSubscription {
    private final Connection connection;

    public ConnectionSubscription(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerReourceForClose(Statement s) {
        try {
            s.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerReourceForClose(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
