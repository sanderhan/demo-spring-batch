package sample.spring.springbatch.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import java.util.List;

public class SimpleItemWriter implements ItemWriter<NACHABatch> {

    Logger logger = LoggerFactory.getLogger(SimpleItemWriter.class);
    private int lineNumber = 0;

    @Override
    public void write(List<? extends NACHABatch> list) throws Exception {
        for(NACHABatch item:list){
            lineNumber++;
            logger.info("ItemWriter Batch #{}: {}", lineNumber,item);
        }
    }

}
