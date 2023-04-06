CREATE TABLE `user_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(64) NOT NULL DEFAULT '' COMMENT '创建记录 调用方标识',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_user` varchar(64) NOT NULL DEFAULT '' COMMENT '更新记录 调用方标识',
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  `is_del` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',

  `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户id',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
  `nickname` varchar(20) NOT NULL DEFAULT '' COMMENT '昵称',
  `gender` tinyint(4) NOT NULL DEFAULT 0 COMMENT '性别，0-未知，1-男，2-女',
  `email` varchar(255) NOT NULL DEFAULT '' COMMENT '邮箱',

  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_uk` (`user_id`)
  KEY `email_idx` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';