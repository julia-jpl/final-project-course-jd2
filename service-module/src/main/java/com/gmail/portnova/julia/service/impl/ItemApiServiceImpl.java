package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ItemRepository;
import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.ItemApiService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ImpossibleToDeleteItemException;
import com.gmail.portnova.julia.service.exception.ItemNotFoundException;
import com.gmail.portnova.julia.service.model.ItemApiDTO;
import com.gmail.portnova.julia.service.model.ItemGroupNameEnumDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.IMPOSSIBLE_TO_DELETE_ITEM_EXCEPTION_MESSAGE;
import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.ITEM_NOT_FOUND_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class ItemApiServiceImpl implements ItemApiService {

    private final ItemRepository itemRepository;
    private final GeneralConverter<Item, ItemApiDTO> itemApiConverter;

    @Override
    @Transactional
    public ItemApiDTO getItemApiByUuid(String uuidString) {
        UUID itemUuid = UUID.fromString(uuidString);
        Item item = itemRepository.findByUuid(itemUuid);
        if (Objects.nonNull(item)) {
            return itemApiConverter.convertObjectToDTO(item);
        } else {
            throw new ItemNotFoundException(String.format(ITEM_NOT_FOUND_EXCEPTION_MESSAGE, uuidString));
        }
    }

    @Override
    @Transactional
    public void deleteItemApiByUuidNotRelatedToSaleUser(String uuidString) {
        UUID itemUuid = UUID.fromString(uuidString);
        Item item = itemRepository.findByUuid(itemUuid);
        if (Objects.nonNull(item)) {
            List<User> users = item.getUsers();
            if (users.isEmpty()) {
                itemRepository.remove(item);
            } else {
                String usersUuids = users.stream()
                        .map(User::getUuid)
                        .map(UUID::toString)
                        .collect(Collectors.joining(","));
                throw new ImpossibleToDeleteItemException(String.format(IMPOSSIBLE_TO_DELETE_ITEM_EXCEPTION_MESSAGE, uuidString, usersUuids));
            }
        } else {
            throw new ItemNotFoundException(String.format(ITEM_NOT_FOUND_EXCEPTION_MESSAGE, uuidString));
        }
    }

    @Override
    @Transactional
    public void addItemToDatabase(ItemApiDTO itemDTO) {
        UUID uuid = UUID.randomUUID();
        itemDTO.setUuid(uuid);
        String itemGroup = itemDTO.getItemGroup();
        String uniqueNumber = getItemUniqueNumber(itemGroup);
        itemDTO.setUniqueNumber(uniqueNumber);
        Item item = itemApiConverter.convertDTOToObject(itemDTO);
        itemRepository.persist(item);
    }

    protected String getItemUniqueNumber(String itemGroup) {
        Long numberCode = System.nanoTime();
        String groupCode = null;
        ItemGroupNameEnumDTO itemGroupNameEnumDTO = ItemGroupNameEnumDTO.valueOf(itemGroup);
        switch (itemGroupNameEnumDTO) {
            case ELECTRONICS:
                groupCode = "EL";
                break;
            case FASHION:
                groupCode = "FA";
                break;
            case SPORTS:
                groupCode = "SP";
                break;
            case HEALTH_BEAUTY:
                groupCode = "HB";
                break;
            default:
                break;
        }
        return String.join("-", groupCode, numberCode.toString());
    }
}
