package com.gmail.portnova.julia.web.controller.web;

import com.gmail.portnova.julia.service.*;
import com.gmail.portnova.julia.service.model.*;
import com.gmail.portnova.julia.web.validator.FormOrderWithTelephoneValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final ItemCustomerService itemCustomerService;
    private final ProfileService profileService;
    private final FormOrderWithTelephoneValidator formOrderWithTelephoneValidator;
    private final OrderService orderService;
    private final OrderPageService orderPageService;
    private final OrderSaleService orderSaleService;
    private final OrderStatusService orderStatusService;

    @GetMapping("/customer/order/{id}")
    public String getOrderFormPage(@PathVariable String id,
                                   Model model,
                                   @ModelAttribute("formOrder") FormOrderDTO formOrder) {
        ItemCustomerDTO item = itemCustomerService.findCustomerItemByUuid(id);
        model.addAttribute("item", item);
        ProfileUserDTO profile = getCurrentUser();
        String telephone = profile.getTelephone();
        model.addAttribute("telephone", telephone);
        return "order_form";
    }

    @PostMapping("/customer/order/{id}")
    public String addOrder(@PathVariable String id,
                           @ModelAttribute("formOrder") FormOrderDTO formOrder,
                           BindingResult result) {
        ProfileUserDTO currentUser = getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            if (Objects.nonNull(currentUser.getTelephone())) {
                formOrder.setCustomerTel(currentUser.getTelephone());
            }
        }
        formOrderWithTelephoneValidator.validate(formOrder, result);
        if (result.hasErrors()) {
            return "order_form";
        } else {
            formOrder.setItemUuid(id);
            formOrder.setCustomerUuid(currentUser.getUuid());
            orderService.addOrderToDatabase(formOrder);
            return "success_order_page";
        }
    }

    @GetMapping("/customer/orders")
    public String getCustomerOrderPage(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "maxResult", defaultValue = "10") int maxResult,
            Model model) {
        ProfileUserDTO currentUser = getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            PageDTO<OrderDTO> page = orderPageService.getCustomerUserOrderPage(pageNumber, maxResult, currentUser.getUuid());
            model.addAttribute("page", page);
            model.addAttribute("currentPage", pageNumber);
            return "customer_orders";
        } else {
            return "redirect:/403";
        }
    }

    @GetMapping("/sale/orders")
    public String getSaleOrdersPage(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "maxResult", defaultValue = "10") int maxResult,
            Model model) {
        ProfileUserDTO currentUser = getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            PageDTO<OrderDTO> page = orderPageService.getSaleUserOrderPage(pageNumber, maxResult, currentUser.getUuid());
            model.addAttribute("page", page);
            model.addAttribute("currentPage", pageNumber);
            return "sale_orders";
        } else {
            return "redirect:/403";
        }
    }

    @GetMapping("sale/orders/{id}")
    public String getSaleOrderDetailPage(@PathVariable("id") String orderUuid,
                                         Model model) {
        OrderSaleDTO orderSale = orderSaleService.getOrderByUuid(orderUuid);
        model.addAttribute("order", orderSale);
        List<StatusDTO> statuses = orderStatusService.findAll();
        model.addAttribute("statuses", statuses);
        return "sale_order_detail";
    }

    @PostMapping("sale/orders/{id}")
    public String changeOrderStatus(@PathVariable("id") String orderUuid,
                                    @RequestParam(name = "newStatusID", required = false) Long newStatusId) {
        if (Objects.nonNull(newStatusId)) {
            orderService.changeOrderStatus(orderUuid, newStatusId);
        }
        return "redirect:/sale/orders/{id}";
    }


    private ProfileUserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return profileService.getUserProfileByEmail(username);
    }
}
