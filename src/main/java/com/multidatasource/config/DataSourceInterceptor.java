package com.multidatasource.config;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataSourceInterceptor extends HandlerInterceptorAdapter {

	private YamlConfig config;

	public DataSourceInterceptor(YamlConfig config) {
		this.config = config;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader(DataSourceMapping.API_AUTH_TOKEN_NAME);
		if (StringUtils.isNotBlank(token)) {
			int clientId = getBuyerIdFromHeaderApiToken(token);
			log.info("=====clientId=====" + clientId);
			String dataSource = DataSourceMapping.getDataSourceByClientId(clientId);
			log.info("=====selected datasource=====" + dataSource);
			request.setAttribute(DataSourceMapping.DATA_SOURCE_KEY, dataSource);
		}
		return true;
	}

	private int getBuyerIdFromHeaderApiToken(String token) throws AuthenticationException {
		if (config.getClientTokens().get(token) == null) {
			throw new AuthenticationException("invalid token");
		}
		return config.getClientTokens().get(token);
	}

}
