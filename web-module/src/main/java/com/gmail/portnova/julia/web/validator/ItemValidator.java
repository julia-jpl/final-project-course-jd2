package com.gmail.portnova.julia.web.validator;

import com.gmail.portnova.julia.service.model.ItemApiDTO;
import com.gmail.portnova.julia.service.model.ItemDTO;
import com.gmail.portnova.julia.service.model.ItemGroupNameEnumDTO;
import com.gmail.portnova.julia.service.model.RoleNameEnumDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.portnova.julia.web.constant.ValidationConstant.BIG_DECIMAL_PATTERN;

@Component
@RequiredArgsConstructor
public class ItemValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return ItemApiDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "item.name.empty", "Name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "item.description.empty", "Description is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "item.price.empty", "Price is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userUuids", "item.userUuids.empty", "Choose sale user");
        ItemApiDTO item = (ItemApiDTO) target;
        if (item.getName().length() > 100) {
            errors.rejectValue("name", "item.name.invalid", "Size of name should be under 100");
        }
        if (item.getDescription().length() > 200) {
            errors.rejectValue("description", "item.description.invalid", "Description of name should under 200");
        }
        if (!item.getPrice().toString().matches(BIG_DECIMAL_PATTERN)) {
            errors.rejectValue("price", "item.price.invalid", "Price doesn't match pattern");
        }
        if (!item.getItemGroup().equals(ItemGroupNameEnumDTO.ELECTRONICS.name()) &&
                !item.getItemGroup().equals(ItemGroupNameEnumDTO.FASHION.name()) &&
                !item.getItemGroup().equals(ItemGroupNameEnumDTO.SPORTS.name()) &&
                !item.getItemGroup().equals(ItemGroupNameEnumDTO.HEALTH_BEAUTY.name())) {
            errors.rejectValue("itemGroup", "item.itemGroup.invalid", "ItemGroup doesn't exist");
        }
    }
}