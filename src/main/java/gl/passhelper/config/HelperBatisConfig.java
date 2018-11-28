package gl.passhelper.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "gl.passhelper.dao.passhelper",sqlSessionFactoryRef = "passhelperSqlSessionFactory")
public class HelperBatisConfig {

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> {
        };
    }

    @Bean("passhelperDataSource")
    @ConfigurationProperties(prefix = "spring.passhelper.datasource.druid")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("passhelperSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource passhelperDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(passhelperDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setMapperLocations(resolver.getResources("mapper/passhelper/*Mapper.xml"));
        return sqlSessionFactory.getObject();
    }

    @Bean("passhelperTransactionManager")
    public DataSourceTransactionManager transactionManager(DataSource passhelperDataSource) {
        return new DataSourceTransactionManager(passhelperDataSource);
    }
}