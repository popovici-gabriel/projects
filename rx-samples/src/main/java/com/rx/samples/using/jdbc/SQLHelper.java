package com.rx.samples.using.jdbc;

import rx.Observable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class SQLHelper {

    public static <T> Observable<T> executeQuery(ConnectionSubscription connectionSubscription, String sqlString, RowMapper<T> rowMapper) throws SQLException {
        List<T> entries = new LinkedList<>();
        Statement s = connectionSubscription.getConnection().createStatement();
        connectionSubscription.registerReourceForClose(s);
        ResultSet resultSet = s.executeQuery(sqlString);
        connectionSubscription.registerReourceForClose(resultSet);
        while (resultSet.next()) {
            entries.add(rowMapper.call(resultSet));
        }
        return Observable.from(entries);
    }
}
