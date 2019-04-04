package com.terrencewei.batch.config;

import com.terrencewei.batch.Validator.PersonValidator;
import com.terrencewei.batch.dto.Person;
import com.terrencewei.batch.listener.MyJobExecListener;
import com.terrencewei.batch.processor.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

/**
 * Created by terrence on 2018/05/23.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;



    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>().name("personItemReader").resource(
                new ClassPathResource("/data/sample-batch-job-data.csv")).delimited()
                                                      .names(new String[] { "name", "password" })
                                                      .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                                                          setTargetType(Person.class);
                                                      }}).build();
    }



    @Bean
    public PersonItemProcessor processor() {
        PersonItemProcessor processor = new PersonItemProcessor();
        processor.setValidator(getPersonValidator());
        return processor;
    }



    @Bean
    public Validator<Person> getPersonValidator() {
        return new PersonValidator();
    }



    @Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO people (name, password) VALUES (:name, :password)").dataSource(dataSource).build();
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]



    @Bean
    public Job importUserJob(MyJobExecListener listener, Step step) {
        return jobBuilderFactory.get("MyImportUserJob").incrementer(new RunIdIncrementer()).listener(listener)
                                .flow(step).end().build();
    }



    @Bean
    public Step step1(JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory.get("step1").<Person, Person>chunk(10).reader(reader()).processor(processor())
                                                                        .writer(writer).build();
    }
    // end::jobstep[]

}