package com.test.dbapi.controller;

import com.test.dbapi.service.Column;
import com.test.dbapi.service.DbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DbController.class)
public class DbControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbServiceMock;

    @Test
    public void shouldListSchemas() throws Exception {
        when(dbServiceMock.schemas(1)).thenReturn(List.of("a schema"));

        mockMvc.perform(get("/api/v1/connection/1/schema"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("a schema")));

        verify(dbServiceMock, times(1)).schemas(eq(1L));
    }

    @Test
    public void shouldListTables() throws Exception {
        when(dbServiceMock.tables(1, "PUBLIC")).thenReturn(List.of("a table"));

        mockMvc.perform(get("/api/v1/connection/1/schema/PUBLIC/table"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("a table")));

        verify(dbServiceMock, times(1)).tables(eq(1L), eq("PUBLIC"));
    }

    @Test
    public void shouldListColumns() throws Exception {
        final Column column = new Column();
        column.setName("column_name");
        List<Column> columns = List.of(column);
        when(dbServiceMock.columns(1, "PUBLIC", "CONNECTION")).thenReturn(columns);

        mockMvc.perform(get("/api/v1/connection/1/schema/PUBLIC/table/CONNECTION/column")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("column_name")));

        verify(dbServiceMock, times(1)).columns(eq(1L), eq("PUBLIC"), eq("CONNECTION"));
    }

    @Test
    public void shouldRetrieveTableData() throws Exception {
        List<Map<String, Object>> data = List.of(Map.of("name", "value"));
        when(dbServiceMock.data(1, "PUBLIC", "CONNECTION")).thenReturn(data);

        mockMvc.perform(get("/api/v1/connection/1/schema/PUBLIC/table/CONNECTION/data")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("value")));

        verify(dbServiceMock, times(1)).data(eq(1L), eq("PUBLIC"), eq("CONNECTION"));
    }

}
