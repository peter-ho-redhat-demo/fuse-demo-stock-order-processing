CREATE DATABASE mydatabase;

CREATE TABLE customer_info(  
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    clientNr VARCHAR(255) COMMENT 'Client Number'
    clientMembership VARCHAR(255) COMMENT 'Client Membership Level'
) DEFAULT CHARSET UTF8;

INSERT INTO customer_info (clientNr, clientMembership) VALUES ('A9', 'GOLD');
INSERT INTO customer_info (clientNr, clientMembership) VALUES ('B2', 'SILVER');
INSERT INTO customer_info (clientNr, clientMembership) VALUES ('A4', 'NORMAL');