package com.multidatasource.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String dataSourceKey = (String) request.getAttribute(DataSourceMapping.DATA_SOURCE_KEY);
		log.info("=====dataSourceKey from request=====" + dataSourceKey);
		if (dataSourceKey == null) {
			dataSourceKey = DataSourceMapping.UPM_DEV_DATABASE;
		}
		return dataSourceKey;
	}

}
