package com.rx.samples.using.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<T> {
    public T call(ResultSet resultSet) throws SQLException;
}
