-- Drop tables if already exist
DROP TABLE CRM_DATA.OUTBOUND_TOUCHPOINTS if exists;
DROP TABLE CRM_DATA.TOUCHPOINTS if exists;
DROP SEQUENCE CRM_DATA.SEQ_OUT_TOUCHPOINTS if exists;

drop schema CRM_DATA if exists;
create schema CRM_DATA;
create SEQUENCE CRM_DATA.SEQ_OUT_TOUCHPOINTS;

CREATE TABLE CRM_DATA.TOUCHPOINTS
(
   TCH_ID NUMBER(19, 0) NOT NULL
, TCH_PTY_ID NUMBER(19, 0) NOT NULL
, CONSTRAINT PK_TOUCH PRIMARY KEY
  (
    TCH_ID
  )
);

CREATE TABLE CRM_DATA.OUTBOUND_TOUCHPOINTS
(
   OT_ID bigint default CRM_DATA.SEQ_OUT_TOUCHPOINTS.nextval
, OT_DESCRIPTION VARCHAR2(256 CHAR) NULL
, OT_LANGUAGE VARCHAR2(256 CHAR) NULL
, OT_MESSAGE_BODY VARCHAR2(256 CHAR) NULL
, CREATE_DATE DATE
, OT_TCH_ID NUMBER(19, 0)
, CONSTRAINT PK_OUTBOUND PRIMARY KEY
  (
    OT_ID
  )
);

 


 

  
