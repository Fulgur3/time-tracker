package com.controller.command;

import com.model.entity.User;
import com.model.service.UserService;
import com.util.CommandUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Get user profile page with user data on it
 */
public class UserProfileCommand implements Command {
    private final UserService userService;

    UserProfileCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param request User http request to server
     * @return name of page or redirect
     */
    @Override
    public String execute(HttpServletRequest request) {
        User user = CommandUtils.getUserFromSession(request);
        request.setAttribute("user", userService.getUserById(user.getId()));
        return "/WEB-INF/pages/profile.jsp";
    }
}
