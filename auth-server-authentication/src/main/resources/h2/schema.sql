-- 存客户端应用 --
DROP TABLE IF EXISTS `oauth_client_details`;
create table oauth_client_details
(
    client_id               VARCHAR(256) PRIMARY KEY,
    resource_ids            VARCHAR(256) COMMENT '允许访问的资源id',
    client_secret           VARCHAR(256),
    scope                   VARCHAR(256) COMMENT '权限范围：read,write',
    authorized_grant_types  VARCHAR(256) COMMENT '授权模式：password,authorization_code,refresh_token',
    web_server_redirect_uri VARCHAR(256),
    authorities             VARCHAR(256),
    access_token_validity   INTEGER COMMENT 'token有效期',
    refresh_token_validity  INTEGER COMMENT 'refresh_token有效期',
    additional_information  VARCHAR(4096),
    autoapprove             VARCHAR(256)
);

-- 存token --
DROP TABLE IF EXISTS `oauth_access_token`;
create table oauth_access_token
(
    token_id          VARCHAR(256),
    token             BLOB,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256),
    authentication    BLOB,
    refresh_token     VARCHAR(256)
);
