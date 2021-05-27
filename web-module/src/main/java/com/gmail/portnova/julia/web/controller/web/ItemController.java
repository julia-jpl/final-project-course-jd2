package com.gmail.portnova.julia.web.controller.web;

import com.gmail.portnova.julia.service.ItemSaleService;
import com.gmail.portnova.julia.service.ItemService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.gmail.portnova.julia.web.constant.RoleNameConstant.ROLE_NAME_CUSTOMER_USER;
import static com.gmail.portnova.julia.web.constant.RoleNameConstant.ROLE_NAME_SALE_USER;

@Controller
@RequiredArgsConstructor

public class ItemController {
    private final ItemService itemService;
    private final UserService userService;
    private final ItemSaleService itemSaleService;

    @GetMapping("/items")
    public String getItemsPage(@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                               @RequestParam(name = "maxResult", defaultValue = "10") int maxResult,
                               Model model) {
        UserDTO user = getCurrentUser();
        PageDTO<ItemDTO> itemPage = new PageableItem();
        switch (user.getRoleName()) {
            case ROLE_NAME_SALE_USER:
                itemPage = itemService.getSaleUserItemsPage(pageNumber, maxResult, user.getUuid());
                break;
            case ROLE_NAME_CUSTOMER_USER:
                itemPage = itemService.getAllItemsPage(pageNumber, maxResult);
                break;
            default:
                itemPage = null;
        }
        if (Objects.nonNull(itemPage)) {
            model.addAttribute("itemPage", itemPage);
            model.addAttribute("currentPage", pageNumber);
            return "items";
        } else {
            return "redirect:/403";
        }
    }

    @PostMapping("/sale/items/delete/{uuid}")
    public String deleteItemForUserCatalog(@PathVariable String uuid) {
        UserDTO currentUser = getCurrentUser();
        UUID currentUserUuid = currentUser.getUuid();
        itemService.deleteItemFromSaleUserCatalog(uuid, currentUserUuid);
        return "redirect:/items";
    }
    @PostMapping("/sale/items/copy/{uuid}")
    public String copyItem(@PathVariable String uuid) {
        itemService.copyItemTOClipboard(uuid);
        return "redirect:/items";
    }

    @GetMapping("/items/{id}")
    public String getSaleItemPage(@PathVariable String id, Model model) {
        ItemSaleDTO item = itemSaleService.findByUuid(id);
        model.addAttribute("item", item);
        return "item_page";
    }

    private UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return userService.findUserByEmail(username);
    }
}
