package com.example.codingTask.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.codingTask.entity.LineRecord;

/**
 * @author BUmerle
 */

@Repository
public interface LineRecordRepository extends CrudRepository<LineRecord, String> {

}
