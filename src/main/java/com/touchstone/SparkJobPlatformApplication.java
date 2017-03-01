package com.touchstone;

import com.touchstone.util.Constant;
import com.touchstone.util.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@ServletComponentScan
@MapperScan("com.touchstone.dao")
public class SparkJobPlatformApplication {

	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		return new org.apache.tomcat.jdbc.pool.DataSource();
	}

//	@Bean
//	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
//
//		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//		sqlSessionFactoryBean.setDataSource(dataSource());
//
//		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//
//		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));
//
//		return sqlSessionFactoryBean.getObject();
//	}
//
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	public static void main(String[] args) {
        final ApplicationContext applicationContext = SpringApplication.run(SparkJobPlatformApplication.class, args);
        // 保存ApplicationContext
        SpringUtils.setApplicationContext(applicationContext);
		Constant.logger.info("[SparkJobPlatformApplication] SpringBoot Start Success");
	}
}
