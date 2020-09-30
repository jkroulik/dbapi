package com.test.dbapi.service;

import lombok.Data;

@Data
public class Column {
    private String name;
    private String type;
    private boolean nullable;
    private String defaultValue;
}
