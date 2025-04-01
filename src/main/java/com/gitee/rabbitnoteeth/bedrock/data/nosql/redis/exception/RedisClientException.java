package com.gitee.rabbitnoteeth.bedrock.data.nosql.redis.exception;

public class RedisClientException extends Exception {

    public RedisClientException() {
        super();
    }

    public RedisClientException(String message) {
        super(message);
    }

    public RedisClientException(Throwable e) {
        super(e);
    }

    public RedisClientException(String message, Throwable e) {
        super(message, e);
    }
}
