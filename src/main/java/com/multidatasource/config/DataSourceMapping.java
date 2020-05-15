package com.multidatasource.config;

import java.util.HashMap;
import java.util.Map;

public class DataSourceMapping {

	public static final String DATA_SOURCE_KEY = "dataSourceKey";
	public static final String API_AUTH_TOKEN_NAME = "API-AUTH-TOKEN";

	public static final String UPM_DEV_DATABASE = "upm-dev";
	public static final String EMPLOYEE_DATABASE = "employees";
	public static final String CITIES_DATABASE = "cities";

	protected static Map<Integer, String> dataSourceMap = new HashMap<>();

	static {
		dataSourceMap.put(1, UPM_DEV_DATABASE);
		dataSourceMap.put(2, EMPLOYEE_DATABASE);
		dataSourceMap.put(3, CITIES_DATABASE);
	}

	protected static String getDataSourceByClientId(int clientId) {
		return dataSourceMap.getOrDefault(clientId, UPM_DEV_DATABASE);
	}

}
