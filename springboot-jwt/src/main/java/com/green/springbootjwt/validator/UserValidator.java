package com.green.springbootjwt.validator;

import com.green.springbootjwt.annotation.UserValidatorAnnotation;
import com.green.springbootjwt.repo.UserRepository;
import com.green.springbootjwt.util.AppUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class UserValidator implements ConstraintValidator<UserValidatorAnnotation, Object> {

    private String email;
    private String password;
    private String confirmPassword;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void initialize(final UserValidatorAnnotation constraintAnnotation) {
        email = constraintAnnotation.email();
        password = constraintAnnotation.password();
        confirmPassword = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(final Object object, ConstraintValidatorContext context) {
        if (Objects.isNull(object)) return true;

        try {
            final String passwordValue = BeanUtils.getProperty(object, password);
            final String confirmationPasswordValue = BeanUtils.getProperty(object, confirmPassword);
            final String emailValue = BeanUtils.getProperty(object, email);

            if (!passwordValue.equals(confirmationPasswordValue)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(AppUtils.ERROR_PASSWORD_CONFIRM_MISS_MATCH)
                        .addConstraintViolation();

                return false;
            }
            if (userRepository.findByEmail(emailValue).isPresent()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(AppUtils.ERROR_EMAIL_EXISTS)
                        .addConstraintViolation();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
