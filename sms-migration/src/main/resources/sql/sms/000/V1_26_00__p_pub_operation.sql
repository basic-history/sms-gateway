ALTER TABLE PUB_OPERATION_LOG ADD CHECK (ID IS NOT NULL);
ALTER TABLE PUB_OPERATION_LOG ADD PRIMARY KEY (ID);