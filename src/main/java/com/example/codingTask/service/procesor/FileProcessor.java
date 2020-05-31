package com.example.codingTask.service.procesor;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.codingTask.entity.LineRecord;

/**
 * @author BUmerle
 */
public interface FileProcessor {


    List<LineRecord> processFile(MultipartFile file);




}
