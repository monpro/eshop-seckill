package com.seckill.validator;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidatorImpl implements InitializingBean {
    private Validator validator;

    public ValidationResult validate(Object bean){
        final ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> validationSet = validator.validate(bean);

        if(validationSet.size() > 0){
            result.setHasErrors(true);

            validationSet.forEach(violation -> {
                String errorMsg = violation.getMessage();
                String path = violation.getPropertyPath().toString();

                result.getErrorMsgMap().put(path, errorMsg);
            });
        }
        return result;
    }
    @Override
    public void afterPropertiesSet() throws Exception{
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();

    }
}
