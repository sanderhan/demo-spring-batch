package sample.spring.springbatch.simple;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

@Configuration
@EnableBatchProcessing
public class SimpleJobConfigration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() {
        return this.jobBuilderFactory.get("simpleJob")
                .start(step1())
                .build();
    }

    private LineMapper nachaLineMapper(){
        DefaultLineMapper lineMapper = new DefaultLineMapper();
        lineMapper.setFieldSetMapper(nachaFieldSetMapper());
        lineMapper.setLineTokenizer(lineTokenizer());
        return lineMapper;
    }

    @Bean
    public FlatFileItemReader<NACHARecord> fileItemReader(){
        FlatFileItemReader<NACHARecord> fileItemReader = new FlatFileItemReader<NACHARecord>();
        fileItemReader.setResource(new ClassPathResource("TestData.txt"));
        fileItemReader.setLineMapper(nachaLineMapper());
        return fileItemReader;
    }

    @Bean
    public ItemReader<NACHABatch> itemReader(){
        SimpleItemReader reader = new SimpleItemReader(fileItemReader());
        return reader;
    }

    public ItemWriter itemWriter(){
            return new SimpleItemWriter();
    }

    private LineTokenizer lineTokenizer(){
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        tokenizer.setNames("recordType","line");
        tokenizer.setColumns(new Range(1,1), new Range(2));

        return tokenizer;
    }

    private FieldSetMapper nachaFieldSetMapper(){
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(NACHARecord.class);
        return fieldSetMapper;
    }


    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .chunk(1)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

}
