package com.example.codingTask.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author BUmerle
 */


public interface FileService {

    void processFile(MultipartFile file);


}
