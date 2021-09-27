-- INSERT INTO USER (USERNAME, PASSWORD) VALUES ('fpmoles', '$2a$11$dp4wMyuqYE3KSwIyQmWZFeUb7jCsHAdk7ZhFc0qGw6i5J124imQBi');
-- INSERT INTO USER (USERNAME, PASSWORD) VALUES ('jdoe', '$2a$11$3NO32OV1TGjap3xMpAEjmuiizitWuaSwUYz42aMtlxRliwJ8zm4Sm');
-- INSERT INTO AUTH_USER_GROUP (USERNAME, AUTH_GROUP) VALUES('fpmoles', 'USER');
-- INSERT INTO AUTH_USER_GROUP (USERNAME, AUTH_GROUP) VALUES('fpmoles', 'ADMIN');
-- INSERT INTO AUTH_USER_GROUP (USERNAME, AUTH_GROUP) VALUES('jdoe', 'USER');

INSERT INTO authorities(id) VALUES('admin'), ('user'), ('readonly');

-- Replace with some SpringRunner script so passwords can be hashed, maybe pick passwords up from properties
INSERT INTO users(username, password, enabled)
VALUES('josh', 'temp', true), ('rhian', 'test', true), ('basic', 'temp', true);

INSERT INTO user_authority(username, authority)
VALUES('josh', 'admin'), ('rhian', 'user'), ('basic', 'readonly');