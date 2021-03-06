drop all objects;

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
, OT_MESSAGE_BODY CLOB NULL
, CREATE_DATE TIMESTAMP
, OT_TCH_ID NUMBER(19, 0)
, CONSTRAINT PK_OUTBOUND PRIMARY KEY
  (
    OT_ID
  )
);

CREATE TABLE CRM_DATA.CUSTOMER_PAYMENT
(
  CPA_ID NUMBER(19, 0) NOT NULL,
  CPA_PAYTYPE NUMBER(10, 0) NOT NULL,
  CPA_CREDIT_CARD_NUMBER VARCHAR2(20 CHAR) NULL,
  CPA_CREDIT_CARD_TYPE VARCHAR2(10 CHAR) NULL,
  CPA_FINANCIAL_INSTITUTION_ID VARCHAR2(20 CHAR) NULL,
  CPA_BANK_ACCOUNT_NUMBER VARCHAR2(20 CHAR) NULL,
  CPA_CHEQUE_NUMBER VARCHAR2(20 CHAR) NULL,
  CPA_PAYMENT_AMOUNT NUMBER(10, 0),
  CPA_APPROVED VARCHAR2(10 CHAR) NULL,
  CPA_CNT_ISO_CODE VARCHAR2(10 CHAR) NULL,
  CONSTRAINT PK_PAYMENT PRIMARY KEY
  (
    CPA_ID
  )
);

CREATE TABLE CRM_DATA.BILLING_AUDIT_LOG
(
  BAL_BILL_ID NUMBER(19, 0) NOT NULL,
  CREATE_DATE DATE,
  BAL_CSP_ID NUMBER(19, 0) NOT NULL,
  BAL_BILL_FOR_DATE DATE,
  BAL_BILL_FROM_DATE DATE,
  BAL_BILL_TO_DATE DATE,
  BAL_BILL_TOT_CHARGE_AMT NUMBER(10, 0),
  BAL_SCI_CHARGE_YEAR_PERIOD NUMBER(1),
  BAL_SCI_CHARGE_MONTH_PERIOD NUMBER(1),
  BAL_SCI_CHARGE_DAY_PERIOD NUMBER(1),
  BAL_BILL_PAYMENT_CPA_ID NUMBER(19, 0) NOT NULL,
  BAL_SCI_METHOD VARCHAR2(20 CHAR) NULL,
  BAL_SEND_STATUS VARCHAR2(20 CHAR) NULL,
  BAL_SEND_STATUS_DATE DATE,
  CONSTRAINT PK_AUDIT_LOG PRIMARY KEY
  (
    BAL_BILL_ID
  )
);

create schema IDBS_QBDT_CNV;

CREATE TABLE IDBS_QBDT_CNV.OBILL_CONV_MSTR_HIST
(
  BATCH_ID NUMBER(19, 0) NOT NULL,
  SUB_ID NUMBER(19, 0) NOT NULL,
  REALM_ID VARCHAR2(20 CHAR) NOT NULL,
  PTY_ID VARCHAR2(20 CHAR) NOT NULL,
  CONSTRAINT PK_MSTR_HIST PRIMARY KEY
  (
    BATCH_ID,
    SUB_ID
  )
);
