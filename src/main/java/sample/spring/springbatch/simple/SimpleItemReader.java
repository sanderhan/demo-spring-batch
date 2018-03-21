package sample.spring.springbatch.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;


public class SimpleItemReader implements ItemReader,InitializingBean,ItemStream {

    Logger logger = LoggerFactory.getLogger(SimpleItemReader.class);

    private FlatFileItemReader<NACHARecord> itemReader;

    public SimpleItemReader(FlatFileItemReader<NACHARecord> itemReader){
        this.itemReader = itemReader;
    }

    public void setDelegtor(FlatFileItemReader reader){
        this.itemReader = reader;
    } 
    
    @Override
    public NACHABatch read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        
        NACHABatch  batch = null;

        for (NACHARecord line = null; (line = this.itemReader.read()) != null;) {
            logger.info("FlatFileItem: {}", line);
            switch(line.recordType){
                case 5:
                    batch = new NACHABatch();
                    batch.addRecord(line);
                    break;
                case 6:
                    batch.addRecord(line);
                    break;
                case 7:
                    batch.addRecord(line);
                    break;
                case 8:
                    batch.addRecord(line);
                    return batch;

            }
        }
       return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        itemReader.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        itemReader.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        itemReader.close();
    }
}
