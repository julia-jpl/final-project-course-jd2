package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.ItemGroupRepository;
import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.*;
import com.gmail.portnova.julia.service.exception.ImpossibleToSaveItemRelatedToUser;
import com.gmail.portnova.julia.service.exception.ItemGroupNotFoundException;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.model.ItemApiDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemApiConverterImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemGroupRepository itemGroupRepository;
    @InjectMocks
    private ItemApiConverterImpl itemApiConverter;

    @Test
    void shouldConvertItemToItemApiDTOAndReturnRightUuid() {
        Item item = new Item();
        UUID uuid = UUID.randomUUID();
        item.setUuid(uuid);

        ItemApiDTO itemDTO = itemApiConverter.convertObjectToDTO(item);
        assertEquals(uuid, itemDTO.getUuid());
    }

    @Test
    void shouldConvertItemToItemApiDTOAndReturnRightUniqueNumber() {
        Item item = new Item();
        String uniqueNumber = "number";
        item.setUniqueNumber(uniqueNumber);

        ItemApiDTO itemDTO = itemApiConverter.convertObjectToDTO(item);
        assertEquals(uniqueNumber, itemDTO.getUniqueNumber());
    }

    @Test
    void shouldConvertItemToItemApiDTOAndReturnRightName() {
        Item item = new Item();
        String name = "name";
        item.setName(name);

        ItemApiDTO itemDTO = itemApiConverter.convertObjectToDTO(item);
        assertEquals(name, itemDTO.getName());
    }

    @Test
    void shouldConvertItemToItemApiDTOAndReturnRightPriceWhenItemDetailNotNull() {
        Item item = new Item();
        ItemDetail itemDetail = new ItemDetail();
        BigDecimal price = new BigDecimal(11.2);
        itemDetail.setPrice(price);
        item.setItemDetail(itemDetail);

        ItemApiDTO itemDTO = itemApiConverter.convertObjectToDTO(item);
        assertEquals(price, itemDTO.getPrice());
    }

    @Test
    void shouldConvertItemToItemApiDTOAndReturnRightDescription() {
        Item item = new Item();
        String description = "description";
        item.setDescription(description);

        ItemApiDTO itemDTO = itemApiConverter.convertObjectToDTO(item);
        assertEquals(description, itemDTO.getDescription());
    }

    @Test
    void shouldConvertItemToItemApiDTOAndReturnRightGroupNameWhenItemGroupIsNotNull() {
        Item item = new Item();
        ItemGroup itemGroup = new ItemGroup();
        ItemGroupNameEnum itemGroupNameEnum = ItemGroupNameEnum.FASHION;
        itemGroup.setItemGroupNameEnum(itemGroupNameEnum);
        item.setItemGroup(itemGroup);

        ItemApiDTO itemDTO = itemApiConverter.convertObjectToDTO(item);
        assertEquals(itemGroupNameEnum.name(), itemDTO.getItemGroup());
    }

    @Test
    void shouldConvertItemToItemApiDTOAndReturnRightUserSaleUuidsWhenListIsNotEmpty() {
        Item item = new Item();
        User user = new User();
        UUID uuid = UUID.randomUUID();
        user.setUuid(uuid);
        List<User> users = Collections.singletonList(user);
        item.getUsers().addAll(users);
        List<String> stringUuids = Collections.singletonList(uuid.toString());

        ItemApiDTO itemDTO = itemApiConverter.convertObjectToDTO(item);
        assertEquals(stringUuids.get(0), itemDTO.getUserUuids().get(0));
    }

    @Test
    void shouldConvertItemToItemApiDTOAndReturnEmptyListOfUserSaleUuids() {
        Item item = new Item();

        ItemApiDTO itemDTO = itemApiConverter.convertObjectToDTO(item);
        assertEquals(Collections.emptyList(), itemDTO.getUserUuids());
    }

    @Test
    void shouldConvertItemApiDTOToItemAndReturnRightUuid() {
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        UUID uuid = UUID.randomUUID();
        itemApiDTO.setUuid(uuid);

        Item item = itemApiConverter.convertDTOToObject(itemApiDTO);
        assertEquals(uuid, item.getUuid());
    }

    @Test
    void shouldConvertItemApiDTOToItemAndReturnRightUniqueNumber() {
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        String uniqueNumber = "number";
        itemApiDTO.setUniqueNumber(uniqueNumber);

        Item item = itemApiConverter.convertDTOToObject(itemApiDTO);
        assertEquals(uniqueNumber, item.getUniqueNumber());
    }

    @Test
    void shouldConvertItemApiDTOToItemAndReturnRightName() {
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        String name = "name";
        itemApiDTO.setName(name);

        Item item = itemApiConverter.convertDTOToObject(itemApiDTO);
        assertEquals(name, item.getName());
    }

    @Test
    void shouldConvertItemApiDTOToItemAndReturnItemDetailWithRightPrice() {
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        BigDecimal price = new BigDecimal(11.2);
        itemApiDTO.setPrice(price);


        Item item = itemApiConverter.convertDTOToObject(itemApiDTO);
        assertEquals(price, item.getItemDetail().getPrice());
    }

    @Test
    void shouldConvertItemApiDTOToItemAndReturnRightDescription() {
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        String description = "description";
        itemApiDTO.setDescription(description);

        Item item = itemApiConverter.convertDTOToObject(itemApiDTO);
        assertEquals(description, item.getDescription());
    }

    @Test
    void shouldConvertItemApiDTOToItemAndReturnRightIsDeleted() {
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        itemApiDTO.setDeleted(false);

        Item item = itemApiConverter.convertDTOToObject(itemApiDTO);
        assertFalse(item.getIsDeleted());
    }

    @Test
    void shouldConvertItemApiDTOToItemAndReturnRightGroupNameWhenItemGroupIsNotNull() {
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        ItemGroupNameEnum itemGroupNameEnum = ItemGroupNameEnum.FASHION;
        itemApiDTO.setItemGroup(itemGroupNameEnum.name());
        ItemGroup itemGroup = new ItemGroup();
        itemGroup.setItemGroupNameEnum(itemGroupNameEnum);

        when(itemGroupRepository.findByName(ItemGroupNameEnum.FASHION)).thenReturn(itemGroup);

        Item item = itemApiConverter.convertDTOToObject(itemApiDTO);
        assertEquals(itemGroupNameEnum, item.getItemGroup().getItemGroupNameEnum());
    }

    @Test
    void shouldConvertItemApiDTOToItemAndTrowExceptionWhenItemGroupIsNotNull() {
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        ItemGroupNameEnum itemGroupNameEnum = ItemGroupNameEnum.FASHION;
        itemApiDTO.setItemGroup(itemGroupNameEnum.name());

        when(itemGroupRepository.findByName(ItemGroupNameEnum.FASHION)).thenReturn(null);

        assertThrows(ItemGroupNotFoundException.class, () -> itemApiConverter.convertDTOToObject(itemApiDTO));
    }

    @Test
    void shouldConvertItemApiDTOToItemAndReturnRightUserSaleList() {
        UUID uuid = UUID.randomUUID();
        List<String> stringUuids = Collections.singletonList(uuid.toString());
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        itemApiDTO.setUserUuids(stringUuids);
        User user = new User();
        user.setUuid(uuid);
        Role role = new Role();
        role.setRoleName(RoleNameEnum.SALE_USER);
        user.setRole(role);

        when(userRepository.findByUuid(uuid)).thenReturn(user);

        Item item = itemApiConverter.convertDTOToObject(itemApiDTO);
        assertEquals(user, item.getUsers().get(0));
    }

    @Test
    void shouldConvertItemApiDTOToItemAndThrowExceptionWhenUserIsNull() {
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        UUID uuid = UUID.randomUUID();
        List<String> stringUuids = Collections.singletonList(uuid.toString());
        itemApiDTO.setUserUuids(stringUuids);

        when(userRepository.findByUuid(uuid)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> itemApiConverter.convertDTOToObject(itemApiDTO));
    }

    @Test
    void shouldConvertItemApiDTOToItemAndThrowExceptionWhenUserHasWrongRole() {
        UUID uuid = UUID.randomUUID();
        List<String> stringUuids = Collections.singletonList(uuid.toString());
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        itemApiDTO.setUserUuids(stringUuids);
        User user = new User();
        user.setUuid(uuid);
        Role role = new Role();
        role.setRoleName(RoleNameEnum.ADMINISTRATOR);
        user.setRole(role);

        when(userRepository.findByUuid(uuid)).thenReturn(user);

        assertThrows(ImpossibleToSaveItemRelatedToUser.class, () -> itemApiConverter.convertDTOToObject(itemApiDTO));
    }
}