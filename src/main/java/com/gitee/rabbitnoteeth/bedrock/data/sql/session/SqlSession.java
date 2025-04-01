package com.gitee.rabbitnoteeth.bedrock.data.sql.session;

import java.io.Closeable;
import java.io.IOException;

public final class SqlSession implements Closeable {

    private final org.apache.ibatis.session.SqlSession session;

    public SqlSession(org.apache.ibatis.session.SqlSession session) {
        this.session = session;
    }

    public <T> T getMapper(Class<T> type) {
        return session.getMapper(type);
    }

    public void commit() {
        session.commit();
    }

    public void rollback() {
        session.rollback();
    }

    @Override
    public void close() throws IOException {
        session.close();
    }

}
