package com.example.codingTask.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;



import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author BUmerle
 */

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
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
