package com.test.dbapi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Connection {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String databaseName;

    @Column
    private String username;

    @Column
    private String password;

}
