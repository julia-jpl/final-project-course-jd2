package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.OrderRepository;
import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Order;
import com.gmail.portnova.julia.repository.model.OrderDetail;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.repository.model.UserDetail;
import com.gmail.portnova.julia.service.ProfileService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.model.ProfileUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.ENTITY_WITH_NAME_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.gmail.portnova.julia.service.constant.ExceptionMessageConstant.ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final GeneralConverter<User, ProfileUserDTO> profileConverter;
    private final OrderRepository orderRepository;


    @Override
    @Transactional
    public ProfileUserDTO getUserProfile(UUID uuid) {
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            return profileConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format(
                    ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, User.class, uuid));
        }
    }

    @Transactional
    @Override
    public ProfileUserDTO changeUserAddress(String address, String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            UserDetail userDetail = user.getUserDetail();
            if (Objects.nonNull(userDetail)) {
                userDetail.setAddress(address);
            } else {
                UserDetail newUserDetail = new UserDetail();
                newUserDetail.setAddress(address);
                user.setUserDetail(newUserDetail);
                newUserDetail.setUser(user);
            }
            return profileConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format(
                    ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, User.class, id));
        }
    }

    @Transactional
    @Override
    public ProfileUserDTO changeUserLastname(String lastName, String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findByUuid(uuid);
        if (Objects.nonNull(user)) {
            user.setLastName(lastName);
            return profileConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format(
                    ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, User.class, id));
        }
    }

    @Transactional
    @Override
    public void changeProfileFirstName(String firstName, String id) {
        UUID userCustomerUuid = UUID.fromString(id);
        changeUserFirstName(firstName, userCustomerUuid);
        changeOrderCustomerIdentifier(firstName, userCustomerUuid);

    }

    @Transactional
    @Override
    public void changeProfileTelephone(String telephone, String id) {
        UUID userCustomerUuid = UUID.fromString(id);
        changeUserTelephone(telephone, userCustomerUuid);
        changeOrderDetailCustomerTelephone(telephone, userCustomerUuid);
    }


    @Override
    @Transactional
    public ProfileUserDTO getUserProfileByEmail(String username) {
        User user = userRepository.findByEmail(username);
        if (Objects.nonNull(user)) {
            return profileConverter.convertObjectToDTO(user);
        } else
            throw new UserNotFoundException(String.format(
                    ENTITY_WITH_NAME_NOT_FOUND_EXCEPTION_MESSAGE, User.class, username));
    }

    protected ProfileUserDTO changeUserTelephone(String telephone, UUID userCustomerUuid) {
        User user = userRepository.findByUuid(userCustomerUuid);
        if (Objects.nonNull(user)) {
            UserDetail userDetail = user.getUserDetail();
            if (Objects.nonNull(userDetail)) {
                userDetail.setTelephone(telephone);
            } else {
                UserDetail newUserDetail = new UserDetail();
                newUserDetail.setTelephone(telephone);
                user.setUserDetail(newUserDetail);
                newUserDetail.setUser(user);
            }
            return profileConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format(
                    ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, User.class, userCustomerUuid));
        }
    }

    protected void changeOrderDetailCustomerTelephone(String telephone, UUID userCustomerUuid) {
        List<Order> orders = orderRepository.findByCustomerUuid(userCustomerUuid);
        if (!orders.isEmpty()) {
            for (Order order : orders) {
                OrderDetail orderDetail = order.getOrderDetail();
                if (Objects.nonNull(orderDetail)) {
                    orderDetail.setCustomerTelephone(telephone);
                }
            }
        }
    }

    protected ProfileUserDTO changeUserFirstName(String firstName, UUID userCustomerUuid) {
        User user = userRepository.findByUuid(userCustomerUuid);
        if (Objects.nonNull(user)) {
            user.setFirstName(firstName);
            return profileConverter.convertObjectToDTO(user);
        } else {
            throw new UserNotFoundException(String.format(
                    ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE, User.class, userCustomerUuid));
        }
    }

    protected void changeOrderCustomerIdentifier(String firstName, UUID userCustomerUuid) {
        List<Order> orders = orderRepository.findByCustomerUuid(userCustomerUuid);
        if (!orders.isEmpty()) {
            for (Order order : orders) {
                OrderDetail orderDetail = order.getOrderDetail();
                if (Objects.nonNull(orderDetail)) {
                    orderDetail.setCustomerIdentifier(firstName);
                }
            }
        }
    }
}
