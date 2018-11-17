ALTER TABLE PUB_MSG_CHANNEL ADD UNIQUE (CODE);
ALTER TABLE PUB_MSG_CHANNEL ADD CHECK (ID IS NOT NULL);
ALTER TABLE PUB_MSG_CHANNEL ADD CHECK (CODE IS NOT NULL);
ALTER TABLE PUB_MSG_CHANNEL ADD CHECK (NAME IS NOT NULL);
ALTER TABLE PUB_MSG_CHANNEL ADD CHECK (STATUS IS NOT NULL);
ALTER TABLE PUB_MSG_CHANNEL ADD CHECK (TEMPLATE_FLAG IS NOT NULL);
ALTER TABLE PUB_MSG_CHANNEL ADD CHECK (PRIORITY IS NOT NULL);
ALTER TABLE PUB_MSG_CHANNEL ADD CHECK (CREATE_TIME IS NOT NULL);
ALTER TABLE PUB_MSG_CHANNEL ADD CHECK (CREATE_BY IS NOT NULL);
ALTER TABLE PUB_MSG_CHANNEL ADD PRIMARY KEY (ID);