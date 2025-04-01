package com.gitee.rabbitnoteeth.bedrock.data.nosql.redis;

import com.gitee.rabbitnoteeth.bedrock.data.nosql.redis.exception.RedisClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisClientManager {

    private RedisClientManager() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisClientManager.class);
    private static final String DEFAULT_CLIENT_ID = "DEFAULT";
    private static final Map<String, RedisClient> CLIENT_MAP = new ConcurrentHashMap<>();

    public static void registerClient(RedisClientConfig config) throws RedisClientException {
        registerClient(DEFAULT_CLIENT_ID, config);
    }

    public static void registerClient(String id, RedisClientConfig config) throws RedisClientException {
        CLIENT_MAP.put(id, new RedisClient(id, config));
        LOGGER.info("register redis client, id={}, config={}", id, config);
    }

    public static RedisClient getClient() {
        return getClient(DEFAULT_CLIENT_ID);
    }

    public static RedisClient getClient(String clientId) {
        RedisClient redisClient = CLIENT_MAP.get(clientId);
        if (redisClient == null) {
            throw new IllegalArgumentException("there is no redis client with id [" + clientId + "]");
        }
        return redisClient;
    }

}
