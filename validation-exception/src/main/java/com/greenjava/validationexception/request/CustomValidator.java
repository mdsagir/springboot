package com.greenjava.validationexception.request;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CustomValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Student student = (Student) target;

        if (student.getId()<2) {

            errors.rejectValue("id", null, "Student with this email is already exists.");
        }
        if (student.getName().equals("sagir")){

            //errors.rejectValue("name","exist");
            errors.rejectValue("name",null,"name already exists");
        }
    }
}
