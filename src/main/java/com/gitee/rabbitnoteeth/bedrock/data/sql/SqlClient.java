package com.gitee.rabbitnoteeth.bedrock.data.sql;

import com.gitee.rabbitnoteeth.bedrock.data.sql.exception.SqlClientException;
import com.gitee.rabbitnoteeth.bedrock.data.sql.session.ExecutorType;
import com.gitee.rabbitnoteeth.bedrock.data.sql.session.SqlSession;
import com.gitee.rabbitnoteeth.bedrock.data.sql.session.TransactionIsolationLevel;
import com.gitee.rabbitnoteeth.bedrock.util.entity.Resource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SqlClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlClient.class);
    private static final ExecutorType DEFAULT_EXECUTOR_TYPE = ExecutorType.SIMPLE;
    private static final TransactionIsolationLevel DEFAULT_TRANSACTION_ISOLATION_LEVEL = TransactionIsolationLevel.REPEATABLE_READ;
    private final String id;
    private final SqlClientConfig config;
    private final SqlSessionFactory sqlSessionFactory;

    SqlClient(String id, SqlClientConfig config) throws SqlClientException {
        this.id = id;
        this.config = config;
        this.sqlSessionFactory = this.init();
    }

    private SqlSessionFactory init() throws SqlClientException {
        try {
            LOGGER.info("Start create SqlClient with id [{}]", this.id);
            // create hikari datasource
            HikariConfig hikariConfig = this.config.toHikariConfig();
            DataSource dataSource = new HikariDataSource(hikariConfig);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Succeed in creating hikari datasource");
            }
            // create mybatis sqlSessionFactory
            TransactionFactory transactionFactory = new JdbcTransactionFactory();
            Environment environment = new Environment(this.id, transactionFactory, dataSource);
            Configuration configuration = new Configuration(environment);
            configuration.setMapUnderscoreToCamelCase(this.config.isMapUnderscoreToCamelCase());
            List<String> mapperScanPackages = this.config.getMapperScanPackages();
            List<String> mapperLocations = this.config.getMapperLocations();
            Set<Resource> resources = scanMapper(mapperScanPackages, mapperLocations);
            for (Resource resource : resources) {
                String path = resource.getPath();
                try (InputStream inputStream = resource.getInputStream()) {
                    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(inputStream, configuration, path, configuration.getSqlFragments());
                    xmlMapperBuilder.parse();
                    LOGGER.info("Succeed in parsing mapping resource [{}]", path);
                } catch (Exception e) {
                    throw new IOException("Failed to parse mapping resource [" + path + "]", e);
                }
            }
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(configuration);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Succeed in creating mybatis SqlSessionFactory");
            }
            LOGGER.info("Succeed in creating SqlClient with id [{}]", this.id);
            return sessionFactory;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to init SqlClient with id [" + this.id + "]", e);
        }
    }

    public SqlSession getSession() {
        return getSession(DEFAULT_EXECUTOR_TYPE, DEFAULT_TRANSACTION_ISOLATION_LEVEL, true);
    }

    public SqlSession getSession(ExecutorType executorType) {
        return getSession(executorType, DEFAULT_TRANSACTION_ISOLATION_LEVEL, true);
    }

    public SqlSession getSession(TransactionIsolationLevel transactionIsolationLevel) {
        return getSession(DEFAULT_EXECUTOR_TYPE, transactionIsolationLevel, false);
    }

    public SqlSession getSession(ExecutorType executorType, TransactionIsolationLevel transactionIsolationLevel) {
        return getSession(executorType, transactionIsolationLevel, false);
    }

    private Set<Resource> scanMapper(List<String> mapperScanPackages, List<String> mapperLocations) {
        Set<Resource> result = new HashSet<>();
        if (mapperLocations.isEmpty()) {
            return result;
        }
        List<PathMatcher> pathMatchers = mapperLocations.stream()
            .map(pattern -> FileSystems.getDefault().getPathMatcher("glob:" + pattern))
            .toList();
        for (String scanPackage : mapperScanPackages) {
            Reflections reflections = new Reflections(scanPackage, Scanners.Resources);
            Set<String> resources = reflections.getResources("(.)+.xml");
            for (String resource : resources) {
                boolean match = pathMatchers.stream().anyMatch(pathMatcher -> pathMatcher.matches(new File(resource).toPath()));
                if (!match) {
                    continue;
                }
                result.add(new Resource(resource));
            }
        }
        return result;
    }

    private SqlSession getSession(ExecutorType executorType, TransactionIsolationLevel transactionIsolationLevel, boolean autoCommit) {
        if (autoCommit) {
            return new SqlSession(sqlSessionFactory.openSession(executorType.getType(), true));
        } else {
            return new SqlSession(sqlSessionFactory.openSession(executorType.getType(), transactionIsolationLevel.getLevel()));
        }
    }

}
