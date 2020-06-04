package com.example.codingTask.service.processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.example.codingTask.entity.LineRecord;
import com.example.codingTask.exceptions.FileCorruptedException;
import com.example.codingTask.service.procesor.FileProcessor;
import com.example.codingTask.service.procesor.FileProcessorImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author BUmerle
 */


public class FileProcessorTest {



    @Test
    public void processFileShouldReturnResultsFromCorrectFile() throws IOException {
        //Given
        FileProcessor fileProcessor = new FileProcessorImpl();

        Path path = Paths.get("src/test/resources/test.csv");
        byte[] content = Files.readAllBytes(path);
        MultipartFile result = new MockMultipartFile("fileName",
                "originalFileName", "text/plain", content);

        //When
        List<LineRecord> lineRecordList = fileProcessor.processFile(result);

        //Then
        assertAll("Check obtained item count and check content of first item",
                () -> assertEquals(8, lineRecordList.size()),
                () -> assertEquals("1", lineRecordList.get(0).getPrimaryKey()),
                () -> assertEquals("John Doe", lineRecordList.get(0).getName()),
                () -> assertEquals("Blond", lineRecordList.get(0).getDescription()),
                () -> assertEquals("some_TS_1", lineRecordList.get(0).getUpdatedTimestamp())
        );
    }

    @Test
    public void processFileShouldThrowOnCorruptedFile() throws IOException {
        //Given
        FileProcessor fileProcessor = new FileProcessorImpl();

        Path path = Paths.get("src/test/resources/test_corrupted.csv");
        byte[] content = Files.readAllBytes(path);
        MultipartFile result = new MockMultipartFile("fileName",
                "originalFileName", "text/plain", content);

        //When &Then
        assertThrows(FileCorruptedException.class, () -> fileProcessor.processFile(result));
    }

    @Test
    public void processFileShouldThrowOnWrongFile() throws IOException {
        //Given
        FileProcessor fileProcessor = new FileProcessorImpl();

        Path path = Paths.get("src/test/resources/test_wrong.csv");
        byte[] content = Files.readAllBytes(path);
        MultipartFile result = new MockMultipartFile("fileName",
            "originalFileName", "text/plain", content);

        //When &Then
        assertThrows(FileCorruptedException.class, () -> fileProcessor.processFile(result));
    }

}
