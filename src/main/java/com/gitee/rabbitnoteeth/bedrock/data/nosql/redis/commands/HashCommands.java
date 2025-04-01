package com.gitee.rabbitnoteeth.bedrock.data.nosql.redis.commands;

import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HashCommands {

    private final JedisPooled jedis;

    public HashCommands(JedisPooled jedis) {
        this.jedis = jedis;
    }

    public long set(String key, String field, String value) {
        return jedis.hset(key, field, value);
    }

    public long set(String key, Map<String, String> hash) {
        return jedis.hset(key, hash);
    }

    public String get(String key, String field) {
        return jedis.hget(key, field);
    }

    public long setnx(String key, String field, String value) {
        return jedis.hsetnx(key, field, value);
    }

    public String mset(String key, Map<String, String> hash) {
        return jedis.hmset(key, hash);
    }

    public List<String> mget(String key, String... fields) {
        return jedis.hmget(key, fields);
    }

    public long incrBy(String key, String field, long value) {
        return jedis.hincrBy(key, field, value);
    }

    public double incrByFloat(String key, String field, double value) {
        return jedis.hincrByFloat(key, field, value);
    }

    public boolean exists(String key, String field) {
        return jedis.hexists(key, field);
    }

    public long del(String key, String... field) {
        return jedis.hdel(key, field);
    }

    public long len(String key) {
        return jedis.hlen(key);
    }

    public Set<String> keys(String key) {
        return jedis.hkeys(key);
    }

    public List<String> vals(String key) {
        return jedis.hvals(key);
    }

    public Map<String, String> getAll(String key) {
        return jedis.hgetAll(key);
    }

    public String randfield(String key) {
        return jedis.hrandfield(key);
    }

    public List<String> randfield(String key, long count) {
        return jedis.hrandfield(key, count);
    }

    public List<Map.Entry<String, String>> randfieldWithValues(String key, long count) {
        return jedis.hrandfieldWithValues(key, count);
    }

    public ScanResult<Map.Entry<String, String>> scan(String key, String cursor) {
        return scan(key, cursor, new ScanParams());
    }

    public ScanResult<Map.Entry<String, String>> scan(String key, String cursor, ScanParams params) {
        return jedis.hscan(key, cursor, params);
    }

    public long strlen(String key, String field) {
        return jedis.hstrlen(key, field);
    }

}
