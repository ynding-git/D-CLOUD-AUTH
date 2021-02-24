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

