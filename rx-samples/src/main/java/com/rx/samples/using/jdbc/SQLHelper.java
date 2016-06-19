package com.rx.samples.using.jdbc;

import rx.Observable;

import java.sql.SQLException;
import java.util.ArrayList;

public class SQLHelper {

    public static <T> Observable<T> executeQuery(ConnectionSubscription connectionSubscription, String sqlString, RowMapper<T> rowMapper) throws SQLException {
        return Observable.from(new ArrayList());
    }
}
