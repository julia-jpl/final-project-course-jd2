package com.gmail.portnova.julia.web.controller.web;

import com.gmail.portnova.julia.service.ProfileService;
import com.gmail.portnova.julia.service.UserAddService;
import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.model.ProfileUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.gmail.portnova.julia.web.constant.ValidationConstant.*;

@RequiredArgsConstructor
@Controller
public class ProfileController {
    private final UserAddService userAddService;
    private final ProfileService profileService;
    private final UserService userService;

    @GetMapping("/profile/{uuid}")
    public String getUserProfile(@PathVariable("uuid") String uuid, Model model,
                                 @ModelAttribute("newProfile") ProfileUserDTO editedProfile) {
        ProfileUserDTO profile = profileService.getUserProfile(uuid);
        model.addAttribute("userProfile", profile);
        return "profile";
    }

    @PostMapping("/profile/change-password/{uuid}")
    public String changePassword(@PathVariable("uuid") String uuid,
                                 @RequestParam(name = "changePassword", defaultValue = "false") boolean changePassword) {
        if (changePassword) {
            userAddService.changePassword(uuid);
            return "redirect:/profile/{uuid}";
        }
        return "redirect:/profile/{uuid}";
    }

    @PostMapping("/profile/change-last-name/{uuid}")
    public String changeLastName(@PathVariable("uuid") String uuid,
                                 @RequestParam(name = "lastName", required = false) String lastName) {
        if (!lastName.isEmpty() && lastName.matches(LAST_AND_MIDDLE_NAME_PATTERN)) {
            userService.changeUserLastname(lastName, uuid);
            return "redirect:/profile/{uuid}";
        }
        return "redirect:/profile/{uuid}";
    }

    @PostMapping("profile/change-first-name/{uuid}")
    public String changeFirstName(@PathVariable("uuid") String uuid,
                                 @RequestParam(name = "firstName", required = false) String firstName) {
        if (!firstName.isEmpty() && firstName.matches(FIRST_NAME_PATTERN)) {
            userService.changeUserFirstName(firstName, uuid);
            return "redirect:/profile/{uuid}";
        }
        return "redirect:/profile/{uuid}";
    }

    @PostMapping("profile/change-address/{uuid}")
    public String changeAddress(@PathVariable("uuid") String uuid,
                                 @RequestParam(name = "address", required = false) String address) {
        if (!address.isEmpty() && (address.length() < ADDRESS_MAX_LENGTH_VALUE) && (address.length() > ADDRESS_MIN_LENGTH_VALUE)) {
            userService.changeUserAddress(address, uuid);
            return "redirect:/profile/{uuid}";
        }
        return "redirect:/profile/{uuid}";
    }

    @PostMapping("profile/change-telephone/{uuid}")
    public String changeTelephone(@PathVariable("uuid") String uuid,
                                 @RequestParam(name = "telephone", required = false) String telephone) {
        if (!telephone.isEmpty() && telephone.matches(TELEPHONE_PATTERN)) {
            userService.changeUserTelephone(telephone, uuid);
            return "redirect:/profile/{uuid}";
        }
        return "redirect:/profile/{uuid}";
    }
}
