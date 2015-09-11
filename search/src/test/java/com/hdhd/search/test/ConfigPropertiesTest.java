package com.hdhd.search.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.search.util.ConfigProperties;

public class ConfigPropertiesTest {
	private static final Logger log = LoggerFactory
			.getLogger(ConfigPropertiesTest.class);

	@Test
	public void testConfigProperties() throws Exception {
		String hosts[] = ConfigProperties
				.getPropertiesArrayValue("solr.servers");
		for (int i = 0; i < hosts.length; i++) {
			log.info("config::{}", hosts[i]);
		}
	}
}
