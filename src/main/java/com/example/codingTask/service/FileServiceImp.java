package com.example.codingTask.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.codingTask.entity.LineRecord;
import com.example.codingTask.repository.LineRecordRepository;
import com.example.codingTask.service.procesor.FileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author BUmerle
 */

@Component
public class FileServiceImp implements FileService {

    @Autowired
    private FileProcessor fileProcessor;

    @Autowired
    private LineRecordRepository repository;

    Logger logger = LoggerFactory.getLogger(FileServiceImp.class);

    @Override
    public void processFile(MultipartFile file) {
        logger.debug("File service processing");

        List<LineRecord> records = fileProcessor.processFile(file);
        logger.debug("File service saving");
        repository.saveAll(records);
    }
}
