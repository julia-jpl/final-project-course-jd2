package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.ArticleRepository;
import com.gmail.portnova.julia.repository.ItemRepository;
import com.gmail.portnova.julia.repository.RoleRepository;
import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Item;
import com.gmail.portnova.julia.repository.model.Role;
import com.gmail.portnova.julia.repository.model.RoleNameEnum;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.exception.UserRoleNotFoundException;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.PageableUser;
import com.gmail.portnova.julia.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.gmail.portnova.julia.service.util.PageUtil.getNumberOfPages;
import static com.gmail.portnova.julia.service.util.PageUtil.getStartPosition;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GeneralConverter<User, UserDTO> userConverter;
    private final RoleRepository roleRepository;
    private final ArticleRepository articleRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public UserDTO findUserByEmail(String username) {
        User user = userRepository.findByEmail(username);
        if (Objects.nonNull(user)) {
            return userConverter.convertObjectToDTO(user);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public UserDTO deleteByUUID(String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            userRepository.remove(user);
            return userConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }

    @Override
    @Transactional
    public UserDTO findByUuid(String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            return userConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }

    @Override
    @Transactional
    public UserDTO changeUserRole(String id, String newRole) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            if (user.getRole().getRoleName().equals(RoleNameEnum.SALE_USER)) {
                List<Item> items = itemRepository.findByUserUuid(user.getUuid());
                if (!items.isEmpty()) {
                    for (Item item : items) {
                        List<User> users = item.getUsers();
                        users.remove(user);
                    }
                }
            }
            RoleNameEnum roleName = RoleNameEnum.valueOf(newRole);
            Role role = roleRepository.findByName(roleName);
            if (Objects.nonNull(role)) {
                user.setRole(role);
                return userConverter.convertObjectToDTO(user);
            } else {
                throw new UserRoleNotFoundException(String.format("Role with name %s was not found", newRole));
            }
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }

    @Transactional
    @Override
    public PageDTO<UserDTO> getUsersPage(String email, int pageNumber, int maxResult) {
        Long numberOfRows = userRepository.count();
        Long numberOfRowsExceptCurrentUser = numberOfRows - 1;
        PageableUser page = new PageableUser();
        page.setTotalPages(getNumberOfPages(numberOfRowsExceptCurrentUser, maxResult));
        int startPosition = getStartPosition(pageNumber, maxResult);
        List<User> users = userRepository.findAllExceptCurrent(email, startPosition, maxResult);
        List<UserDTO> userDTOS = getPageDTOList(users);
        page.getObjects().addAll(userDTOS);
        return page;
    }

    @Transactional
    @Override
    public UserDTO changeUserLastname(String lastName, String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            user.setLastName(lastName);
            return userConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }

    @Transactional
    @Override
    public UserDTO changeUserFirstName(String firstName, String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            user.setFirstName(firstName);
            return userConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format("User with uuid %s was not found", id));
        }
    }

    protected List<UserDTO> getPageDTOList(List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();
        if (!users.isEmpty()) {
            List<UserDTO> list = new ArrayList<>();
            for (User user : users) {
                UserDTO userDTO = userConverter.convertObjectToDTO(user);
                list.add(userDTO);
            }
            userDTOS = list;
        }
        return userDTOS;
    }
}
