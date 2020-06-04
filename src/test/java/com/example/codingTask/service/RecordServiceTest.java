package com.example.codingTask.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.codingTask.entity.LineRecord;
import com.example.codingTask.exceptions.RecordNotFoundException;
import com.example.codingTask.repository.LineRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author BUmerle
 */

@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class RecordServiceTest {



    @TestConfiguration
    static class RecordServiceImplConfiguration {
        @Bean
        public RecordService recordService() {
            return new RecordServiceImpl();
        }
    }

    @Autowired
    private RecordService recordService;

    @MockBean
    private LineRecordRepository lineRecordRepository;

    @Test
    public void obtainRecordShouldReturnLineRecord() {
        //Given
        LineRecord lineRecord = LineRecord.builder()
                .primaryKey("TestKey")
                .description("TestDescription")
                .name("TestName")
                .updatedTimestamp("SomeFakeTimestamp")
                .build();
        Mockito.when(lineRecordRepository.findById(lineRecord.getPrimaryKey()))
                .thenReturn(Optional.of(lineRecord));

        //When
        LineRecord obtained = recordService.obtainRecord(lineRecord.getPrimaryKey());

        //Then
        assertThat(obtained.equals(lineRecord));
        Mockito.verify(lineRecordRepository, times(1)).findById(lineRecord.getPrimaryKey());
        Mockito.reset(lineRecordRepository);

    }

    @Test
    public void obtainRecordShouldThrowRecordNotFound() {
        //Given
        String notExisting = "Not_Existing";
        Mockito.when(lineRecordRepository.findById(notExisting))
                .thenReturn(Optional.empty());

        //When & Then
        assertThrows(RecordNotFoundException.class, () -> recordService.obtainRecord(notExisting));

        Mockito.verify(lineRecordRepository, times(1)).findById(notExisting);
        Mockito.reset(lineRecordRepository);

    }

    @Test
    public void deleteRecordShouldCallDeleteOnExistingRecord() {
        //Given
        LineRecord lineRecord = LineRecord.builder()
                .primaryKey("TestKey")
                .description("TestDescription")
                .name("TestName")
                .updatedTimestamp("SomeFakeTimestamp")
                .build();
        Mockito.when(lineRecordRepository.findById(lineRecord.getPrimaryKey()))
                .thenReturn(Optional.of(lineRecord));

        //When
        recordService.deleteRecord(lineRecord.getPrimaryKey());

        //Then
        Mockito.verify(lineRecordRepository, times(1)).findById(lineRecord.getPrimaryKey());
        Mockito.verify(lineRecordRepository, times(1)).delete(lineRecord);
        Mockito.reset(lineRecordRepository);

    }

    @Test
    public void deleteRecordShouldThrowRecordNotFound() {
        //Given
        String notExisting = "Not_Existing";
        Mockito.when(lineRecordRepository.findById(notExisting))
                .thenReturn(Optional.empty());

        //When & Then
        assertThrows(RecordNotFoundException.class, () -> recordService.deleteRecord(notExisting));

        Mockito.verify(lineRecordRepository, times(1)).findById(notExisting);
        Mockito.reset(lineRecordRepository);
    }
}
