package com.samh.personal.common.config;

import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * ClickHouse数据源
 */
@Configuration
@Data
@MapperScan(basePackages = {"com.samh.analyse.mapper",
        "com.samh.dataload.mapper",
        "com.samh.persona.mapper.clickhouse",
        "com.samh.usergroupResult.mapper"},
        sqlSessionTemplateRef = "sqlSessionTemplate02")
public class DataSource02Config {

    @Bean("dataSource02")
    @ConfigurationProperties(prefix = "spring.datasource.source02")
    public DataSource dataSource02() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactory02")
    public SqlSessionFactory sqlSessionFactory02(@Qualifier("dataSource02") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/clickhouse/*.xml"));
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "transactionManager02")
    public DataSourceTransactionManager transactionManager02(@Qualifier("dataSource02") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate02")
    public SqlSessionTemplate sqlSessionTemplate02(@Qualifier("sqlSessionFactory02") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}