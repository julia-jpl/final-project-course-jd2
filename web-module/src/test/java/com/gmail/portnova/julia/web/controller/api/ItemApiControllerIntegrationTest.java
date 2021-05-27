package com.gmail.portnova.julia.web.controller.api;

import com.gmail.portnova.julia.service.model.ItemApiDTO;
import com.gmail.portnova.julia.service.model.ItemDTO;
import com.gmail.portnova.julia.service.model.ItemGroupNameEnumDTO;
import com.gmail.portnova.julia.web.controller.api.config.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemApiControllerIntegrationTest extends BaseIT {

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
            "/scripts/cleanItemDetail.sql",
            "/scripts/cleanItem.sql",
            "/scripts/cleanItemGroup.sql",
            "/scripts/initItemGroup.sql",
            "/scripts/initItem.sql"})
    @Test
    void shouldGetAllItems() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<List<ItemDTO>> response = testRestTemplate
                .withBasicAuth("rest@myhost.com", "qwer")
                .exchange(
                        "/api/items",
                        HttpMethod.GET,
                        request,
                        createParameterizedTypeReferenceListItemDto()
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("shampoo", response.getBody().get(0).getName());
    }

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
            "/scripts/cleanItemDetail.sql",
            "/scripts/cleanItem.sql",
            "/scripts/cleanItemGroup.sql",
            "/scripts/initItemGroup.sql",
            "/scripts/initItem.sql"})
    @Test
    void shouldGetItemByUuid() {
        String uuid = "3123b448-2900-4fd2-9183-6780de6f8343";
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<ItemApiDTO> response = testRestTemplate
                .withBasicAuth("rest@myhost.com", "qwer")
                .exchange(
                        "/api/items/" + uuid,
                        HttpMethod.GET,
                        request,
                        createParameterizedTypeReferenceItemApiDto()
                );


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(uuid, response.getBody().getUuid().toString());
    }

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
            "/scripts/cleanItemDetail.sql",
            "/scripts/cleanItem.sql",
            "/scripts/cleanItemGroup.sql",
            "/scripts/initItemGroup.sql"})
    @Test
    void shouldAddItemWithPriceRelatedToSaleUser() {
        ItemApiDTO itemApiDTO = new ItemApiDTO();
        String name = "name";
        itemApiDTO.setName(name);
        BigDecimal price = new BigDecimal("99.99");
        itemApiDTO.setPrice(price);
        String description = "description";
        itemApiDTO.setDescription(description);
        String itemGroup = ItemGroupNameEnumDTO.HEALTH_BEAUTY.name();
        itemApiDTO.setItemGroup(itemGroup);
        String userUuid = "3422b448-2900-4fd2-9183-8770de6f8343";
        List<String> userUuids = Collections.singletonList(userUuid);
        itemApiDTO.setUserUuids(userUuids);

        HttpEntity<ItemApiDTO> request = new HttpEntity<>(itemApiDTO, new HttpHeaders());

        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("rest@myhost.com", "qwer")
                .exchange(
                        "/api/items",
                        HttpMethod.POST,
                        request,
                        String.class
                );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

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
            "/scripts/cleanItemDetail.sql",
            "/scripts/cleanItem.sql",
            "/scripts/cleanItemGroup.sql",
            "/scripts/initItemGroup.sql",
            "/scripts/initItem.sql"})
    @Test
    void shouldDeleteItemByUuid() {

        String uuid = "3123b448-2900-4fd2-9183-6780de6f8343";
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("rest@myhost.com", "qwer")
                .exchange(
                        "/api/items/" + uuid,
                        HttpMethod.DELETE,
                        request,
                        String.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    protected ParameterizedTypeReference<List<ItemDTO>> createParameterizedTypeReferenceListItemDto() {
        return new ParameterizedTypeReference<>() {
        };
    }

    protected ParameterizedTypeReference<ItemApiDTO> createParameterizedTypeReferenceItemApiDto() {
        return new ParameterizedTypeReference<>() {
        };
    }

}