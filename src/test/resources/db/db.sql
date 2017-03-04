drop DATABASE IF EXISTS `restbesttest`;
CREATE DATABASE IF NOT EXISTS `restbesttest`;
use `restbesttest`;

drop TABLE IF EXISTS `product`;

CREATE table product (
  id varchar(36) primary key not null,
  name varchar(128) not null,
  brand varchar(128) not null,
  title varchar(128) not null,
  price DECIMAL(13,2) not null,
  created_by varchar(128),
  updated_by varchar(128),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE =InnoDB DEFAULT CHARSET=utf8;