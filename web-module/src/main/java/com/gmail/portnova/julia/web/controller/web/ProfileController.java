package com.gmail.portnova.julia.web.controller.web;

import com.gmail.portnova.julia.service.ProfileService;
import com.gmail.portnova.julia.service.UserAddService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.model.ProfileUserDTO;
import com.gmail.portnova.julia.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.UUID;

import static com.gmail.portnova.julia.web.constant.ValidationConstant.*;

@RequiredArgsConstructor
@Controller
public class ProfileController {
    private final UserAddService userAddService;
    private final ProfileService profileService;
    private final UserService userService;

    @GetMapping("/profile")
    public String getUserProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        UserDTO user = userService.findUserByEmail(username);
        if (Objects.nonNull(user)) {
            UUID uuid = user.getUuid();
            ProfileUserDTO profile = profileService.getUserProfile(uuid);
            model.addAttribute("userProfile", profile);
        }
        return "profile";
    }

    @PostMapping("/profile/change-password/{uuid}")
    public String changePassword(@PathVariable("uuid") String uuid,
                                 @RequestParam(name = "changePassword", defaultValue = "false") boolean changePassword) {
        if (changePassword) {
            UserDTO savedUser = userAddService.saveNewPasswordInDatabase(uuid);
            userAddService.sendEmail(savedUser);
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }

    @PostMapping("/profile/change-last-name/{uuid}")
    public String changeLastName(@PathVariable("uuid") String uuid,
                                 @RequestParam(name = "lastName", required = false) String lastName) {
        if (!lastName.isEmpty() && lastName.matches(LAST_AND_MIDDLE_NAME_PATTERN)) {
            profileService.changeUserLastname(lastName, uuid);
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }

    @PostMapping("profile/change-first-name/{uuid}")
    public String changeFirstName(@PathVariable("uuid") String uuid,
                                  @RequestParam(name = "firstName", required = false) String firstName) {
        if (Objects.nonNull(firstName) && !firstName.isEmpty() && firstName.matches(FIRST_NAME_PATTERN)) {
            profileService.changeProfileFirstName(firstName, uuid);
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }

    @PostMapping("profile/change-address/{uuid}")
    public String changeAddress(@PathVariable("uuid") String uuid,
                                @RequestParam(name = "address", required = false) String address) {
        if (!address.isEmpty() && (address.length() < ADDRESS_MAX_LENGTH_VALUE) && (address.length() > ADDRESS_MIN_LENGTH_VALUE)) {
            profileService.changeUserAddress(address, uuid);
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }

    @PostMapping("profile/change-telephone/{uuid}")
    public String changeTelephone(@PathVariable("uuid") String uuid,
                                  @RequestParam(name = "telephone", required = false) String telephone) {
        if (!telephone.isEmpty() && telephone.matches(TELEPHONE_PATTERN)) {
            profileService.changeProfileTelephone(telephone, uuid);
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }
}
