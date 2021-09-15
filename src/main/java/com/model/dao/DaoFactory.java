package com.model.dao;

import com.model.dao.impl.JDBCDaoFactory;

/**
 * Factory that return dao and connection to use with dao
 */
public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }

    public abstract UserDao createUserDao(DaoConnection connection);

    public abstract ActivityDao createActivityDao(DaoConnection connection);

    public abstract ActivityRequestDao createActivityRequestDao(DaoConnection connection);

    public abstract DaoConnection getConnection();
}
