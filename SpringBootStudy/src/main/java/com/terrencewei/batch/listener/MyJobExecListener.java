package com.terrencewei.batch.listener;

import com.terrencewei.batch.dto.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class MyJobExecListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(MyJobExecListener.class);

    private final JdbcTemplate jdbcTemplate;



    @Autowired
    public MyJobExecListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("===================================");
        System.out.println();
        System.out.println("      beforeJob");
        System.out.println();
        System.out.println("===================================");
    }



    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println("===================================");
            System.out.println();
            System.out.println("      afterJob");
            jdbcTemplate.query("SELECT name, password FROM people",
                    (rs, row) -> new Person(rs.getString(1), rs.getString(2))).forEach(
                    person -> log.info(MessageFormat.format("Verify Job Result in DB: person:{0}", person)));
            System.out.println();
            System.out.println("===================================");
        }
    }
}