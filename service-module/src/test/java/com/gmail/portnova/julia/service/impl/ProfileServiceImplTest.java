package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.repository.model.UserDetail;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.UserNotFoundException;
import com.gmail.portnova.julia.service.model.ProfileUserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private GeneralConverter<User, ProfileUserDTO> profileConverter;
    @InjectMocks
    private ProfileServiceImpl profileService;

    @Test
    void shouldGetUserProfile() {
        UUID uuid = UUID.randomUUID();
        User user = new User();
        user.setUuid(uuid);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        ProfileUserDTO profileUserDTO = new ProfileUserDTO();
        profileUserDTO.setUuid(uuid);
        when(profileConverter.convertObjectToDTO(user)).thenReturn(profileUserDTO);

        ProfileUserDTO resultProfile = profileService.getUserProfile(uuid);
        assertEquals(uuid, resultProfile.getUuid());
    }

    @Test
    void shouldThrowExceptionWhenWeTryToGetUserProfile() {
        UUID uuid = UUID.randomUUID();
        when(userRepository.findByUuid(uuid)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> profileService.getUserProfile(uuid));
    }

    @Test
    void shouldChangeUserTelephone() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        User user = new User();
        user.setUuid(uuid);

        UserDetail userDetail = new UserDetail();
        user.setUserDetail(userDetail);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        String newTelephone = "1234";
        userDetail.setTelephone(newTelephone);

        ProfileUserDTO profileUserDTO = new ProfileUserDTO();
        profileUserDTO.setUuid(uuid);
        profileUserDTO.setTelephone(newTelephone);
        when(profileConverter.convertObjectToDTO(user)).thenReturn(profileUserDTO);

        ProfileUserDTO resultProfileUser = profileService.changeUserTelephone(newTelephone, id);
        assertEquals(newTelephone, resultProfileUser.getTelephone());
    }

    @Test
    void shouldAddUserTelephone() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        User user = new User();
        user.setUuid(uuid);

        when(userRepository.findByUuid(uuid)).thenReturn(user);

        UserDetail userDetail = new UserDetail();
        String newTelephone = "1234";
        userDetail.setTelephone(newTelephone);
        user.setUserDetail(userDetail);
        userDetail.setUser(user);

        ProfileUserDTO profileUserDTO = new ProfileUserDTO();
        profileUserDTO.setUuid(uuid);
        profileUserDTO.setTelephone(newTelephone);
        when(profileConverter.convertObjectToDTO(user)).thenReturn(profileUserDTO);

        ProfileUserDTO resultProfileUser = profileService.changeUserTelephone(newTelephone, id);
        assertEquals(newTelephone, resultProfileUser.getTelephone());
    }

    @Test
    void shouldNotChangeUserTelephoneWhenUserWasNotFound() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        String newTelephone = "1234";
        when(userRepository.findByUuid(uuid)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> profileService.changeUserTelephone(newTelephone, id));
    }

    @Test
    void shouldChangeUserAddress() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        User user = new User();
        user.setUuid(uuid);

        UserDetail userDetail = new UserDetail();
        user.setUserDetail(userDetail);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        String newAddress = "address";
        userDetail.setAddress(newAddress);

        ProfileUserDTO profileUserDTO = new ProfileUserDTO();
        profileUserDTO.setUuid(uuid);
        profileUserDTO.setAddress(newAddress);
        when(profileConverter.convertObjectToDTO(user)).thenReturn(profileUserDTO);

        ProfileUserDTO resultProfileUser = profileService.changeUserAddress(newAddress, id);
        assertEquals(newAddress, resultProfileUser.getAddress());
    }

    @Test
    void shouldAddUserAddress() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        User user = new User();
        user.setUuid(uuid);

        when(userRepository.findByUuid(uuid)).thenReturn(user);

        UserDetail userDetail = new UserDetail();
        String newAddress = "address";
        userDetail.setAddress(newAddress);
        user.setUserDetail(userDetail);
        userDetail.setUser(user);

        ProfileUserDTO profileUserDTO = new ProfileUserDTO();
        profileUserDTO.setUuid(uuid);
        profileUserDTO.setAddress(newAddress);
        when(profileConverter.convertObjectToDTO(user)).thenReturn(profileUserDTO);

        ProfileUserDTO resultProfileUser = profileService.changeUserAddress(newAddress, id);
        assertEquals(newAddress, resultProfileUser.getAddress());
    }

    @Test
    void shouldNotChangeUserAddressWhenUserWasNotFound() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        String newAddress = "address";
        when(userRepository.findByUuid(uuid)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> profileService.changeUserAddress(newAddress, id));
    }

}