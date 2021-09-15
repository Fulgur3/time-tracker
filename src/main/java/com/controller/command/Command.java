package com.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Command interface that provide method to return page name
 *
 */
public interface Command {
    /**
     * @param request User http request to server
     * @return name of page or redirect to servlet
     */
    String execute(HttpServletRequest request);
}
