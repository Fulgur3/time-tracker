package com.model.dao.impl.mapper;

import com.model.entity.ActivityRequest;
import com.model.entity.ActivityRequestAction;
import com.model.entity.ActivityRequestStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

public class ActivityRequestMapper implements ObjectMapper<ActivityRequest> {
    @Override
    public ActivityRequest extractFromResultSet(ResultSet rs) throws SQLException {
        ActivityRequest activityRequest = new ActivityRequest();
        activityRequest.setId(rs.getLong("activityRequests.id"));

        Timestamp dbTimestamp = rs.getTimestamp("activityRequests.requestDate");
        activityRequest.setRequestDate(dbTimestamp != null ? dbTimestamp.toLocalDateTime() : null);

        String actionStr = rs.getString("activityRequests.action");
        activityRequest.setAction(actionStr != null ? ActivityRequestAction.valueOf(actionStr) : null);

        String statusStr = rs.getString("activityRequests.status");
        activityRequest.setStatus(statusStr != null ? ActivityRequestStatus.valueOf(statusStr) : null);

        return activityRequest;
    }

    @Override
    public ActivityRequest makeUnique(Map<Long, ActivityRequest> cache, ActivityRequest activityRequest) {
        cache.putIfAbsent(activityRequest.getId(), activityRequest);
        return cache.get(activityRequest.getId());
    }
}
