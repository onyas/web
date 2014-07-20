package com.onyas.oa.cfg;

public class Configuration {

	private static int pageSize;
	
	static{
		//TODO:读取配置文件(default.properties)，读取属性的值
		pageSize=10;
	}
	
	public static int getPageSize() {
		return pageSize;
	}

}
