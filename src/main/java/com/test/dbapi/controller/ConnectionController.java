package com.test.dbapi.controller;

import com.test.dbapi.model.Connection;
import com.test.dbapi.repository.ConnectionRepository;
import com.test.dbapi.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ConnectionController {

    private final ConnectionRepository repository;
    private final ConnectionService service;

    @GetMapping("/connection")
    public List<Connection> connections() {
        return repository.findAll();
    }

    @DeleteMapping("/connection/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        Optional<Connection> conn = repository.findById(id);
        conn.ifPresent(service::delete);
        return new ResponseEntity<>(id, conn.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/connection")
    public ResponseEntity<Long> insert(@RequestBody Connection connection) {
        if (connection.getId() != null)
            return new ResponseEntity<>(connection.getId(), HttpStatus.PRECONDITION_FAILED);
        Connection savedConn = service.save(connection);
        return new ResponseEntity<>(savedConn.getId(), HttpStatus.OK);
    }

    @PutMapping("/connection/{id}")
    public ResponseEntity<Long> modify(@PathVariable Long id, @RequestBody Connection connection) {
        Optional<Connection> conn = repository.findById(id);
        if (conn.isEmpty())
            return new ResponseEntity<>(connection.getId(), HttpStatus.PRECONDITION_FAILED);
        connection.setId(id);
        Connection savedConn = service.save(connection);
        return new ResponseEntity<>(savedConn.getId(), HttpStatus.OK);
    }

}
