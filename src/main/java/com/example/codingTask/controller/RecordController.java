package com.example.codingTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.codingTask.entity.LineRecord;
import com.example.codingTask.service.RecordService;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author BUmerle
 */

@RestController
@RequestMapping(path = RecordController.ROOT_PATH)
public class RecordController {

    public static final String ROOT_PATH = "/record";

    @Autowired
    private RecordService recordService;

    Logger logger = LoggerFactory.getLogger(RecordController.class);

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public LineRecord getLineRecord(
            @ApiParam(value = "ID of product to return", name = "id", required = true)
            @PathVariable @NotNull final String id) {
        logger.debug("Get record: " + id);
        return recordService.obtainRecord(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public void deleteLineRecord(
            @ApiParam(value = "ID of product to delete", name = "id", required = true)
            @PathVariable @NotNull final String id) {

        logger.debug("Delete: " + id);
        recordService.deleteRecord(id);
    }

}
