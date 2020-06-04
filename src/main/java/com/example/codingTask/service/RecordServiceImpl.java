package com.example.codingTask.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.codingTask.entity.LineRecord;
import com.example.codingTask.exceptions.RecordNotFoundException;
import com.example.codingTask.repository.LineRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author BUmerle
 */

@Component
public class RecordServiceImpl implements RecordService {

    @Autowired
    private LineRecordRepository repository;

    Logger logger = LoggerFactory.getLogger(RecordServiceImpl.class);

    @Override
    public LineRecord obtainRecord(String id) {
        logger.debug("Obtain record: " + id);
        Optional<LineRecord> lineRecord =  repository.findById(id);
        return lineRecord.orElseThrow(() -> new RecordNotFoundException(
                "Record not found, id: " + id));
    }

    @Override
    public void deleteRecord(String id) {
        logger.debug("Delete record: " + id);
        LineRecord lineRecord =  repository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Record not found, id: " + id));
        repository.delete(lineRecord);
    }
}
