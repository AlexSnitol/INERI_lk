package com.ineri.ineri_lk.controller;

import com.ineri.ineri_lk.model.Favorite;
import com.ineri.ineri_lk.model.Role;
import com.ineri.ineri_lk.model.User;
import com.ineri.ineri_lk.service.impl.FavoriteServiceImpl;
import com.ineri.ineri_lk.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

/**
 * @author Kozlov Alexander
 */

@Controller
@RequestMapping("/{username}")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private FavoriteServiceImpl favoriteServiceImpl;

    @Autowired
    private AuthController authController;

    @GetMapping("/profile")
    public ModelAndView showProfile(@PathVariable("username") String username) {
        ModelAndView mv = new ModelAndView("test_view_profile");
        mv = authController.setupUser(mv);
        return mv;
    }

    @GetMapping("/profile/edit")
    public ModelAndView updateUserForm(@PathVariable("username") String username) {
        ModelAndView mv = new ModelAndView("test_edit_user_profile");
        mv.addObject("username", username);
        mv.addObject("user", userServiceImpl.getUserByUsername(username));
        return mv;
    }

    @PostMapping("/profile/edit")
    public String updateUser(User user){
        user.getRoles().add(Role.ROLE_USER);
        userServiceImpl.updateUser(user);
        return "redirect:/" + user.getUsername() + "/profile";
    }

    @GetMapping("/favorites")
    public ModelAndView getUserFavorites(@PathVariable("username") String username) {
        ModelAndView mv = new ModelAndView("view_favorites");
        List<Favorite> favorites= favoriteServiceImpl.getUserFavorites(username);

        mv = authController.setupUser(mv);

        mv.addObject("lightTheme", true);

        mv.addObject("favorites", favorites);

        return mv;
    }

    @GetMapping("/favorites/{advertisedId}/delete")
    public String deleteUserFavorite(
            @PathVariable("username") String username,
            @PathVariable("advertisedId") Long advertisedId)
    {
        User user = userServiceImpl.getUserByUsername(username);
        favoriteServiceImpl.deleteByUserIdAndFavoriteId(user.getId(), advertisedId);
        return "redirect:/" + username + "/favorites";
    }

}
