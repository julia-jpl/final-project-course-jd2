package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.repository.model.UserDetail;
import com.gmail.portnova.julia.service.model.ProfileUserDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProfileUserConverterImplTest {

    private final ProfileUserConverterImpl profileUserConverter = new ProfileUserConverterImpl();

    @Test
    void shouldConvertUserToProfileUserDTOAndReturnRightId() {
        User user = new User();
        Long id = 1l;
        user.setId(id);
        ProfileUserDTO profileUserDTO = profileUserConverter.convertObjectToDTO(user);
        assertEquals(id, profileUserDTO.getId());
    }

    @Test
    void shouldConvertUserToProfileUserDTOAndReturnRightUuid() {
        User user = new User();
        UUID uuid = UUID.randomUUID();
        user.setUuid(uuid);

        ProfileUserDTO profileUserDTO = profileUserConverter.convertObjectToDTO(user);
        assertEquals(uuid, profileUserDTO.getUuid());
    }

    @Test
    void shouldConvertUserToProfileUserDTOAndReturnRightLastname() {
        User user = new User();
        String lastname = "last";
        user.setLastName(lastname);

        ProfileUserDTO profileUserDTO = profileUserConverter.convertObjectToDTO(user);
        assertEquals(lastname, profileUserDTO.getLastName());
    }

    @Test
    void shouldConvertUserToProfileUserDTOAndReturnRightFirstname() {
        User user = new User();
        String firstname = "first";
        user.setFirstName(firstname);

        ProfileUserDTO profileUserDTO = profileUserConverter.convertObjectToDTO(user);
        assertEquals(firstname, profileUserDTO.getFirstName());
    }

    @Test
    void shouldConvertUserToProfileUserDTOAndReturnRightAddressAndTelephone() {
        User user = new User();
        UserDetail userDetail = new UserDetail();
        String address = "address";
        userDetail.setAddress(address);
        String telephone = "telephone";
        userDetail.setTelephone(telephone);
        user.setUserDetail(userDetail);

        ProfileUserDTO profileUserDTO = profileUserConverter.convertObjectToDTO(user);
        assertEquals(address, profileUserDTO.getAddress());
        assertNotNull(telephone, profileUserDTO.getTelephone());
    }
    @Test
    void shouldConvertUserToProfileUserDTOAndReturnNotNullObject() {
        User user = new User();
        ProfileUserDTO profileUserDTO = profileUserConverter.convertObjectToDTO(user);
        assertNotNull(profileUserDTO);
    }
    @Test
    void shouldConvertUserToProfileUserDTOWhenUserIsNull() {
        ProfileUserDTO profileUserDTO = profileUserConverter.convertObjectToDTO(null);
        assertNull(profileUserDTO);
    }
}