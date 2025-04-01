package com.gitee.rabbitnoteeth.bedrock.data.sql;

import com.gitee.rabbitnoteeth.bedrock.data.sql.exception.SqlClientException;
import com.gitee.rabbitnoteeth.bedrock.util.validation.ValidationUtils;
import com.gitee.rabbitnoteeth.bedrock.util.validation.annotation.Validate;
import com.gitee.rabbitnoteeth.bedrock.util.validation.entity.Rule;
import com.zaxxer.hikari.HikariConfig;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public final class SqlClientConfig {

    private final String driverClassName;
    private final String url;
    private final String username;
    private final String password;
    private final List<String> mapperScanPackages;
    private final List<String> mapperLocations;
    private final boolean mapUnderscoreToCamelCase;
    private final long connectionTimeout;
    private final long validationTimeout;
    private final long idleTimeout;
    private final long maxLifetime;
    private final int maxPoolSize;
    private final int minIdle;

    public SqlClientConfig(String driverClassName, String url, String username, String password, boolean mapUnderscoreToCamelCase, long connectionTimeout, long validationTimeout, long idleTimeout, long maxLifetime, int maxPoolSize, int minIdle) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.username = username;
        this.password = password;
        this.mapperScanPackages = new ArrayList<>();
        this.mapperLocations = new ArrayList<>();
        this.mapUnderscoreToCamelCase = mapUnderscoreToCamelCase;
        this.connectionTimeout = connectionTimeout;
        this.validationTimeout = validationTimeout;
        this.idleTimeout = idleTimeout;
        this.maxLifetime = maxLifetime;
        this.maxPoolSize = maxPoolSize;
        this.minIdle = minIdle;
    }

    HikariConfig toHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(this.driverClassName);
        hikariConfig.setJdbcUrl(this.url);
        hikariConfig.setUsername(this.username);
        hikariConfig.setPassword(this.password);
        hikariConfig.setConnectionTimeout(this.connectionTimeout);
        hikariConfig.setValidationTimeout(this.validationTimeout);
        hikariConfig.setIdleTimeout(this.idleTimeout);
        hikariConfig.setMaxLifetime(this.maxLifetime);
        hikariConfig.setMaximumPoolSize(this.maxPoolSize);
        hikariConfig.setMinimumIdle(this.minIdle);
        return hikariConfig;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public long getValidationTimeout() {
        return validationTimeout;
    }

    public long getIdleTimeout() {
        return idleTimeout;
    }

    public long getMaxLifetime() {
        return maxLifetime;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public List<String> getMapperLocations() {
        return mapperLocations;
    }

    public SqlClientConfig addMapperLocation(String location) {
        this.mapperLocations.add(location);
        return this;
    }

    public List<String> getMapperScanPackages() {
        return mapperScanPackages;
    }

    public SqlClientConfig addMapperScanPackage(String location) {
        this.mapperScanPackages.add(location);
        return this;
    }

    public boolean isMapUnderscoreToCamelCase() {
        return mapUnderscoreToCamelCase;
    }

    public static class Builder {
        private static final boolean DEFAULT_MAP_UNDERSCORE_TO_CAMEL_CASE = true;
        private static final long DEFAULT_CONNECTION_TIMEOUT = SECONDS.toMillis(30);
        private static final long DEFAULT_VALIDATION_TIMEOUT = SECONDS.toMillis(5);
        private static final long DEFAULT_IDLE_TIMEOUT = MINUTES.toMillis(10);
        private static final long DEFAULT_MAX_LIFETIME = MINUTES.toMillis(30);
        private static final int DEFAULT_MAX_POOL_SIZE = 20;
        private static final int DEFAULT_MIN_IDLE = 0;

        @Validate(rule = Rule.NOT_BLANK, message = "driverClassName can not be blank")
        private String driverClassName;
        @Validate(rule = Rule.NOT_BLANK, message = "url can not be blank")
        private String url;
        @Validate(rule = Rule.NOT_BLANK, message = "username can not be blank")
        private String username;
        @Validate(rule = Rule.NOT_BLANK, message = "password can not be blank")
        private String password;
        private boolean mapUnderscoreToCamelCase = DEFAULT_MAP_UNDERSCORE_TO_CAMEL_CASE;
        private long connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
        private long validationTimeout = DEFAULT_VALIDATION_TIMEOUT;
        private long idleTimeout = DEFAULT_IDLE_TIMEOUT;
        private long maxLifetime = DEFAULT_MAX_LIFETIME;
        private int maxPoolSize = DEFAULT_MAX_POOL_SIZE;
        private int minIdle = DEFAULT_MIN_IDLE;

        public Builder setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
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

        public Builder setMapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase) {
            this.mapUnderscoreToCamelCase = mapUnderscoreToCamelCase;
            return this;
        }

        public Builder setConnectionTimeout(long connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public Builder setValidationTimeout(long validationTimeout) {
            this.validationTimeout = validationTimeout;
            return this;
        }

        public Builder setIdleTimeout(long idleTimeout) {
            this.idleTimeout = idleTimeout;
            return this;
        }

        public Builder setMaxLifetime(long maxLifetime) {
            this.maxLifetime = maxLifetime;
            return this;
        }

        public Builder setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
            return this;
        }

        public Builder setMinIdle(int minIdle) {
            this.minIdle = minIdle;
            return this;
        }

        public SqlClientConfig build() throws SqlClientException {
            try {
                ValidationUtils.validate(this);
                return new SqlClientConfig(driverClassName, url, username, password, mapUnderscoreToCamelCase, connectionTimeout, validationTimeout, idleTimeout, maxLifetime, maxPoolSize, minIdle);
            } catch (Throwable e) {
                throw new SqlClientException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "SqlClientConfig{" +
            "driverClassName='" + driverClassName + '\'' +
            ", url='" + url + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", connectionTimeout='" + connectionTimeout + '\'' +
            ", validationTimeout='" + validationTimeout + '\'' +
            ", idleTimeout='" + idleTimeout + '\'' +
            ", maxLifetime='" + maxLifetime + '\'' +
            ", maxPoolSize='" + maxPoolSize + '\'' +
            ", minIdle='" + minIdle + '\'' +
            ", mapUnderscoreToCamelCase='" + mapUnderscoreToCamelCase + '\'' +
            ", mapperScanPackages='" + String.join(",", mapperLocations) + '\'' +
            ", mapperLocationPatterns=" + String.join(",", mapperLocations) +
            '}';
    }

}
