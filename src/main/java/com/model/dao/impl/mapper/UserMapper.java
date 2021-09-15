package com.model.dao.impl.mapper;

import com.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements ObjectMapper<User> {
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("users.id"));
        user.setFirstName(rs.getString("users.firstName"));
        user.setLastName(rs.getString("users.lastName"));
        user.setPassword(rs.getString("users.password"));
        user.setUsername(rs.getString("users.username"));
        return user;
    }

    @Override
    public User makeUnique(Map<Long, User> cache, User user) {
        cache.putIfAbsent(user.getId(), user);
        return cache.get(user.getId());
    }
}
