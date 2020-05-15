package com.multidatasource.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.multidatasource.mapper.QueryMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DynamicDataSourceConfig {

	private static final String DATA_SOURCE = "dataSource";
	private static final String SESSION_FACTORY = "sessionFactory";
	private static final String MAPPER_LOCATION = "classpath:mappers/**/*.xml";
	private static final String DOMAIN_OBJECTS = "com.multidatasource.model";

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.upm-dev")
	public DataSource upmDevDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.employees")
	public DataSource employeesDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.cities")
	public DataSource citiesDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Autowired
	@Bean(name = DATA_SOURCE, destroyMethod = "")
	public DataSource dataSource() {
		DynamicRoutingDataSource dynamicDataSource = new DynamicRoutingDataSource();
		dynamicDataSource.setTargetDataSources(getDataSourceMap());
		return dynamicDataSource;
	}

	protected Map<Object, Object> getDataSourceMap() {
		Map<Object, Object> dataSourceMap = new HashMap<>();
		dataSourceMap.put(DataSourceMapping.UPM_DEV_DATABASE, upmDevDataSource());
		dataSourceMap.put(DataSourceMapping.EMPLOYEE_DATABASE, employeesDataSource());
		dataSourceMap.put(DataSourceMapping.CITIES_DATABASE, citiesDataSource());
		log.info("====dataSourceMap size =====" + dataSourceMap.size());
		return dataSourceMap;
	}

	@Bean(name = SESSION_FACTORY, destroyMethod = "")
	public SqlSessionFactoryBean sqlSessionFactory(@Named(DATA_SOURCE) final DataSource dataSource) throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources(MAPPER_LOCATION));
		sqlSessionFactoryBean.setTypeAliasesPackage(DOMAIN_OBJECTS);
		return sqlSessionFactoryBean;
	}

	@Bean
	public MapperFactoryBean<QueryMapper> queryMapper(
			@Named(SESSION_FACTORY) final SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
		MapperFactoryBean<QueryMapper> factoryBean = new MapperFactoryBean<>(QueryMapper.class);
		factoryBean.setSqlSessionFactory(sqlSessionFactoryBean.getObject());
		factoryBean.getSqlSessionFactory().getConfiguration().setMapUnderscoreToCamelCase(true);
		return factoryBean;
	}

}
