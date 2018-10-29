-- Create sequence 
create sequence SEQ_CHANNEL_APP_CODE
minvalue 11
maxvalue 99
start with 31
increment by 1
cache 20
cycle;

create sequence SEQ_CHANNEL_CODE
minvalue 11
maxvalue 99
start with 51
increment by 1
cache 20
cycle;

create sequence SEQ_MENU_ID
minvalue 10001
maxvalue 99999
start with 10201
increment by 1
cache 20
cycle;

create sequence SEQ_PERMISSION_ID
minvalue 1000000001
maxvalue 9999999999
start with 1000000081
increment by 1
cache 20
cycle;

create sequence SEQ_PLATFORM_SIGNATURE_NUMBER
minvalue 11
maxvalue 99
start with 31
increment by 1
cache 20
cycle;

create sequence SEQ_PLATFORM_TEMPLATE_NUMBER
minvalue 11
maxvalue 99
start with 31
increment by 1
cache 20
cycle;

create sequence SEQ_SMS_LOG_BATCH_ID
minvalue 100000000000000000
maxvalue 999999999999999999
start with 100000000000000361
increment by 1
cache 20;

create sequence SEQ_VER_CODE
minvalue 101
maxvalue 999
start with 141
increment by 1
cache 20;