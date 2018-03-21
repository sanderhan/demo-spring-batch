package sample.spring.springbatch.simple;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NACHABatch {
    List<NACHARecord> entries = new ArrayList<NACHARecord>();

    public void addRecord(NACHARecord record){
        entries.add(record);
    }
}
