package com.gmail.portnova.julia.web.controller.api;

import com.gmail.portnova.julia.service.model.RoleNameEnumDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import com.gmail.portnova.julia.web.controller.api.config.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserApiControllerIntegrationTest extends BaseIT {
    @Sql({"/scripts/cleanFeedback.sql",
            "/scripts/cleanUserDetail.sql",
            "/scripts/cleanOpinion.sql",
            "/scripts/cleanArticle.sql",
            "/scripts/cleanItemUser.sql",
            "/scripts/cleanOrderDetail.sql",
            "/scripts/cleanOrder.sql",
            "/scripts/cleanUser.sql",
            "/scripts/cleanRole.sql",
            "/scripts/initRole.sql",
            "/scripts/initUser.sql",
            "/scripts/initArticle.sql"})
    @Test
    void shouldAddUser() {
        UserDTO userDTO = new UserDTO();
        String lastName = "lastname";
        userDTO.setLastName(lastName);
        String firstName = "firstname";
        userDTO.setFirstName(firstName);
        String middleName = "middlename";
        userDTO.setMiddleName(middleName);
        String email = "test@test.com";
        userDTO.setEmail(email);
        String roleName = RoleNameEnumDTO.SALE_USER.name();
        userDTO.setRoleName(roleName);

        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, new HttpHeaders());

        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("rest@myhost.com", "qwer")
                .exchange(
                        "/api/users",
                        HttpMethod.POST,
                        request,
                        String.class
                );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}