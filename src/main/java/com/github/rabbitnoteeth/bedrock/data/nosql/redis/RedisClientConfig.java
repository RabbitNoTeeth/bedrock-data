package com.github.rabbitnoteeth.bedrock.data.nosql.redis;

import com.github.rabbitnoteeth.bedrock.data.nosql.redis.exception.RedisClientException;
import com.github.rabbitnoteeth.bedrock.util.validation.ValidationUtils;
import com.github.rabbitnoteeth.bedrock.util.validation.annotation.Validate;
import com.github.rabbitnoteeth.bedrock.util.validation.entity.Rule;

public class RedisClientConfig {

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final int connectTimeout;
    private final int database;
    private final int maxTotal;
    private final int maxIdle;
    private final int minIdle;

    private RedisClientConfig(String host, int port, String username, String password, int connectTimeout, int database, int maxTotal, int maxIdle, int minIdle) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.connectTimeout = connectTimeout;
        this.database = database;
        this.maxTotal = maxTotal;
        this.maxIdle = maxIdle;
        this.minIdle = minIdle;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public int getDatabase() {
        return database;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public static class Builder {
        private static final int DEFAULT_MAX_TOTAL = 8;
        private static final int DEFAULT_MAX_IDLE = 8;
        private static final int DEFAULT_MIN_IDLE = 0;
        private static final int DEFAULT_DATABASE = 0;
        private static final int DEFAULT_CONNECT_TIMEOUT = 30000;

        @Validate(rule = Rule.NOT_BLANK, message = "host can not be blank")
        private String host;
        @Validate(rule = Rule.NOT_BLANK, message = "port can not be null")
        private int port;
        private String username;
        private String password;
        private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
        private int database = DEFAULT_DATABASE;
        private int maxTotal = DEFAULT_MAX_TOTAL;
        private int maxIdle = DEFAULT_MAX_IDLE;
        private int minIdle = DEFAULT_MIN_IDLE;

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setDatabase(int database) {
            this.database = database;
            return this;
        }

        public Builder setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
            return this;
        }

        public Builder setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
            return this;
        }

        public Builder setMinIdle(int minIdle) {
            this.minIdle = minIdle;
            return this;
        }

        public RedisClientConfig build() throws RedisClientException {
            try {
                ValidationUtils.validate(this);
                return new RedisClientConfig(host, port, username, password, connectTimeout, database, maxTotal, maxIdle, minIdle);
            } catch (Throwable e) {
                throw new RedisClientException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "RedisClientConfig{" +
            "host='" + host + '\'' +
            ", port=" + port +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", connectTimeout=" + connectTimeout +
            ", database=" + database +
            ", maxTotal=" + maxTotal +
            ", maxIdle=" + maxIdle +
            ", minIdle=" + minIdle +
            '}';
    }
}
