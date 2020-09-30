package com.test.dbapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DbService {

    private final JdbcService jdbcService;

    public List<String> schemas(long connectionId) throws Exception {
        return jdbcService.runQuery(
                connectionId,
                "SHOW SCHEMAS",
                rs -> {
                    List<String> schemas = new LinkedList<>();
                    while (rs.next()) {
                        schemas.add(rs.getString("SCHEMA_NAME"));
                    }
                    return schemas;
                }
        );
    }

    public List<String> tables(long connectionId, String schema) throws Exception {
        return jdbcService.runQuery(
                connectionId,
                "SHOW TABLES FROM " + schema, //TODO check SQL injection
                rs -> {
                    List<String> tableNames = new LinkedList<>();
                    while (rs.next()) {
                        tableNames.add(rs.getString("TABLE_NAME"));
                    }
                    return tableNames;
                }
        );
    }

    public List<Column> columns(long connectionId, String schema, String table) throws Exception {
        return jdbcService.runQuery(
                connectionId,
                "SHOW COLUMNS FROM " + table + " FROM " + schema, //TODO check SQL injection
                rs -> {
                    List<Column> columns = new LinkedList<>();
                    while (rs.next()) {
                        Column column = new Column();
                        column.setName(rs.getString("FIELD"));
                        column.setType(rs.getString("TYPE"));
                        column.setNullable(rs.getBoolean("NULL"));
                        column.setDefaultValue(rs.getObject("DEFAULT") + "");
                        columns.add(column);
                    }
                    return columns;
                }
        );
    }

    public List<Map<String, Object>> data(long connectionId, String schema, String table) throws SQLException {
        return jdbcService.runQuery(
                connectionId,
                "SELECT * FROM " + schema + "." + table + " limit 100", //TODO check SQL injection
                rs -> {
                    List<String> columns = new LinkedList<>();
                    ResultSetMetaData metaData = rs.getMetaData();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        columns.add(metaData.getColumnName(i));
                    }
                    List<Map<String, Object>> data = new LinkedList<>();
                    while (rs.next()) {
                        Map<String, Object> row = new HashMap<>();
                        for (String column : columns) {
                            row.put(column, rs.getObject(column));
                        }
                        data.add(row);
                    }
                    return data;
                }
        );
    }
}
