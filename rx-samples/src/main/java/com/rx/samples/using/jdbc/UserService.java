package com.rx.samples.using.jdbc;

import rx.Observable;

import java.sql.SQLException;

import static com.rx.samples.using.jdbc.SQLHelper.executeQuery;

public class UserService {

//    public Observable<User> getUser() throws SQLException {
//        return Observable.using(Database::createSubscription, (subscription) -> {
//            try {
//                return executeQuery(subscription, "SELECT ID, USERNAME FROM USER", (resultSet) -> {
//                    return new User(resultSet.getLong("ID"), resultSet.getString("USERNAME"));
//                });
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
}
