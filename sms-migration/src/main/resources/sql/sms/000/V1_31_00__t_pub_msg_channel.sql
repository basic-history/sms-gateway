CREATE TABLE PUB_MSG_CHANNEL 
(
  ID VARCHAR2(32 CHAR) NOT NULL ,
  CODE VARCHAR2(2 CHAR) NOT NULL ,
  NAME VARCHAR2(32 CHAR) NOT NULL ,
  RULE_EXTEND_PARAM VARCHAR2(4000 CHAR) NULL ,
  URL_EXTEND_PARAM VARCHAR2(4000 CHAR) NULL ,
  STATUS VARCHAR2(2 CHAR) NOT NULL ,
  TEMPLATE_FLAG VARCHAR2(2 CHAR) NOT NULL ,
  MULTIPLE_SIGN_FLAG VARCHAR2(2 CHAR) NULL ,
  CREATE_SIGN_API_FLAG VARCHAR2(2 CHAR) NULL ,
  CREATE_TEMPLATE_API_FLAG VARCHAR2(2 CHAR) NULL ,
  PRIORITY NUMBER(2) NOT NULL ,
  CREATE_TIME TIMESTAMP(6)  NOT NULL ,
  CREATE_BY VARCHAR2(30 CHAR) NOT NULL ,
  REMARK VARCHAR2(255 CHAR) NULL ,
  CHANNEL_SERVICE VARCHAR2(200 BYTE) NULL 
);