
CREATE TABLE movie_Details (
    recordID INTEGER PRIMARY KEY AUTO_INCREMENT,
    userSessionID VARCHAR(100),
    movieId INTEGER,
    movieName VARCHAR(50),
    movieOccupation INTEGER,
    guestName VARCHAR(50),
    seatChosen INTEGER,
    movieTime VARCHAR(10),
    movieDate VARCHAR(20),
    moviePrice DECIMAL(7,2),
    seatLeft INTEGER
);

create table SEC_USER
(
  userId            BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userName          VARCHAR(36) NOT NULL UNIQUE,
  encryptedPassword VARCHAR(128) NOT NULL,
  ENABLED           BIT NOT NULL 
) ;


create table SEC_ROLE
(
  roleId   BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  roleName VARCHAR(30) NOT NULL UNIQUE
) ;


create table USER_ROLE
(
  ID      BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userId BIGINT NOT NULL,
  roleId BIGINT NOT NULL
);

alter table USER_ROLE
  add constraint USER_ROLE_UK unique (userId, roleId);

alter table USER_ROLE
  add constraint USER_ROLE_FK1 foreign key (userId)
  references SEC_USER (userId);
 
alter table USER_ROLE
  add constraint USER_ROLE_FK2 foreign key (roleId)
  references SEC_ROLE (roleId);

insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Altaher', '$2a$10$4L1pNUqxE2n7FtUE9mu.EuDaez0DBkCJMzdwg.YlSJpyCWVkh4gte', 1);
 
 
insert into sec_role (roleName)
values ('ROLE_ADMIN');
 
insert into sec_role (roleName)
values ('ROLE_USER');
 
insert into user_role (userId, roleId)
values (1, 1);
 







COMMIT;

