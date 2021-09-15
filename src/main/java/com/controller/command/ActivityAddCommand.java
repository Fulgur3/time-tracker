package com.controller.command;

import com.controller.dto.ActivityDTO;
import com.util.validator.*;
import com.model.entity.Activity;
import com.model.entity.ActivityPriority;
import com.model.entity.ActivityStatus;
import com.model.service.ActivityService;
import com.util.CommandUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Command that response to user requests and add activity to database using activity service
 */
public class ActivityAddCommand implements Command {
    public static final Logger log = LogManager.getLogger();
    private final ActivityService activityService;
    private ResourceBundle rb;

    ActivityAddCommand(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * @param request User http request to server
     * @return name of page or redirect
     */
    @Override
    public String execute(HttpServletRequest request) {
        rb = ResourceBundle.getBundle("i18n.messages", CommandUtils.getLocaleFromSession(request));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String importance = request.getParameter("importance");

        if (!ObjectUtils.allNotNull(name, description, importance)) {
            request.setAttribute("importanceLevels", ActivityPriority.values());
            return "/WEB-INF/pages/add-activity.jsp";
        }

        Activity activity = new Activity();
        activity.setName(name);
        activity.setDescription(description);
        activity.setPriority(ActivityPriority.valueOf(importance));
        activity.setStatus(ActivityStatus.PENDING);

        ActivityDTO activityDTO = ActivityDTO.builder()
                .name(name)
                .description(description)
                .importance(ActivityPriority.valueOf(importance))
                .build();

        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(activityDTO);
        if (!validationErrorsMap.isEmpty()) {
            request.setAttribute("activity", activityDTO);
            request.setAttribute("importanceLevels", ActivityPriority.values());
            request.setAttribute("errors", validationErrorsMap);
            return "/WEB-INF/pages/add-activity.jsp";
        }
        activityService.createActivity(activityDTO);
        return "redirect:/activities";
    }

    /**
     * Check activity dto on errors by validator
     *
     * @param activityDTO activity dto filled with user input data
     * @return map with error path in jsp and error messages from validator
     * @see CompositeValidator
     * @see Validator
     * @see Result
     */
    private Map<String, String[]> getValidationErrorsMap(ActivityDTO activityDTO) {
        Map<String, String[]> validationErrorMap = new HashMap<>();
        CompositeValidator<String> activityDescriptionValidator = new CompositeValidator<>(
                new SizeValidator(0, 500, rb.getString("validation.activity.description.size"))
        );
        CompositeValidator<String> activityNameValidator = new CompositeValidator<>(
                new SizeValidator(5, 100, rb.getString("validation.activity.name.size")),
                new NotBlankValidator(rb.getString("validation.activity.name.not_blank")));
        Result result = activityNameValidator.validate(activityDTO.getName());
        if (!result.isValid()) {
            validationErrorMap.put("nameErrors", result.getMessage().split("\n"));
        }
        result = activityDescriptionValidator.validate(activityDTO.getDescription());
        if (!result.isValid()) {
            validationErrorMap.put("descriptionErrors", result.getMessage().split("\n"));
        }
        return validationErrorMap;
    }
}
