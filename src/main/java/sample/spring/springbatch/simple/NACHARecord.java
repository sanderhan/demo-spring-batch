package sample.spring.springbatch.simple;

import lombok.Data;

import javax.annotation.sql.DataSourceDefinition;

@Data
public class NACHARecord {

    int recordType;
    String line;

}
