package com.test.dbapi.service;

import com.test.dbapi.model.Connection;
import com.test.dbapi.repository.ConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final ConnectionRepository repository;

    @Transactional
    public Connection save(Connection connection) {
        return repository.save(connection);
    }

    @Transactional
    public void delete(Connection connection) {
        repository.delete(connection);
    }

    @Transactional
    public void saveDefaultConnection() {
        Connection connection = new Connection();
        connection.setName("internal");
        connection.setDatabaseName("testdb");
        connection.setUsername("user");
        connection.setPassword("pwd");
        repository.save(connection);
    }

}
