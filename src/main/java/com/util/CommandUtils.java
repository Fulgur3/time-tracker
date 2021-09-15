package com.util;

import com.controller.filter.AuthFilter;
import com.controller.filter.LocalizationFilter;
import com.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Util class for command functions
 */
public class CommandUtils {
    /**
     * Get locale that is set in session by {@code LocalizationFilter}
     *
     * @param request User http request to server
     * @return current session locale
     * @see LocalizationFilter
     */
    public static Locale getLocaleFromSession(HttpServletRequest request) {
        String lang = (String) request.getSession().getAttribute("lang");
        return lang != null ? Locale.forLanguageTag(lang) : Locale.getDefault();
    }

    /**
     * Get user that is set in session by {@code AuthFilter}
     *
     * @param request User http request to server
     * @return current user in session
     * @see AuthFilter
     */
    public static User getUserFromSession(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("authUser");
    }
}
