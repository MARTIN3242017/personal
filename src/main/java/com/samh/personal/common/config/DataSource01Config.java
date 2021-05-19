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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * MySQL数据源
 */
@Configuration
@Data
@MapperScan(basePackages = {"com.samh.metadata.mapper"},
        sqlSessionTemplateRef = "sqlSessionTemplate01")
public class DataSource01Config {

    @Bean("dataSource01")
    @ConfigurationProperties(prefix = "spring.datasource.source01")
    @Primary
    public DataSource dataSource01() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactory01")
    @Primary
    public SqlSessionFactory sqlSessionFactory01(@Qualifier("dataSource01") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/mysql/*.xml"));
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "transactionManager01")
    @Primary
    public DataSourceTransactionManager transactionManager01(@Qualifier("dataSource01") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate01")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate01(@Qualifier("sqlSessionFactory01") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}