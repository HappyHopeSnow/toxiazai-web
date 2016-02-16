create table user
(
   id                  bigint not null AUTO_INCREMENT,
   user_name  varchar(255) default '',
   primary key (id)
)ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

insert  into `user`(`id`,`user_name`) values ('1','lianle')