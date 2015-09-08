package com.hdhd.search.test;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.cloud.ClusterState;
import org.apache.solr.common.cloud.ZkStateReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.search.service.SolrJEngine;

public class SolrJEngineTest {

	private CloudSolrServer cloudSolrServer;
	
	@Before
	public void init(){
		//TODO 修改相应的地址与端口
	    final String zkHost = "10.142.51.186:2181";		
		final String  defaultCollection = "core_004";
		final int  zkClientTimeout = 20000;
		final int zkConnectTimeout = 1000;
		
		cloudSolrServer = SolrJEngine.getCloudSolrServer(zkHost);	    
	    System.out.println("The Cloud SolrServer Instance has benn created!");
	    
	    cloudSolrServer.setDefaultCollection(defaultCollection);
	    cloudSolrServer.setZkClientTimeout(zkClientTimeout);
	    cloudSolrServer.setZkConnectTimeout(zkConnectTimeout);  
	    
		cloudSolrServer.connect();
		System.out.println("The cloud Server has been connected !!!!");
		
		ZkStateReader zkStateReader = cloudSolrServer.getZkStateReader();
		ClusterState cloudState  = zkStateReader.getClusterState();
		System.out.println(cloudState);
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
		SolrJEngine.search(cloudSolrServer, "*:*");
	}
	
	@After
	public void destroy(){
		cloudSolrServer.shutdown();
	}
}