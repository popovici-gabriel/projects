package com.rx.samples.using.jdbc;

import java.sql.Connection;

public class ConnectionSubscription {
    private final Connection connection;

    public ConnectionSubscription(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
