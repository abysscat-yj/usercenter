package com.abysscat.usercenter.dal.config;

import java.lang.annotation.Annotation;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * usercenter库数据源配置
 */
@Configuration
public class MyBatisUserCenterDataSourceConfig {

    /**
     * Following two items must be updated according to specific data source
     */
    static final String NAMESPACE = "usercenter";
    static final Class<? extends Annotation> ANNOTATION = MyBatisUserCenterDao.class;

    static final String PACKAGE = "com.abysscat.usercenter.dal." + NAMESPACE;
    static final String DATA_SOURCE = NAMESPACE + "DataSource";
    static final String SQL_SESSION_FACTORY = NAMESPACE + "SqlSessionFactory";
    static final String CONFIGURATION_PROPERTIES = "spring.datasource." + NAMESPACE + ".hikari";
    static final String TRANSACTION = NAMESPACE + "TransactionManager";
    static final String MAPPER_SCANNER = NAMESPACE + "MapperScannerConfigurer";

    @Primary
    @Bean(name = DATA_SOURCE)
    @ConfigurationProperties(prefix = CONFIGURATION_PROPERTIES)
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = SQL_SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactory(@Qualifier(DATA_SOURCE) DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(PACKAGE);
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = TRANSACTION)
    public DataSourceTransactionManager transactionManager(@Qualifier(DATA_SOURCE) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    public static class PropertySourcesConfiguration {
        @Bean(name = MAPPER_SCANNER)
        public MapperScannerConfigurer usercenterMapperScannerConfigurer() {
            MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
            mapperScannerConfigurer.setSqlSessionFactoryBeanName(SQL_SESSION_FACTORY);
            mapperScannerConfigurer.setBasePackage(PACKAGE);
            mapperScannerConfigurer.setAnnotationClass(ANNOTATION);
            return mapperScannerConfigurer;
        }
    }

}
