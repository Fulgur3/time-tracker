
create database if not exists timemanaging;
USE timemanaging ;


CREATE TABLE IF NOT EXISTS activities (
                                          id INT NOT NULL AUTO_INCREMENT,
                                          name VARCHAR(100) NOT NULL,
                                          description VARCHAR(255) NOT NULL,
                                          StartTime DATETIME NOT NULL,
                                          EndTime DATETIME NOT NULL,
                                          duration TIME NOT NULL,
                                          priority VARCHAR(15) NOT NULL,
                                          importance varchar(30) not null,
                                          status VARCHAR(10) NOT NULL,
                                          PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS users (
                                     id INT NOT NULL AUTO_INCREMENT,
                                     username VARCHAR(50) NOT NULL,
                                     firstName VARCHAR(20) NOT NULL,
                                     lastName VARCHAR(30) NOT NULL,
                                     password BLOB(100) NOT NULL,
                                     PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS user_Authorities(
                                               user_id int references users(id),
                                               authorities VARCHAR(30) not null,
                                               PRIMARY KEY (user_id)
);
CREATE TABLE IF NOT EXISTS users_activities(
                                               user_id int references users(id),
                                               activity_id int references activities(id)
);

CREATE TABLE IF NOT EXISTS activityRequests (
                                                id int not null,
                                                activity_id INT references activities(id),
                                                user_id INT references users(id),
                                                requestDate datetime not null,
                                                action varchar(30) not null,
                                                status varchar(30) not null,
                                                PRIMARY KEY (user_id, activity_id));