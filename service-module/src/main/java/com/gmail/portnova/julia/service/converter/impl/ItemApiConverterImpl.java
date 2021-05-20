package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.ItemGroupRepository;
import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.*;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.EntityNotFoundException;
import com.gmail.portnova.julia.service.exception.ItemGroupNotFoundException;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.model.ItemApiDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.*;

@Component
@RequiredArgsConstructor
public class ItemApiConverterImpl implements GeneralConverter<Item, ItemApiDTO> {
    private final UserRepository userRepository;
    private final ItemGroupRepository itemGroupRepository;

    @Override
    public ItemApiDTO convertObjectToDTO(Item item) {
        if (Objects.nonNull(item)) {
            ItemApiDTO itemApiDTO = new ItemApiDTO();
            itemApiDTO.setUuid(item.getUuid());
            itemApiDTO.setUniqueNumber(item.getUniqueNumber());
            itemApiDTO.setName(item.getName());
            itemApiDTO.setDescription(item.getDescription());
            ItemDetail itemDetail = item.getItemDetail();
            if (Objects.nonNull(itemDetail)) {
                itemApiDTO.setPrice(itemDetail.getPrice());
            }
            ItemGroup itemGroup = item.getItemGroup();
            if (Objects.nonNull(itemGroup)) {
                String groupName = itemGroup.getItemGroupNameEnum().name();
                itemApiDTO.setItemGroup(groupName);
            }
            List<User> users = item.getUsers();
            if (!users.isEmpty()) {
                List<String> userUuids = users.stream()
                        .map(User::getUuid)
                        .map(UUID::toString)
                        .collect(Collectors.toList());
                itemApiDTO.getUserUuids().addAll(userUuids);
            }
            return itemApiDTO;
        } else {
            return null;
        }
    }

    @Override
    public Item convertDTOToObject(ItemApiDTO itemApiDTO) {
        Item item = new Item();
        item.setUuid(itemApiDTO.getUuid());
        item.setUniqueNumber(itemApiDTO.getUniqueNumber());
        item.setName(itemApiDTO.getName());
        item.setDescription(itemApiDTO.getDescription());
        ItemDetail itemDetail = getItemDetail(itemApiDTO);
        item.setItemDetail(itemDetail);
        itemDetail.setItem(item);
        String itemGroupName = itemApiDTO.getItemGroup();
        ItemGroupNameEnum itemGroupNameEnum = ItemGroupNameEnum.valueOf(itemGroupName);
        ItemGroup itemGroup = itemGroupRepository.findByName(itemGroupNameEnum);
        if (Objects.nonNull(itemGroup)) {
            item.setItemGroup(itemGroup);
        } else {
            throw new ItemGroupNotFoundException(String.format(ITEM_GROUP_NOT_FOUND_EXCEPTION_MESSAGE, itemGroupName));
        }
        List<User> users = getUsers(itemApiDTO);
        item.getUsers().addAll(users);
        return item;
    }

    protected List<User> getUsers(ItemApiDTO itemApiDTO) {
        List<String> userUuids = itemApiDTO.getUserUuids();
        List<User> users = new ArrayList<>();
        for (String userUuidString : userUuids) {
            UUID userUuid = UUID.fromString(userUuidString);
            User user = userRepository.findByUuid(userUuid);
            if (Objects.nonNull(user)) {
                if (user.getRole().getRoleName().equals(RoleNameEnum.CUSTOMER_USER)) {
                    users.add(user);
                }
            } else {
                throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_EXCEPTION_MESSAGE, User.class, userUuidString));
            }
        }
        return users;
    }

    protected ItemDetail getItemDetail(ItemApiDTO itemApiDTO) {
        ItemDetail itemDetail = new ItemDetail();
        itemDetail.setPrice(itemApiDTO.getPrice());
        return itemDetail;
    }
}
