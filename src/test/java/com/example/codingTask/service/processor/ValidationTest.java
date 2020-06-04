package com.example.codingTask.service.processor;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import com.example.codingTask.entity.LineRecord;
import com.example.codingTask.utils.ValidationUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author BUmerle
 */


public class ValidationTest {

    @Test
    public void validateShouldProcessCorrectRecordsWithoutThrow() {
        //Given
        List<LineRecord> correctLineRecords = new ArrayList<LineRecord>() {{
            add(LineRecord.builder()
                .primaryKey("TestKey")
                .description("TestDescription")
                .name("TestName")
                .updatedTimestamp("SomeFakeTimestamp")
                .build());
            add(LineRecord.builder()
                .primaryKey("TestKey2")
                .description("TestDescription2")
                .name("TestName2")
                .updatedTimestamp("SomeFakeTimestamp2")
                .build());
        }};

        //When & Then
        assertDoesNotThrow(() ->correctLineRecords.forEach(ValidationUtils::validate), "Should pass validation");
    }

    @Test
    public void validateShouldProcessWrongRecordsAndThrow() {
        //Given
        List<LineRecord> wrongLineRecords = new ArrayList<LineRecord>() {{
            add(LineRecord.builder()
                .primaryKey("TestKey")
                .description(null)
                .name("TestName")
                .updatedTimestamp("SomeFakeTimestamp")
                .build());
            add(LineRecord.builder()
                .primaryKey("TestKey2")
                .description("TestDescription2")
                .name(null)
                .updatedTimestamp("SomeFakeTimestamp2")
                .build());
        }};

        //When & Then
        assertThrows(ValidationException.class,
            () ->wrongLineRecords.forEach(ValidationUtils::validate));
    }

}
