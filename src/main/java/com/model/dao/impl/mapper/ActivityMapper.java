package com.model.dao.impl.mapper;

import com.model.entity.Activity;
import com.model.entity.ActivityPriority;
import com.model.entity.ActivityStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Map;

public class ActivityMapper implements ObjectMapper<Activity> {
    @Override
    public Activity extractFromResultSet(ResultSet rs) throws SQLException {
        Activity activity = new Activity();
        activity.setId(rs.getLong("activities.id"));
        activity.setName(rs.getString("activities.name"));
        activity.setDescription(rs.getString("activities.description"));

        Timestamp dbTimestamp = rs.getTimestamp("activities.startTime");
        activity.setStartTime(dbTimestamp != null ? dbTimestamp.toLocalDateTime() : null);

        dbTimestamp = rs.getTimestamp("activities.endTime");
        activity.setEndTime(dbTimestamp != null ? dbTimestamp.toLocalDateTime() : null);

        activity.setDuration(Duration.ofSeconds(rs.getLong("activities.duration")));

        String importanceStr = rs.getString("activities.importance");
        activity.setPriority(importanceStr != null ? ActivityPriority.valueOf(importanceStr) : null);

        String statusStr = rs.getString("activities.status");
        activity.setStatus(statusStr != null ? ActivityStatus.valueOf(statusStr) : null);

        return activity;
    }

    @Override
    public Activity makeUnique(Map<Long, Activity> cache, Activity activity) {
        cache.putIfAbsent(activity.getId(), activity);
        return cache.get(activity.getId());
    }
}
