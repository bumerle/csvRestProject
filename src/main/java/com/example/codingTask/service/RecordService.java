package com.example.codingTask.service;


import com.example.codingTask.entity.LineRecord;

/**
 * @author BUmerle
 */


public interface RecordService {

    LineRecord obtainRecord(String id);

    void deleteRecord(String id);


}
