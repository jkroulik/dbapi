package com.test.dbapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ConnectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldListConnections() throws Exception {
        //should list one default item
        mockMvc.perform(get("/api/v1/connection")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].databaseName", is("testdb")));
    }

    @Test
    public void testDeleteAndInsert() throws Exception {
        //when item is deleted
        mockMvc.perform(delete("/api/v1/connection/1")).andExpect(status().isOk());

        //then the list is empty
        mockMvc.perform(get("/api/v1/connection"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        //and can't be deleted for the second time
        mockMvc.perform(delete("/api/v1/connection/1")).andExpect(status().isNotFound());

        //when item is inserted again
        mockMvc.perform(post("/api/v1/connection").contentType(MediaType.APPLICATION_JSON).content("{\"databaseName\":\"testdb\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(is(equalTo("2")))); // id of the new record is 2

        //then should list one item again
        mockMvc.perform(get("/api/v1/connection"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].databaseName", is("testdb")));
    }

    @Test
    public void testConnectionModification() throws Exception {
        //when put is sent
        mockMvc.perform(put("/api/v1/connection/1").contentType(MediaType.APPLICATION_JSON).content("{\"databaseName\":\"testdb2\"}"))
                .andExpect(status().isOk());

        //then the item is changed
        mockMvc.perform(get("/api/v1/connection"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].databaseName", is("testdb2")));
    }

}
