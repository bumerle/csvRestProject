package com.example.codingTask.service.procesor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.codingTask.entity.LineRecord;
import com.example.codingTask.exceptions.FileCorruptedException;
import com.example.codingTask.utils.ValidationUtils;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author BUmerle
 */

@Component
public class FileProcessorImpl implements FileProcessor {

    Logger logger = LoggerFactory.getLogger(FileProcessorImpl.class);

    @Override
    public List<LineRecord> processFile(MultipartFile file) {
        logger.debug("File processor processing");

        List<LineRecord> lineRecords;

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<LineRecord> csvToBean = new CsvToBeanBuilder<LineRecord>(reader)
                    .withType(LineRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            lineRecords = csvToBean.parse();
            lineRecords.forEach(ValidationUtils::validate);

        } catch (Exception e) {
            throw new FileCorruptedException("File corrupted or wrong content. " + e.getMessage());
        }
        return lineRecords;
    }
}
