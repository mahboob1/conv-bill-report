Insert into WEBS.SUBSCRIPTION_BULK_REQ (CORRELATION_ID,TRANSACTION_ID,ACCOUNT_ID,REQUEST_STATUS,REQUEST_TYPE,PUBLISHED_FALLOUT,PUBLISHED_COMPLETION_EVENT,CREATE_DATE,UPDATE_DATE) values ('17b249a6-c6c1-41f5-9542-1dabb66f98ab','b1ff13d7-9f45-4631-aa13-341ee39a6c2b',112233,'COMPLETE','MIGRATE_IN','N','Y','24-02-18','24-02-18');
Insert into WEBS.SUBSCRIPTION_BULK_REQ (CORRELATION_ID,TRANSACTION_ID,ACCOUNT_ID,REQUEST_STATUS,REQUEST_TYPE,PUBLISHED_FALLOUT,PUBLISHED_COMPLETION_EVENT,CREATE_DATE,UPDATE_DATE) values ('1fc9bd5b-6116-43d9-a4e6-9cf116bf905f','5678',1,'COMPLETE','MIGRATE_IN','N','Y','24-02-18','24-02-18');
Insert into WEBS.SUBSCRIPTION_BULK_REQ (CORRELATION_ID,TRANSACTION_ID,ACCOUNT_ID,REQUEST_STATUS,REQUEST_TYPE,PUBLISHED_FALLOUT,PUBLISHED_COMPLETION_EVENT,CREATE_DATE,UPDATE_DATE) values ('85985f75-8615-4a05-9e7a-4c06c6297e3b','101b2475-57ad-46b5-9e6d-92b6dab0acd8',1,'pending','MIGRATE_IN','N','Y','24-02-18','24-02-18');
Insert into WEBS.SUBSCRIPTION_BULK_REQ (CORRELATION_ID,TRANSACTION_ID,ACCOUNT_ID,REQUEST_STATUS,REQUEST_TYPE,PUBLISHED_FALLOUT,PUBLISHED_COMPLETION_EVENT,CREATE_DATE,UPDATE_DATE) values ('89c3ad26-b70e-4894-82d2-6a9603af174e','5678',1,'COMPLETE','MIGRATE_IN','N','Y','24-02-18','24-02-18');

-- Move Client Test Records
Insert into WEBS.SUBSCRIPTION_BULK_REQ (CORRELATION_ID,TRANSACTION_ID,ACCOUNT_ID,REQUEST_STATUS,REQUEST_TYPE,PUBLISHED_FALLOUT,PUBLISHED_COMPLETION_EVENT,CREATE_DATE,UPDATE_DATE) values ('89c3ad26-b70e-4894-82d2-6a9603af174f','5678',11,'PENDING','MOVE_CLIENT','N','N','24-02-18','24-02-18');
Insert into WEBS.SUBSCRIPTION_BULK_REQ_UNIT (CORRELATION_ID,SUBSCRIPTION_ID,REQUEST_STATUS,CUSTOMER_ACCOUNT_ID,PUBLISHED_FALLOUT,PUBLISHED_FINAL_EVENT,ERRORS_DOC,CREATE_DATE,UPDATE_DATE) values ('89c3ad26-b70e-4894-82d2-6a9603af174f',10000307,'PENDING',10000307,'N','N','','24-02-18','24-02-18');
Insert into WEBS.SUBSCRIPTION_BULK_REQ_UNIT (CORRELATION_ID,SUBSCRIPTION_ID,REQUEST_STATUS,CUSTOMER_ACCOUNT_ID,PUBLISHED_FALLOUT,PUBLISHED_FINAL_EVENT,ERRORS_DOC,CREATE_DATE,UPDATE_DATE) values ('89c3ad26-b70e-4894-82d2-6a9603af174f',10000308,'PENDING',10000308,'N','N','','24-02-18','24-02-18');

Insert into WEBS.SUBSCRIPTION_BULK_REQ_UNIT (CORRELATION_ID,SUBSCRIPTION_ID,REQUEST_STATUS,PUBLISHED_FALLOUT,PUBLISHED_FINAL_EVENT,ERRORS_DOC,CREATE_DATE,UPDATE_DATE) values ('17b249a6-c6c1-41f5-9542-1dabb66f98ab',10000144,'SUCCESS','Y','N','','24-02-18','24-02-18');
Insert into WEBS.SUBSCRIPTION_BULK_REQ_UNIT (CORRELATION_ID,SUBSCRIPTION_ID,REQUEST_STATUS,PUBLISHED_FALLOUT,PUBLISHED_FINAL_EVENT,ERRORS_DOC,CREATE_DATE,UPDATE_DATE) values ('1fc9bd5b-6116-43d9-a4e6-9cf116bf905f',10000261,'SUCCESS','N','Y','','24-02-18','24-02-18');
Insert into WEBS.SUBSCRIPTION_BULK_REQ_UNIT (CORRELATION_ID,SUBSCRIPTION_ID,REQUEST_STATUS,PUBLISHED_FALLOUT,PUBLISHED_FINAL_EVENT,ERRORS_DOC,CREATE_DATE,UPDATE_DATE) values ('85985f75-8615-4a05-9e7a-4c06c6297e3b',10000072,'Directly Changed','Y','N','','24-02-18','24-02-18');
Insert into WEBS.SUBSCRIPTION_BULK_REQ_UNIT (CORRELATION_ID,SUBSCRIPTION_ID,REQUEST_STATUS,PUBLISHED_FALLOUT,PUBLISHED_FINAL_EVENT,ERRORS_DOC,CREATE_DATE,UPDATE_DATE) values ('89c3ad26-b70e-4894-82d2-6a9603af174e',10000307,'Completed','Y','N','','24-02-18','24-02-18');
Insert into WEBS.SUBSCRIPTION_BULK_REQ_UNIT (CORRELATION_ID,SUBSCRIPTION_ID,REQUEST_STATUS,PUBLISHED_FALLOUT,PUBLISHED_FINAL_EVENT,ERRORS_DOC,CREATE_DATE,UPDATE_DATE) values ('89c3ad26-b70e-4894-82d2-6a9603af174e',10000308,'Not Completed','N','Y','','24-02-18','24-02-18');

Insert into WEBS.SUBSCRIPTION_BULK_REQ_PAYLOAD (CORRELATION_ID,REQUEST_PAYLOAD,CREATE_DATE,UPDATE_DATE) values ('17b249a6-c6c1-41f5-9542-1dabb66f98ab','intuit world value Something Changed twice','24-02-18','24-02-18');
Insert into WEBS.SUBSCRIPTION_BULK_REQ_PAYLOAD (CORRELATION_ID,REQUEST_PAYLOAD,CREATE_DATE,UPDATE_DATE) values ('1fc9bd5b-6116-43d9-a4e6-9cf116bf905f','intuit world','24-02-18','24-02-18');
Insert into WEBS.SUBSCRIPTION_BULK_REQ_PAYLOAD (CORRELATION_ID,REQUEST_PAYLOAD,CREATE_DATE,UPDATE_DATE) values ('85985f75-8615-4a05-9e7a-4c06c6297e3b','intuit world','24-02-18','24-02-18');
Insert into WEBS.SUBSCRIPTION_BULK_REQ_PAYLOAD (CORRELATION_ID,REQUEST_PAYLOAD,CREATE_DATE,UPDATE_DATE) values ('89c3ad26-b70e-4894-82d2-6a9603af174e','intuit world','24-02-18','24-02-18');

-- ### Batch Switch Offer records
Insert into WEBS.SUBSCRIPTION_BULK_REQ_PAYLOAD (CORRELATION_ID,REQUEST_PAYLOAD,CREATE_DATE,UPDATE_DATE) values ('5469bd5b-6116-43d9-a4e6-9cf116bf905f','intuit world','22-10-18','22-10-18');
Insert into WEBS.SUBSCRIPTION_BULK_REQ (CORRELATION_ID,TRANSACTION_ID,ACCOUNT_ID,REQUEST_STATUS,REQUEST_TYPE,PUBLISHED_FALLOUT,PUBLISHED_COMPLETION_EVENT,BATCH_RECORDS_COUNT,SOURCE,CREATE_DATE,UPDATE_DATE) values ('5469bd5b-6116-43d9-a4e6-9cf116bf905f','5469bd5b-6116-43d9-a4e6-9cf116bf905f','','PENDING','SWITCH_OFFER','N','N',3,'source','22-10-18','22-10-18');

Insert into WEBS.SUBSCRIPTION_BULK_REQ_UNIT (CORRELATION_ID,SUBSCRIPTION_ID,REQUEST_STATUS,PUBLISHED_FALLOUT,PUBLISHED_FINAL_EVENT,ERRORS_DOC,CUSTOMER_ACCOUNT_ID,TO_OFFER_ID,SWITCH_OFFER_REASON,RESET_DISCOUNT,CREATE_DATE,UPDATE_DATE) values ('5469bd5b-6116-43d9-a4e6-9cf116bf905f',10000144,'SUCCESS','Y','N','',12312313,20000072,'reason','Y','22-10-18','22-10-18');
Insert into WEBS.SUBS_BULK_REQ_UNIT_FALLOUT (CORRELATION_ID,CUSTOMER_ACCOUNT_ID,SUBSCRIPTION_ID,TO_OFFER_ID,SWITCH_OFFER_REASON,RESET_DISCOUNT,REQUEST_STATUS, ERROR_CATEGORY, ERRORS_DOC,CREATE_DATE,UPDATE_DATE) values('5469bd5b-6116-43d9-a4e6-9cf116bf905f','44332324','','23223232','Reason','Y','FAILED','FUNCTIONAL_FAILURE','Account not found','22-10-18','22-10-18');
Insert into WEBS.SUBS_BULK_REQ_UNIT_FALLOUT (CORRELATION_ID,CUSTOMER_ACCOUNT_ID,SUBSCRIPTION_ID,TO_OFFER_ID,SWITCH_OFFER_REASON,RESET_DISCOUNT,REQUEST_STATUS, ERROR_CATEGORY, ERRORS_DOC,CREATE_DATE,UPDATE_DATE) values('5469bd5b-6116-43d9-a4e6-9cf116bf905f','14332324','','23223232','Reason','Y','FAILED','SYSTEM_FAILURE','Downstream Service Unavailable','22-10-18','22-10-18');