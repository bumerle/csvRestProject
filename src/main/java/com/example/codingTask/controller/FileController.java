package com.example.codingTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.codingTask.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author BUmerle
 */

@RestController
@RequestMapping(path = FileController.ROOT_PATH)
public class FileController {

    public static final String ROOT_PATH = "/file";

    @Autowired
    private FileService fileService;

    Logger logger = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/uploadfile")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        logger.debug("Got file");
        fileService.processFile(file);
    }


}
