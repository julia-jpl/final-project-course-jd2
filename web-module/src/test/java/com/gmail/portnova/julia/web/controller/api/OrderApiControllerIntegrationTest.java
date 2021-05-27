package com.gmail.portnova.julia.web.controller.api;

import com.gmail.portnova.julia.service.model.*;
import com.gmail.portnova.julia.web.controller.api.config.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderApiControllerIntegrationTest extends BaseIT {
    @Sql({"/scripts/cleanFeedback.sql",
            "/scripts/cleanUserDetail.sql",
            "/scripts/cleanOpinion.sql",
            "/scripts/cleanArticle.sql",
            "/scripts/cleanItemUser.sql",
            "/scripts/cleanOrderDetail.sql",
            "/scripts/cleanOrder.sql",
            "/scripts/cleanOrderStatus.sql",
            "/scripts/cleanUser.sql",
            "/scripts/cleanRole.sql",
            "/scripts/initRole.sql",
            "/scripts/initUser.sql",
            "/scripts/cleanItemDetail.sql",
            "/scripts/cleanItem.sql",
            "/scripts/cleanItemGroup.sql",
            "/scripts/initItemGroup.sql",
            "/scripts/initItem.sql",
            "/scripts/initOrderStatus.sql",
            "/scripts/initOrder.sql",
            "/scripts/initOrderDetail.sql",
    })
    @Test
    void shouldGetAllOrders() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        String orderNumber = "22222222222";

        ResponseEntity<List<OrderDTO>> response = testRestTemplate
                .withBasicAuth("rest@myhost.com", "qwer")
                .exchange(
                        "/api/orders",
                        HttpMethod.GET,
                        request,
                        createParameterizedTypeReferenceListOrderDTO()
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderNumber, response.getBody().get(0).getNumber());
    }
    protected ParameterizedTypeReference<List<OrderDTO>> createParameterizedTypeReferenceListOrderDTO() {
        return new ParameterizedTypeReference<>() {
        };
    }

    @Sql({"/scripts/cleanFeedback.sql",
            "/scripts/cleanUserDetail.sql",
            "/scripts/cleanOpinion.sql",
            "/scripts/cleanArticle.sql",
            "/scripts/cleanItemUser.sql",
            "/scripts/cleanOrderDetail.sql",
            "/scripts/cleanOrder.sql",
            "/scripts/cleanOrderStatus.sql",
            "/scripts/cleanUser.sql",
            "/scripts/cleanRole.sql",
            "/scripts/initRole.sql",
            "/scripts/initUser.sql",
            "/scripts/cleanItemDetail.sql",
            "/scripts/cleanItem.sql",
            "/scripts/cleanItemGroup.sql",
            "/scripts/initItemGroup.sql",
            "/scripts/initItem.sql",
            "/scripts/initOrderStatus.sql",
            "/scripts/initOrder.sql",
            "/scripts/initOrderDetail.sql",
    })
    @Test
    void shouldGetOrderByUuid() {
        String uuid = "3422b448-2900-4fd2-9183-8000de6f8343";
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<OrderApiDTO> response = testRestTemplate
                .withBasicAuth("rest@myhost.com", "qwer")
                .exchange(
                        "/api/orders/" + uuid,
                        HttpMethod.GET,
                        request,
                        createParameterizedTypeReferenceOrderApiDTO()
                );


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(uuid, response.getBody().getOrderUuid().toString());
    }

    protected ParameterizedTypeReference<OrderApiDTO> createParameterizedTypeReferenceOrderApiDTO() {
        return new ParameterizedTypeReference<>() {
        };
    }

}