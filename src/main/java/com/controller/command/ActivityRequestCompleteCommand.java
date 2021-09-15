package com.controller.command;

import com.model.entity.Activity;
import com.model.entity.ActivityRequest;
import com.model.entity.User;
import com.model.service.ActivityRequestService;
import com.util.CommandUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Add activity request from user to complete activity using activity request service
 */
public class ActivityRequestCompleteCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final ActivityRequestService activityRequestService;

    ActivityRequestCompleteCommand(ActivityRequestService activityRequestService) {
        this.activityRequestService = activityRequestService;
    }

    /**
     * @param request User http request to server
     * @return name of page or redirect
     */
    @Override
    public String execute(HttpServletRequest request) {
        User user = CommandUtils.getUserFromSession(request);

        long activityId;
        try {
            activityId = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            log.warn("Can not parse number from request parameter");
            return "/WEB-INF/error/404.jsp";
        }

        activityRequestService.makeCompleteActivityRequest(user.getId(), activityId);
        return "redirect:/activities";
    }
}
