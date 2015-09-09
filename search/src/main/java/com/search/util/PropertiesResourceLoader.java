package com.search.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;

public final class PropertiesResourceLoader {
	private Logger log = LoggerFactory
			.getLogger(PropertiesResourceLoader.class);
	private static final String CONFIG_RESOURCE = "/config.properties";
	private Properties prop = new Properties();

	public static PropertiesResourceLoader instance;

	private PropertiesResourceLoader() {
	}

	private void postInit() {
		instance = new PropertiesResourceLoader();
		InputStream in = PropertiesResourceLoader.class
				.getResourceAsStream(CONFIG_RESOURCE);

		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Error load properties {}", CONFIG_RESOURCE, e);
		} finally {
			IOUtils.closeQuietly(in);
		}

	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}

	public Set<Object> keySet() {
		return prop.keySet();
	}

	public void destroy() {
		instance = null;
	}

}
