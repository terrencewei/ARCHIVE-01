package com.terrencewei.batch.Validator;

import com.terrencewei.batch.dto.Person;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.InitializingBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by terrence on 2018/05/23.
 */
public class PersonValidator implements Validator<Person>, InitializingBean {
    private javax.validation.Validator validator;



    @Override
    public void afterPropertiesSet() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }



    @Override
    public void validate(Person value) throws ValidationException {
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(value);
        if (constraintViolations.size() > 0) {
            StringBuilder message = new StringBuilder();
            for (ConstraintViolation<Person> constraintViolation : constraintViolations) {
                message.append(constraintViolation.getMessage() + "\n");
            }
            throw new ValidationException(message.toString());
        }
    }
}