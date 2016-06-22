package com.rx.samples.using.jdbc;

import rx.Observable;

import java.sql.SQLException;

import static com.rx.samples.using.jdbc.SQLHelper.executeQuery;

public class UserRepositoryImpl implements UserRepository {

    public Observable<User> getUser() throws SQLException {
        return Observable.using(
                /* a function that creates a disposable resource */
                Database::createSubscription,
                /* a function that creates an observable */
                (subscription) -> {
                    try {
                        return executeQuery(subscription, "SELECT ID, USERNAME FROM USER", (resultSet) -> {
                            return new User(resultSet.getLong("ID"), resultSet.getString("USERNAME"));
                        });
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                },
                /* a function that disposes the resource */
                (connection) -> {
                    connection.close();
                }
        );
    }
}
