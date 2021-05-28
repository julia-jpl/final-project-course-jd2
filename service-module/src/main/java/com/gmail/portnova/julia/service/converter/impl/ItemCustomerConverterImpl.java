package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.repository.model.ItemDetail;
import com.gmail.portnova.julia.repository.model.RoleNameEnum;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.ItemCustomerDTO;
import com.gmail.portnova.julia.service.model.SellerDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ItemCustomerConverterImpl implements GeneralConverter<Item, ItemCustomerDTO> {
    @Override
    public ItemCustomerDTO convertObjectToDTO(Item item) {
        ItemCustomerDTO itemDTO = new ItemCustomerDTO();
        itemDTO.setUuid(item.getUuid());
        itemDTO.setName(item.getName());
        ItemDetail itemDetail = item.getItemDetail();
        if (Objects.nonNull(itemDetail)) {
            itemDTO.setPrice(itemDetail.getPrice());
        }
        List<User> users = item.getUsers();
        if (!users.isEmpty()) {
            for (User user : users) {
                if (user.getRole().getRoleName().equals(RoleNameEnum.SALE_USER)) {
                    SellerDTO sellerDTO = new SellerDTO();
                    sellerDTO.setSellerName(String.join(" ", user.getLastName(), user.getFirstName()));
                    sellerDTO.setUuid(user.getUuid());
                    itemDTO.getSellers().add(sellerDTO);
                }
            }
        }
        return itemDTO;
    }

    @Override
    public Item convertDTOToObject(ItemCustomerDTO object) {
        throw new UnsupportedOperationException("This method hasn't been implemented");
    }
}
