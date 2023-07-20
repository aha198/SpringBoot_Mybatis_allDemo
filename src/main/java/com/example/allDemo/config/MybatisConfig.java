package com.example.allDemo.config;
import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.apache.ibatis.session.SqlSessionFactory;
import com.example.allDemo.commons.*;
@Configuration//这是配置类的注解
@MapperScan(basePackages = "com.example.allDemo.dao")//用注解表示要去扫描的dao层
public class MybatisConfig extends BaseConfig {
    private static final String MAPPER_PATH = "mapper/*.xml";//这是映射文件的位置
    
    @Bean//产生一个方法bean，将bean交给spring 进行管理
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
        sfb.setDataSource(dataSource);
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + MAPPER_PATH;
        sfb.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSearchPath));
        SqlSessionFactory factory = sfb.getObject();
        factory.getConfiguration().setMapUnderscoreToCamelCase(true);
        return factory;
    }
}