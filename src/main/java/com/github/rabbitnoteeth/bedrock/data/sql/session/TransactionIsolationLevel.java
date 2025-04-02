package com.github.rabbitnoteeth.bedrock.data.sql.session;

public enum TransactionIsolationLevel {
    NONE(org.apache.ibatis.session.TransactionIsolationLevel.NONE),
    READ_UNCOMMITTED(org.apache.ibatis.session.TransactionIsolationLevel.READ_UNCOMMITTED),
    READ_COMMITTED(org.apache.ibatis.session.TransactionIsolationLevel.READ_COMMITTED),
    REPEATABLE_READ(org.apache.ibatis.session.TransactionIsolationLevel.REPEATABLE_READ),
    SERIALIZABLE(org.apache.ibatis.session.TransactionIsolationLevel.SERIALIZABLE);

    private final org.apache.ibatis.session.TransactionIsolationLevel level;

    TransactionIsolationLevel(org.apache.ibatis.session.TransactionIsolationLevel level) {
        this.level = level;
    }

    public org.apache.ibatis.session.TransactionIsolationLevel getLevel() {
        return level;
    }
}
