package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ItemRepository;
import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.ItemService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ItemNotFoundException;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.model.ItemDTO;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.PageableItem;
import com.gmail.portnova.julia.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.USER_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.gmail.portnova.julia.service.util.PageUtil.getNumberOfPages;
import static com.gmail.portnova.julia.service.util.PageUtil.getStartPosition;

@Service
@RequiredArgsConstructor
@Log4j2
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final GeneralConverter<Item, ItemDTO> itemConverter;
    private final GeneralConverter<User, UserDTO> userConverter;

    @Override
    @Transactional
    public PageDTO<ItemDTO> getAllItemsPage(int pageNumber, int maxResult) {
        Long numberOfRows = itemRepository.count();
        PageableItem pageDTO = new PageableItem();
        Long numberOfPages = getNumberOfPages(numberOfRows, maxResult);
        pageDTO.setTotalPages(numberOfPages);
        int startPosition = getStartPosition(pageNumber, maxResult);
        List<Item> items = itemRepository.findAllWithLimit(startPosition, maxResult);
        List<ItemDTO> itemDTOS = getItemDTOList(items);
        pageDTO.getObjects().addAll(itemDTOS);
        return pageDTO;
    }

    @Override
    @Transactional
    public PageDTO<ItemDTO> getSaleUserItemsPage(int pageNumber, int maxResult, UUID userUuid) {
        Long numberOfRows = itemRepository.countItemsByUserUuid(userUuid);
        PageableItem pageDTO = new PageableItem();
        Long numberOfPages = getNumberOfPages(numberOfRows, maxResult);
        pageDTO.setTotalPages(numberOfPages);
        int startPosition = getStartPosition(pageNumber, maxResult);
        List<Item> items = itemRepository.findItemsWithLimitByUserUuid(startPosition, maxResult, userUuid);
        List<ItemDTO> itemDTOS = getItemDTOList(items);
        pageDTO.getObjects().addAll(itemDTOS);
        return pageDTO;
    }

    @Override
    @Transactional
    public List<UserDTO> deleteItemFromSaleUserCatalog(String uuid, UUID currentUserUuid) {
        UUID itemUuid = UUID.fromString(uuid);
        Item item = itemRepository.findByUuid(itemUuid);
        if (Objects.nonNull(item)) {
            List<User> itemUsers = item.getUsers();
            if (itemUsers.size() > 1) {
                Optional<User> optionalUser = itemUsers.stream()
                        .filter(user -> user.getUuid().equals(currentUserUuid))
                        .findFirst();
                try {
                    User user = optionalUser.get();
                    itemUsers.remove(user);
                    return itemUsers.stream()
                            .map(userConverter::convertObjectToDTO).collect(Collectors.toList());
                } catch (NoSuchElementException e) {
                    log.error(e.getMessage());
                    throw new UserNotFoundException(String.format(ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, User.class, currentUserUuid));
                }
            } else {
                itemUsers.clear();
                itemRepository.remove(item);
                return Collections.emptyList();
            }
        } else {
            throw new ItemNotFoundException(String.format(ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, Item.class, uuid));
        }
    }

    @Override
    @Transactional
    public String copyItemTOClipboard(String uuid) {
        UUID itemUuid = UUID.fromString(uuid);
        Item item = itemRepository.findByUuid(itemUuid);
        ItemDTO itemDTO = itemConverter.convertObjectToDTO(item);
        StringSelection stringSelection = new StringSelection(itemDTO.toString());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        return itemDTO.toString();
    }

    @Override
    @Transactional
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAllWithoutRelationToSaleUser();
        return items.stream()
                .map(itemConverter::convertObjectToDTO)
                .collect(Collectors.toList());
    }

    protected List<ItemDTO> getItemDTOList(List<Item> items) {
        List<ItemDTO> itemDTOS = new ArrayList<>();
        if (!items.isEmpty()) {
            List<ItemDTO> list = new ArrayList<>();
            for (Item item : items) {
                ItemDTO itemDTO = itemConverter.convertObjectToDTO(item);
                list.add(itemDTO);
            }
            itemDTOS = list;
        }
        return itemDTOS;
    }
}
