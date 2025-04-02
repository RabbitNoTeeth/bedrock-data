package com.github.rabbitnoteeth.bedrock.data.sql.session;

public enum ExecutorType {

    SIMPLE(org.apache.ibatis.session.ExecutorType.SIMPLE),

    REUSE(org.apache.ibatis.session.ExecutorType.REUSE),

    BATCH(org.apache.ibatis.session.ExecutorType.BATCH);

    private final org.apache.ibatis.session.ExecutorType type;

    ExecutorType(org.apache.ibatis.session.ExecutorType type) {
        this.type = type;
    }

    public org.apache.ibatis.session.ExecutorType getType() {
        return type;
    }
}
