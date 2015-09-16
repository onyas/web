package com.hdhd.search.test;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer;
import org.apache.solr.common.cloud.ClusterState;
import org.apache.solr.common.cloud.ZkStateReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.search.service.SolrJEngine;

public class SolrJEngineTest {
	private static final Logger log = LoggerFactory
			.getLogger(SolrJEngineTest.class);

	private CloudSolrServer cloudSolrServer;

	@Before
	public void init() {
		// TODO 修改相应的地址与端口，如果有多个zk,中间用逗号分开
		final String zkHost = "127.0.0.1:2181";
		final String defaultCollection = "db";
		final int zkClientTimeout = 20000;
		final int zkConnectTimeout = 1000;

		cloudSolrServer = SolrJEngine.getCloudSolrServer(zkHost);
		log.info("The Cloud SolrServer Instance has benn created!");

		cloudSolrServer.setDefaultCollection(defaultCollection);
		cloudSolrServer.setZkClientTimeout(zkClientTimeout);
		cloudSolrServer.setZkConnectTimeout(zkConnectTimeout);

		cloudSolrServer.connect();
		log.info("The cloud Server has been connected !!!!");

		ZkStateReader zkStateReader = cloudSolrServer.getZkStateReader();
		ClusterState cloudState = zkStateReader.getClusterState();
		log.info("cloudState::{}",cloudState.toString());
	}

	@Test
	public void testAddIndex() throws Exception {
		SolrJEngine.addIndex(cloudSolrServer);
	}

	@Test
	public void testDeleteByQuery() throws Exception {
		SolrJEngine.deleteIndexByQuery(cloudSolrServer, "*:*");
	}

	@Test
	public void testSearch() throws Exception {
		//*:*&wt=json&start=0&rows=1000&fq={!geofilt}&pt=39.991861,116.424724&sfield=store&d=0.5&sort=geodist()%20asc&fl=distance:geodist(),*
		SolrJEngine.search(cloudSolrServer, "*:*");
	}

	@After
	public void destroy() {
		cloudSolrServer.shutdown();
	}
}