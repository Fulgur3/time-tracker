package com.model.dao;

import com.model.entity.Activity;

import java.util.List;

/**
 * Implementation of generic dao to handle with activity entity in database
 */
public interface ActivityDao extends GenericDao<Activity> {
    List<Activity> findAllPageable(int page, int size);

    long getNumberOfRecords();
}
