package com.javagreen.controller;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class UserNameValidator implements ConstraintValidator<UserNameValidatorAnnotation, Object> {

    private static final Logger logger = LogManager.getLogger(UserNameValidator.class);


    private String password;
    private String confirmationPassword;


    @Override
    public void initialize(final UserNameValidatorAnnotation annotation) {

        password = annotation.password();
        confirmationPassword = annotation.confirmationPassword();
    }

    @Override
    public boolean isValid(final Object object, ConstraintValidatorContext context) {

        if (Objects.isNull(object)) return true;

        try {
            final String password2 = BeanUtils.getProperty(object, password);
            final String confirmationPassword2 = BeanUtils.getProperty(object, confirmationPassword);

            if (!password2.equals(confirmationPassword2)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(Constants.ERROR_PASSWORD_CONFIRM_MISS_MATCH)
                        .addConstraintViolation();

                return false;
            }

        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return true;
    }
}