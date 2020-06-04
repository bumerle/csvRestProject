package com.example.codingTask.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.codingTask.entity.LineRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author BUmerle
 */

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class LineRecordRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LineRecordRepository lineRecordRepository;

    @Test
    public void findByIdShouldReturnLineRecord() {
        //Given
        LineRecord lineRecord = LineRecord.builder()
                .primaryKey("TestKey")
                .description("TestDescription")
                .name("TestName")
                .updatedTimestamp("SomeFakeTimestamp")
                .build();

        entityManager.persistAndFlush(lineRecord);

        //When
        LineRecord obtained = lineRecordRepository.findById(lineRecord.getPrimaryKey()).get();

        //Then
        assertThat(obtained.equals(lineRecord));
    }

    @Test
    public void findByNotExistingIdShouldReturnNull() {
        //Given: Nothing stored in DB

        //When
        LineRecord obtained = lineRecordRepository.findById("TestKey").orElse(null);

        //Then
        assertThat(obtained).isNull();
    }

    @Test
    public void saveAllShouldStoreAllLineRecords() {
        //Given
        List<LineRecord> list = new ArrayList<LineRecord>() {{
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

        //When
        lineRecordRepository.saveAll(list);

        //Then
        LineRecord obtained = entityManager.find(LineRecord.class, "TestKey");
        LineRecord obtained2 = entityManager.find(LineRecord.class, "TestKey2");

        assertAll("Verify all Entities stored",
                () -> assertEquals(obtained, list.get(0)),
                () -> assertEquals(obtained2, list.get(1))
        );
    }

    @Test
    public void deleteShouldRemoveLineRecord() {
        //Given
        List<LineRecord> list = new ArrayList<LineRecord>() {{
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
        entityManager.persist(list.get(0));
        entityManager.persist(list.get(1));
        entityManager.flush();

        //When
        lineRecordRepository.delete(list.get(1));

        //Then
        LineRecord obtained = entityManager.find(LineRecord.class, "TestKey");
        LineRecord obtained2 = entityManager.find(LineRecord.class, "TestKey2");

        assertAll("Verify one of Entities deleted",
                () -> assertEquals(obtained, list.get(0)),
                () -> assertNull(obtained2)
        );
    }

    @Test
    public void deleteNotExistingShouldNotThrow() {
        //Given
        LineRecord lineRecord = LineRecord.builder()
                .primaryKey("TestKey")
                .description("TestDescription")
                .name("TestName")
                .updatedTimestamp("SomeFakeTimestamp")
                .build();

        //When and Then
        assertDoesNotThrow(() -> lineRecordRepository.delete(lineRecord));
    }

}
