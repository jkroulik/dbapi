package com.test.dbapi.controller;

import com.test.dbapi.service.Column;
import com.test.dbapi.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DbController {

    private final DbService dbService;

    @GetMapping("/connection/{id}/schema")
    public List<String> schemas(@PathVariable Long id) throws Exception {
        return dbService.schemas(id);
    }

    @GetMapping("/connection/{id}/schema/{schema}/table")
    public List<String> tables(@PathVariable Long id, @PathVariable String schema) throws Exception {
        return dbService.tables(id, schema);
    }

    @GetMapping("/connection/{id}/schema/{schema}/table/{table}/column")
    public List<Column> columns(@PathVariable Long id, @PathVariable String schema, @PathVariable String table) throws Exception {
        return dbService.columns(id, schema, table);
    }

    @GetMapping("/connection/{id}/schema/{schema}/table/{table}/data")
    public List<Map<String, Object>> data(@PathVariable Long id, @PathVariable String schema, @PathVariable String table) throws Exception {
        return dbService.data(id, schema, table);
    }
}
