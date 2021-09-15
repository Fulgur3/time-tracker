package com.controller.dto;

import com.controller.command.Command;
import com.model.entity.Activity;
import com.model.entity.ActivityPriority;

/**
 * Data transfer object to move activity data from command to service
 */
public class ActivityDTO {
    private String name;
    private String description;
    private ActivityPriority importance;

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityPriority getImportance() {
        return importance;
    }

    public void setImportance(ActivityPriority importance) {
        this.importance = importance;
    }

    /**
     * Builder for activity dto class
     *
     * @see ActivityDTO
     */
    public static class Builder {
        private String name;
        private String description;
        private ActivityPriority importance;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder importance(ActivityPriority importance) {
            this.importance = importance;
            return this;
        }

        public ActivityDTO build() {
            ActivityDTO activityDTO = new ActivityDTO();
            activityDTO.setName(name);
            activityDTO.setDescription(description);
            activityDTO.setImportance(importance);
            return activityDTO;
        }
    }
}
