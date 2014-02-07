-- inserts manufacturer MOTOWN with charging station type MODEL1
INSERT INTO MANUFACTURER (ID, CODE) VALUES(1,'MOTOWN');

INSERT INTO EVSE (ID, IDENTIFIER) VALUES(1,1);
INSERT INTO EVSE VALUES(2,2);
INSERT INTO CONNECTOR (ID, CHARGINGPROTOCOL, CONNECTORTYPE, CURRENT, MAXAMP, PHASE, VOLTAGE) VALUES(1,3,23,1,32,3,230);
INSERT INTO CONNECTOR VALUES(2,3,23,1,32,3,230);
INSERT INTO CONNECTOR VALUES(3,3,23,1,32,3,230);
INSERT INTO CONNECTOR VALUES(4,3,23,1,32,3,230);
INSERT INTO CONNECTOR VALUES(5,3,23,1,32,3,230);
INSERT INTO CONNECTOR VALUES(6,3,23,1,32,3,230);
INSERT INTO EVSE_CONNECTOR (EVSE_ID, CONNECTORS_ID) VALUES(1,1);
INSERT INTO EVSE_CONNECTOR VALUES(1,2);
INSERT INTO EVSE_CONNECTOR VALUES(1,3);
INSERT INTO EVSE_CONNECTOR VALUES(2,4);
INSERT INTO EVSE_CONNECTOR VALUES(2,5);
INSERT INTO EVSE_CONNECTOR VALUES(2,6);
INSERT INTO CHARGINGSTATIONTYPE (ID, CODE) VALUES(1,'MODEL1');
INSERT INTO CHARGINGSTATIONTYPE_EVSE (CHARGINGSTATIONTYPE_ID, EVSES_ID) VALUES(1,1);
INSERT INTO CHARGINGSTATIONTYPE_EVSE VALUES(1,2);

-- inserts manufacturer ACME with charging station type CORP1
INSERT INTO MANUFACTURER (ID, CODE) VALUES(2,'ACME');

INSERT INTO EVSE (ID, IDENTIFIER) VALUES(3,1);
INSERT INTO EVSE VALUES(4,2);
INSERT INTO CONNECTOR (ID, CHARGINGPROTOCOL, CONNECTORTYPE, CURRENT, MAXAMP, PHASE, VOLTAGE) VALUES(7,3,23,1,16,3,230);
INSERT INTO CONNECTOR VALUES(8,3,23,1,16,3,230);
INSERT INTO CONNECTOR VALUES(9,3,23,1,16,3,230);
INSERT INTO CONNECTOR VALUES(10,3,23,1,16,3,230);
INSERT INTO CONNECTOR VALUES(11,3,23,1,16,3,230);
INSERT INTO CONNECTOR VALUES(12,3,23,1,16,3,230);
INSERT INTO EVSE_CONNECTOR (EVSE_ID, CONNECTORS_ID) VALUES(3,7);
INSERT INTO EVSE_CONNECTOR VALUES(3,8);
INSERT INTO EVSE_CONNECTOR VALUES(3,9);
INSERT INTO EVSE_CONNECTOR VALUES(4,10);
INSERT INTO EVSE_CONNECTOR VALUES(4,11);
INSERT INTO EVSE_CONNECTOR VALUES(4,12);
INSERT INTO CHARGINGSTATIONTYPE (ID, CODE) VALUES(2,'CORP1');
INSERT INTO CHARGINGSTATIONTYPE_EVSE (CHARGINGSTATIONTYPE_ID, EVSES_ID) VALUES(2,3);
INSERT INTO CHARGINGSTATIONTYPE_EVSE VALUES(2,4);