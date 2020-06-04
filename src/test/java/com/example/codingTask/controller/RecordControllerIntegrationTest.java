package com.example.codingTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.codingTask.CodingTaskApplication;
import com.example.codingTask.entity.LineRecord;
import com.example.codingTask.repository.LineRecordRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author BUmerle
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = CodingTaskApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class RecordControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private LineRecordRepository repository;

    @AfterEach
    public void resetDatabase() {
        repository.deleteAll();
    }

    @Test
    @WithMockUser(value = "user1")
    public void getShouldReturnJsonWhenRecordExists() throws Exception {
        //Given
        LineRecord lineRecord = LineRecord.builder()
                .primaryKey("TestKey")
                .description("TestDescription")
                .name("TestName")
                .updatedTimestamp("SomeFakeTimestamp")
                .build();
        repository.save(lineRecord);

        //When & Then
         mvc.perform(get("/record/" + lineRecord.getPrimaryKey()))
                 .andDo(print())
                 .andExpect(status().isOk())
                 .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                 .andExpect(jsonPath("$.primaryKey", is(lineRecord.getPrimaryKey())));
    }

    @Test
    @WithMockUser(username="admin1",roles={"USER","ADMIN"})
    public void getShouldReturn404WhenRecordDoesNotExist() throws Exception {
        //Given: Empty database

        //When & Then
        mvc.perform(get("/record/NonExistingKey"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getShouldReturnUnauthorizedWhenNoAuthGiven() throws Exception {
        //Given: Empty database

        //When & Then
        mvc.perform(get("/record/NonExistingKey"))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username="admin1",roles={"USER","ADMIN"})
    public void deleteShouldReturnSuccessWhenAccessedWithAdminRole() throws Exception {
        //Given
        LineRecord lineRecord = LineRecord.builder()
                .primaryKey("TestKey")
                .description("TestDescription")
                .name("TestName")
                .updatedTimestamp("SomeFakeTimestamp")
                .build();
        repository.save(lineRecord);

        //When & Then
        mvc.perform(delete("/record/delete/" + lineRecord.getPrimaryKey()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteShouldReturn404WhenAccessedWhenNoAuthGiven() throws Exception {
        //Given: Empty DB

        //When & Then
        mvc.perform(delete("/record/delete/NonExistingKey"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username="admin1",roles={"USER","ADMIN"})
    public void deleteShouldReturnUnauthorizedWhenAccessedWithAdminRoleForNotExistingEntry() throws Exception {
        //Given: Empty DB

        //When & Then
        mvc.perform(delete("/record/delete/NonExistingKey"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(value = "user1")
    public void deleteShouldReturnForbiddenWhenAccessedWithUserRole() throws Exception {
        //Given
        LineRecord lineRecord = LineRecord.builder()
                .primaryKey("TestKey")
                .description("TestDescription")
                .name("TestName")
                .updatedTimestamp("SomeFakeTimestamp")
                .build();
        repository.save(lineRecord);

        //When & Then
        mvc.perform(delete("/record/delete/" + lineRecord.getPrimaryKey()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
