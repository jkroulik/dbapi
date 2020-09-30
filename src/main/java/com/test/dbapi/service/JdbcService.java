package com.test.dbapi.service;

import com.test.dbapi.repository.ConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
@RequiredArgsConstructor
public class JdbcService {

    private final ConnectionRepository connectionRepository;

    public interface UseResultSetLambda<T> {
        T apply(ResultSet resultSet) throws SQLException;
    }

    public <T> T runQuery(long connectionId, String query, UseResultSetLambda<T> a) throws SQLException {
        try (
                Connection connection = getConnection(connectionId);
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query)
        ) {
            return a.apply(rs);
        }
    }

    private Connection getConnection(long id) {
        return connectionRepository.findById(id)
                .map(this::getConnection)
                .orElseThrow(() -> new IllegalArgumentException("Unknown id"));
    }

    private Connection getConnection(com.test.dbapi.model.Connection connection) {
        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection("jdbc:h2:mem:" + connection.getDatabaseName(), connection.getUsername(), connection.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
