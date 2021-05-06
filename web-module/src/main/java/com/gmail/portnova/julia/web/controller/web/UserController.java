package com.gmail.portnova.julia.web.controller.web;

import com.gmail.portnova.julia.service.RoleService;
import com.gmail.portnova.julia.service.UserAddService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.model.PageDTO;
import com.gmail.portnova.julia.service.model.RoleDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import com.gmail.portnova.julia.web.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;
@RequiredArgsConstructor
@Controller
public class UserController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;
    private final UserAddService userAddService;
    private final RoleService roleService;
    private final UserValidator userValidator;
    private final Integer maxResult = 10;

    @GetMapping("/users/all")
    public String getAllUsers(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                              @RequestParam(name = "maxResult", defaultValue = "10") Integer maxResult,
                              Authentication authentication,
                              Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (Objects.nonNull(userDetails)) {
            String email = userDetails.getUsername();
            PageDTO<UserDTO> page = userService.getUsersPage(email, pageNumber, maxResult);
            model.addAttribute("page", page);
            model.addAttribute("currentPage", pageNumber);
            return "all_users";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/users/add")
    public String getAddUserPage(@ModelAttribute("newUser") UserDTO user,
                                 Model model) {
        List<RoleDTO> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        return "add_user";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute("newUser") UserDTO user,
                          BindingResult results) {
        userValidator.validate(user, results);
        if (results.hasErrors()) {
            return "add_user";
        } else {

            userAddService.addUser(user);
            return "redirect:/users/all";
        }
    }

    @PostMapping("/users/all")
    public String deleteUser(@RequestParam(name = "userIds", required = false) List<String> userIds) {
        if (Objects.nonNull(userIds)) {
            for (String id : userIds) {
                userService.deleteByUUID(id);
            }
        }
        return "redirect:/users/all";
    }

    @GetMapping("/users/change-password/{id}")
    public String changePassword(@PathVariable String id, Model model) {
        UserDTO user = userAddService.changePassword(id);
        model.addAttribute("user", user);
        return "change_password";
    }

    @GetMapping("/users/change-role/{id}")
    public String getChangeUserRolePage(@PathVariable String id, Model model) {
        UserDTO user = userService.findByUuid(id);
        model.addAttribute("userToChangeRole", user);
        List<RoleDTO> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        return "change_role";
    }

    @PostMapping("/users/change-role/{id}")
    public String changeUserRole(@PathVariable String id,
                                 @ModelAttribute("newRole") String newRole,
                                 Model model) {
        UserDTO user = userService.changeUserRole(id, newRole);
        if (Objects.nonNull(user)) {
            model.addAttribute("isRoleChanged", true);
            return "redirect:/users/change-role/{id}";
        } else {
            model.addAttribute("isRoleChanged", false);
            return "change_role";
        }
    }

    @GetMapping("/403")
    public String getDeniedPage() {
        return "denied_page";
    }
}
