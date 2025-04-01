package com.gitee.rabbitnoteeth.bedrock.data.nosql.redis;

import com.gitee.rabbitnoteeth.bedrock.data.nosql.redis.commands.*;
import com.gitee.rabbitnoteeth.bedrock.data.nosql.redis.exception.RedisClientException;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Connection;
import redis.clients.jedis.JedisPooled;

public class RedisClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisClient.class);
    private final String id;
    private final RedisClientConfig config;
    private final JedisPooled jedis;
    private final KeyCommands keyCommands;
    private final StringCommands stringCommands;
    private final ListCommands listCommands;
    private final HashCommands hashCommands;
    private final SetCommands setCommands;
    private final ZSetCommands zSetCommands;

    public RedisClient(String id, RedisClientConfig config) throws RedisClientException {
        this.id = id;
        this.config = config;
        this.jedis = this.init();
        this.keyCommands = new KeyCommands(this.jedis);
        this.stringCommands = new StringCommands(this.jedis);
        this.listCommands = new ListCommands(this.jedis);
        this.hashCommands = new HashCommands(this.jedis);
        this.setCommands = new SetCommands(this.jedis);
        this.zSetCommands = new ZSetCommands(this.jedis);
    }

    private JedisPooled init() throws RedisClientException {
        try {
            LOGGER.info("Start create RedisClient with id [{}]", this.id);
            GenericObjectPoolConfig<Connection> poolConfig = new GenericObjectPoolConfig<>();
            poolConfig.setMaxTotal(this.config.getMaxTotal());
            poolConfig.setMaxIdle(this.config.getMaxIdle());
            poolConfig.setMinIdle(this.config.getMinIdle());
            JedisPooled jedis = new JedisPooled(poolConfig, this.config.getHost(), this.config.getPort(), this.config.getConnectTimeout(),
                this.config.getUsername(), this.config.getPassword(), this.config.getDatabase());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Succeed in creating JedisPooled");
            }
            LOGGER.info("Succeed in creating RedisClient with id [{}]", this.id);
            return jedis;
        } catch (Throwable e) {
            throw new RedisClientException("Failed to init RedisClient with id [" + this.id + "]", e);
        }
    }

    public KeyCommands key() {
        return this.keyCommands;
    }

    public StringCommands string() {
        return this.stringCommands;
    }

    public ListCommands list() {
        return this.listCommands;
    }

    public HashCommands hash() {
        return this.hashCommands;
    }

    public SetCommands set() {
        return this.setCommands;
    }

    public ZSetCommands zset() {
        return this.zSetCommands;
    }
}
