package com.example.codingTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.codingTask.CodingTaskApplication;
import com.example.codingTask.repository.LineRecordRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author BUmerle
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = CodingTaskApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class FileControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private LineRecordRepository repository;

    @AfterEach
    public void resetDatabase() {
        repository.deleteAll();
    }

    @Test
    @WithMockUser(username="admin1",roles={"USER","ADMIN"})
    public void uploadFileShouldReturnStatusOkWhenAccessedWithAdminRole() throws Exception {
        //When & Then
         mvc.perform(multipart("/file/uploadfile").file(new MockMultipartFile("file", (byte[]) null)))
                 .andDo(print())
                 .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="user1")
    public void uploadFileShouldReturnStatusOkWhenAccessedWithUserRole() throws Exception {
        //When & Then
        mvc.perform(multipart("/file/uploadfile").file(new MockMultipartFile("file", (byte[]) null)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void uploadFileShouldReturnUnauthorizedWhenNoAuthGiven() throws Exception {
        //When & Then
        mvc.perform(multipart("/file/uploadfile").file(new MockMultipartFile("file", (byte[]) null)))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }


}
