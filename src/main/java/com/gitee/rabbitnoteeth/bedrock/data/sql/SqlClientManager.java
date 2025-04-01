package com.gitee.rabbitnoteeth.bedrock.data.sql;

import com.gitee.rabbitnoteeth.bedrock.data.sql.exception.SqlClientException;
import com.gitee.rabbitnoteeth.bedrock.data.sql.session.ExecutorType;
import com.gitee.rabbitnoteeth.bedrock.data.sql.session.SqlSession;
import com.gitee.rabbitnoteeth.bedrock.data.sql.session.TransactionIsolationLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SqlClientManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlClientManager.class);
    private static final String DEFAULT_CLIENT_ID = "DEFAULT";
    private static final Map<String, SqlClient> CLIENT_MAP = new ConcurrentHashMap<>();

    public static void registerClient(SqlClientConfig config) throws SqlClientException {
        registerClient(DEFAULT_CLIENT_ID, config);
    }

    public static void registerClient(String id, SqlClientConfig config) throws SqlClientException {
        CLIENT_MAP.put(id, new SqlClient(id, config));
        LOGGER.info("register sql client, id={}, config={}", id, config);
    }

    public static boolean existClient(String id) {
        return CLIENT_MAP.containsKey(id);
    }

    public static SqlSession openSession() {
        return openSession(DEFAULT_CLIENT_ID);
    }

    public static SqlSession openSession(String clientId) {
        SqlClient client = getClient(clientId);
        return client.getSession();
    }

    public static SqlSession openSession(ExecutorType executorType) {
        return openSession(DEFAULT_CLIENT_ID, executorType);
    }

    private static SqlSession openSession(String clientId, ExecutorType executorType) {
        SqlClient client = getClient(clientId);
        return client.getSession(executorType);
    }

    public static SqlSession openSession(TransactionIsolationLevel transactionIsolationLevel) {
        return openSession(DEFAULT_CLIENT_ID, transactionIsolationLevel);
    }

    public static SqlSession openSession(String clientId, TransactionIsolationLevel transactionIsolationLevel) {
        SqlClient client = getClient(clientId);
        return client.getSession(transactionIsolationLevel);
    }

    public static SqlSession openSession(ExecutorType executorType, TransactionIsolationLevel transactionIsolationLevel) {
        return openSession(DEFAULT_CLIENT_ID, executorType, transactionIsolationLevel);
    }

    public static SqlSession openSession(String clientId, ExecutorType executorType, TransactionIsolationLevel transactionIsolationLevel) {
        SqlClient client = getClient(clientId);
        return client.getSession(executorType, transactionIsolationLevel);
    }

    public static SqlClient getClient(String clientId) {
        SqlClient sqlClient = CLIENT_MAP.get(clientId);
        if (sqlClient == null) {
            throw new IllegalArgumentException("there is no sql client with id [" + clientId + "]");
        }
        return sqlClient;
    }

}
