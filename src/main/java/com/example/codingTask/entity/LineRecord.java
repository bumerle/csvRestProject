package com.example.codingTask.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;



import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author BUmerle
 */

@Entity
@Getter
@Setter
@ToString
public class LineRecord {

    @Id
    @NotNull
    @NotEmpty
    @CsvBindByName(column = "PRIMARY_KEY")
    private String primaryKey;

    @NotNull
    @CsvBindByName(column = "NAME")
    private String name;

    @NotNull
    @CsvBindByName(column = "DESCRIPTION")
    private String description;

    @NotNull
    @CsvBindByName(column = "UPDATED_TIMESTAMP")
    private String updatedTimestamp;

}
