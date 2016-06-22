package com.rx.samples.using.jdbc;

import rx.Observable;

import java.sql.SQLException;

public interface UserRepository {
    Observable<User> getUser() throws SQLException;
}
