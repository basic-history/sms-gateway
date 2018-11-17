CREATE TABLE PUB_MSG_VER_CODE 
(
  ID VARCHAR2(32 CHAR) NOT NULL ,
  CODE VARCHAR2(3 CHAR) NOT NULL ,
  VER_NAME VARCHAR2(200 CHAR) NOT NULL ,
  CREATE_TIME TIMESTAMP(6)  NOT NULL ,
  CREATE_BY VARCHAR2(30 CHAR) NOT NULL ,
  REMARK VARCHAR2(255 CHAR) NULL ,
  MID VARCHAR2(15 BYTE) NULL 
);