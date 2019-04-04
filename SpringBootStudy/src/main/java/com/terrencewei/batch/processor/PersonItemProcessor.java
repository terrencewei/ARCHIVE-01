package com.terrencewei.batch.processor;

import com.terrencewei.batch.dto.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.validator.ValidatingItemProcessor;

/**
 * Created by terrence on 2018/05/23.
 */
public class PersonItemProcessor extends ValidatingItemProcessor<Person> {

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);



    @Override
    public Person process(final Person person) {
        // invoke super.process() to call custome validator
        super.process(person);

        final String transformedName = person.getName().toLowerCase();
        final String transformedPassword = person.getPassword();
        final Person transformedPerson = new Person(transformedName, transformedPassword);
        log.info("Converting (" + person + ") into (" + transformedPerson + ")");
        return transformedPerson;
    }

}