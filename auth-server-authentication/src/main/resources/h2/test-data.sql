-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------
insert into oauth_access_token values('1','','authid','ynding','orderApp','','refreshToken');

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
insert into oauth_client_details values('admin','','$2a$10$gpzdDca0wu6zDEaPXdnh7.GIMWwRnLvrxabNVqrbKKWWtBihznBXG','read,write','password,authorization_code,refresh_token',null,null,3600,3600,null,null);
insert into oauth_client_details values('orderApp','gateway-server,order-server,admin-server','$2a$10$gpzdDca0wu6zDEaPXdnh7.GIMWwRnLvrxabNVqrbKKWWtBihznBXG','read,write','password,authorization_code,refresh_token',null,null,3600,3600,null,null);
insert into oauth_client_details values('orderService','order-server','$2a$10$gpzdDca0wu6zDEaPXdnh7.GIMWwRnLvrxabNVqrbKKWWtBihznBXG','read,write','password',null,null,3600,null,null,null);
insert into oauth_client_details values('gateway','','$2a$10$gpzdDca0wu6zDEaPXdnh7.GIMWwRnLvrxabNVqrbKKWWtBihznBXG','read,write','password',null,null,3600,null,null,null);


-- ----------------------------
-- Records of user
-- ----------------------------
insert into `user` values ('3', '系统管理员', '18568887789', '029-82881234', '深圳南山', '1', 'admin', '$2a$10$qHoUo2ZFcyuVxr2nC0czuOdIGEFvecWCRKWvNz4O7NmUSB4ySUVqa', 'http://bpic.588ku.com/element_pic/01/40/00/64573ce2edc0728.jpg', null);
INSERT INTO `user` VALUES ('5', '丁', '18568123489', '029-82123434', '海口美兰', '1', 'ynding', '$2a$10$qHoUo2ZFcyuVxr2nC0czuOdIGEFvecWCRKWvNz4O7NmUSB4ySUVqa', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514093920321&di=913e88c23f382933ef430024afd9128a&imgtype=0&src=http%3A%2F%2Fp.3761.com%2Fpic%2F9771429316733.jpg', null);

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'ROLE_MANAGER', '部门经理');
INSERT INTO `role` VALUES ('6', 'ROLE_admin', '系统管理员');
INSERT INTO `role` VALUES ('13', 'ROLE_TEST', '测试角色');

-- ----------------------------
-- Records of hr_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '3', '6');
INSERT INTO `user_role` VALUES ('2', '5', '1');
-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '/', null, null, '所有', null, null, null, null, '1');
INSERT INTO `menu` VALUES ('2', '/', '/home', 'Home', '员工资料', 'fa fa-user-circle-o', null, '1', '1', '1');
INSERT INTO `menu` VALUES ('3', '/', '/home', 'Home', '系统管理', 'fa fa-windows', null, '1', '1', '1');
INSERT INTO `menu` VALUES ('4', '/employee/basic/**', '/emp/basic', 'EmpBasic', '基本资料', null, null, '1', '2', '1');
INSERT INTO `menu` VALUES ('5', '/system/cfg/**', '/sys/cfg', 'SysCfg', '系统管理', null, null, '1', '3', '1');
INSERT INTO `menu` VALUES ('6', '/system/init/**', '/sys/init', 'SysInit', '初始化数据库', null, null, '1', '3', '1');
INSERT INTO `menu` VALUES ('7', '/book/**', '/book', 'book', '书本', null, null, '1', '4', '1');
INSERT INTO `menu` VALUES ('8', '/config/user', '/user', 'user', '当前用户', null, null, '1', '4', '1');

-- ----------------------------
-- Records of menu_role
-- ----------------------------
INSERT INTO `menu_role` VALUES ('162', '1', '6');
INSERT INTO `menu_role` VALUES ('163', '2', '6');
INSERT INTO `menu_role` VALUES ('164', '3', '6');
INSERT INTO `menu_role` VALUES ('165', '4', '6');
INSERT INTO `menu_role` VALUES ('166', '5', '6');
INSERT INTO `menu_role` VALUES ('167', '6', '6');
INSERT INTO `menu_role` VALUES ('168', '7', '6');
INSERT INTO `menu_role` VALUES ('169', '8', '6');
INSERT INTO `menu_role` VALUES ('256', '1', '1');
INSERT INTO `menu_role` VALUES ('257', '2', '1');
INSERT INTO `menu_role` VALUES ('258', '3', '1');
INSERT INTO `menu_role` VALUES ('259', '4', '1');
INSERT INTO `menu_role` VALUES ('260', '5', '1');
INSERT INTO `menu_role` VALUES ('261', '6', '1');
INSERT INTO `menu_role` VALUES ('262', '7', '1');
INSERT INTO `menu_role` VALUES ('263', '8', '1');
