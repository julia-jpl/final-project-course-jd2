package com.gmail.portnova.julia.web.validator;

import com.gmail.portnova.julia.service.model.FormOrderDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;

import static com.gmail.portnova.julia.web.constant.ValidationConstant.TELEPHONE_PATTERN;

@Component
public class FormOrderWithTelephoneValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return FormOrderDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "itemQuantity", "formOrder.itemQuantity.empty", "Quantity is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerTel", "formOrder.customerTel.empty", "Telephone is required");
        FormOrderDTO formOrder = (FormOrderDTO) target;
        if (!formOrder.getCustomerTel().matches(TELEPHONE_PATTERN)) {
            errors.rejectValue("customerTel", "formOrder.customerTel.invalid", "Telephone should match pattern +375-22-222-22-22");
        }
        if (Objects.isNull(formOrder.getItemQuantity())) {
            errors.rejectValue("itemQuantity", "formOrder.itemQuantity.invalid", "Quantity is required");
        } else {
            if ((formOrder.getItemQuantity() > 10) || (formOrder.getItemQuantity() < 1)) {
                errors.rejectValue("itemQuantity", "formOrder.itemQuantity.invalid", "The quantity of item should be from 1 to 10");
            }
        }
    }
}
