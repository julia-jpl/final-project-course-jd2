package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.UserDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GeneralConverter<User, UserDTO> userConverter;


    public UserServiceImpl(UserRepository userRepository,
                           GeneralConverter<User, UserDTO> userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

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
    public List<UserDTO> findAllUsersExceptCurrent(String email, Integer page, Integer maxResult) {
        int startPosition = maxResult * (page - 1);
        List<User> users = userRepository.findAllExceptCurrent(email, startPosition, maxResult);
        return users.stream()
                .map(userConverter::convertObjectToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long countPages(Integer maxResult) {
        Long numberOfRows = userRepository.count();
        return (numberOfRows / maxResult) + 1;
    }

    @Override
    @Transactional
    public void deleteByUUID(String id) {
        User user = userRepository.findByUuid(id);
        userRepository.remove(user);
    }
}
