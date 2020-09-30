package com.test.dbapi.controller;

import com.test.dbapi.service.Column;
import com.test.dbapi.service.DbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DbServiceTest {

    @Autowired
    DbService dbService;

    @Test
    public void shouldListSchemas() throws Exception {
        List<String> schemas = dbService.schemas(1);
        assertEquals(2, schemas.size());
    }

    @Test
    public void shouldListTables() throws Exception {
        List<String> tables = dbService.tables(1, "PUBLIC");
        assertEquals(1, tables.size());
    }

    @Test
    public void shouldListColumns() throws Exception {
        List<Column> columns = dbService.columns(1, "PUBLIC", "CONNECTION");
        assertEquals(5, columns.size());
    }

    @Test
    public void shouldPreviewData() throws Exception {
        List<Map<String, Object>> data = dbService.data(1, "PUBLIC", "CONNECTION");
        assertEquals(1, data.size());
        assertEquals("internal", data.get(0).get("NAME"));
    }

}
