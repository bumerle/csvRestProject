package com.example.codingTask.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import com.example.codingTask.entity.LineRecord;
import com.example.codingTask.repository.LineRecordRepository;
import com.example.codingTask.service.procesor.FileProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author BUmerle
 */

@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class FileServiceTest {



    @TestConfiguration
    static class FileServiceImplConfiguration {
        @Bean
        public FileService fileService() {
            return new FileServiceImpl();
        }
    }

    @Autowired
    private FileService fileService;

    @MockBean
    private LineRecordRepository lineRecordRepository;

    @MockBean
    private FileProcessor fileProcessor;

    @Test
    public void processFileShouldProcessItAndSaveResults() {
        //Given
        LineRecord lineRecord = LineRecord.builder()
                .primaryKey("TestKey")
                .description("TestDescription")
                .name("TestName")
                .updatedTimestamp("SomeFakeTimestamp")
                .build();

        LineRecord lineRecord2 = LineRecord.builder()
                .primaryKey("TestKey2")
                .description("TestDescription2")
                .name("TestName2")
                .updatedTimestamp("SomeFakeTimestamp2")
                .build();

        List<LineRecord> lineRecords = Arrays.asList(lineRecord, lineRecord2);
        Mockito.when(fileProcessor.processFile(any())).thenReturn(lineRecords);

        MultipartFile file = new MockMultipartFile("MockName", (byte[]) null);

        //When
        fileService.processFile(file);

        //Then
        Mockito.verify(fileProcessor, times(1)).processFile(any());
        Mockito.reset(fileProcessor);
        Mockito.verify(lineRecordRepository, times(1)).saveAll(lineRecords);
        Mockito.reset(lineRecordRepository);

    }

    @Test
    public void processFileShouldProcessNotSaveNullProcessingResult() {
        //Given
        Mockito.when(fileProcessor.processFile(any())).thenReturn(null);

        MultipartFile file = new MockMultipartFile("MockName", (byte[]) null);

        //When
        fileService.processFile(file);

        //Then
        Mockito.verify(fileProcessor, times(1)).processFile(any());
        Mockito.reset(fileProcessor);
        Mockito.verify(lineRecordRepository, times(0)).saveAll(any());
        Mockito.reset(lineRecordRepository);
    }

    @Test
    public void processFileShouldProcessNotSaveEmptyProcessingResult() {
        //Given
        Mockito.when(fileProcessor.processFile(any())).thenReturn(new ArrayList<LineRecord>());

        MultipartFile file = new MockMultipartFile("MockName", (byte[]) null);

        //When
        fileService.processFile(file);

        //Then
        Mockito.verify(fileProcessor, times(1)).processFile(any());
        Mockito.reset(fileProcessor);
        Mockito.verify(lineRecordRepository, times(0)).saveAll(any());
        Mockito.reset(lineRecordRepository);
    }

}
