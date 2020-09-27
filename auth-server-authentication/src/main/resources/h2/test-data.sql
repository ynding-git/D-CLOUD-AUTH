
insert into oauth_access_token values('1','token','authid','ynding','orderApp','authentication','refreshToken');

insert into oauth_client_details values('orderApp','order-server,gateway-server','','read,write','password,authorization_code,refresh_token',null,null,3600,3600,null,null);
insert into oauth_client_details values('orderService','order-server','','read,write','password',null,null,3600,null,null,null);
insert into oauth_client_details values('gateway','order-server','','read,write','password',null,null,3600,null,null,null);

