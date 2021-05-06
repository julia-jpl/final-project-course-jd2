package com.gmail.portnova.julia.web.validator;

import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.model.RoleNameEnumDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.gmail.portnova.julia.web.constant.ValidationConstant.*;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "user.lastName.empty", "lastName is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "user.firstName.empty", "firstName is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "middleName", "user.middleName.empty", "middleName is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "user.email.empty", "email is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleName", "user.roleName.empty", "roleName is required");
        UserDTO user = (UserDTO) target;
        if (!user.getLastName().matches(LAST_AND_MIDDLE_NAME_PATTERN)) {
            errors.rejectValue("lastName", "user.lastName.invalid", "last name doesn't match pattern");
        }
        if (!user.getFirstName().matches(FIRST_NAME_PATTERN)) {
            errors.rejectValue("firstName", "user.firstname.invalid", "first name doesn't match pattern");
        }
        if (!user.getMiddleName().matches(LAST_AND_MIDDLE_NAME_PATTERN)) {
            errors.rejectValue("middleName", "user.middleName.invalid", "middle name doesn't match pattern");
        }
        if (!user.getEmail().matches(EMAIL_PATTERN)) {
            errors.rejectValue("email", "user.email.invalid", "email doesn't match pattern");
        }
        UserDTO existedUser = userService.findUserByEmail(user.getEmail());
        if (existedUser != null) {
            errors.rejectValue("email", "user.email.existed", "This email has already existed");
        }
        if (!user.getRoleName().equals(RoleNameEnumDTO.ADMINISTRATOR.name()) &&
                !user.getRoleName().equals(RoleNameEnumDTO.SALE_USER.name()) &&
                !user.getRoleName().equals(RoleNameEnumDTO.CUSTOMER_USER.name()) &&
                !user.getRoleName().equals(RoleNameEnumDTO.SECURE_REST_API.name())) {
            errors.rejectValue("roleName", "user.roleName.invalid", "This role doesn't exist");
        }
    }
}
