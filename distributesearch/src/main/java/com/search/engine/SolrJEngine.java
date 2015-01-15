package com.search.engine;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.cloud.ClusterState;
import org.apache.solr.common.cloud.ZkStateReader;

public class SolrJEngine {
	
	private static CloudSolrServer cloudSolrServer;
	public  static synchronized CloudSolrServer getCloudSolrServer(final String zkHost) {
		if(cloudSolrServer == null) {
			try {
				cloudSolrServer = new CloudSolrServer(zkHost);
			}catch(MalformedURLException e) {
				System.out.println("The URL of zkHost is not correct!! Its form must as below:\n zkHost:port");
				e.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();				
			}
		}
		return cloudSolrServer;
	}
	
	/***
	 * 添加索引
	 * @param solrServer
	 */
	public static void addIndex(SolrServer solrServer) {
		try {		
			SolrInputDocument doc1 = new SolrInputDocument();	
			
			//在schema.xml中有相应的field,并且要有相应的uniquekey 
			doc1.addField("id", UUID.randomUUID());
			doc1.addField("titel","test8");
			doc1.addField("content","test8");
			
			SolrInputDocument doc2 = new SolrInputDocument();
			doc2.addField("id", UUID.randomUUID());
			doc2.addField("name", "刘俊1");
	
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			docs.add(doc1);
			docs.add(doc2);
			
			solrServer.add(docs);			
		}catch(SolrServerException e) {
			System.out.println("Add docs Exception !!!");
			e.printStackTrace();		
		}catch(IOException e){
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Unknowned Exception!!!!!");
			e.printStackTrace();
		}		
	}
	
	/**
	 * 根据查询参数进行搜索
	 * @param solrServer 
	 * @param query 查询参数,solr语法
	 */
	public static void search(SolrServer solrServer, String query) {		
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(query);
		try {
			QueryResponse response = solrServer.query(solrQuery);
			SolrDocumentList docs = response.getResults();

			System.out.println("文档个数：" + docs.getNumFound());
			System.out.println("查询时间：" + response.getQTime());

			for (SolrDocument doc : docs) {
				String name = (String) doc.getFieldValue("name");
				String id = (String) doc.getFieldValue("id");
				System.out.println("id: " + id);
				System.out.println("name: " + name);
				System.out.println();
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Unknowned Exception!!!!");
			e.printStackTrace();
		}
	}
	
	/***
	 * 把solr中搜索到的删除掉
	 * @param solrServer
	 * @param query 查询参数,solr语法
	 */
    public static void deleteIndexByQuery(SolrServer solrServer,String query) {
    	try {
    		solrServer.deleteByQuery("*:*");// delete everything!
    		solrServer.commit();
    	}catch(SolrServerException e){
    		e.printStackTrace();
    	}catch(IOException e) {
    		e.printStackTrace();
    	}catch(Exception e) {
    		System.out.println("Unknowned Exception !!!!");
    		e.printStackTrace();
    	}
    }
	
	public static void main(String[] args) {		
		//TODO 修改相应的地址与端口
	    final String zkHost = "10.142.51.186:2181";		
		final String  defaultCollection = "core_004";
		final int  zkClientTimeout = 20000;
		final int zkConnectTimeout = 1000;
		
		CloudSolrServer cloudSolrServer = getCloudSolrServer(zkHost);	    
	    System.out.println("The Cloud SolrServer Instance has benn created!");
	    
	    cloudSolrServer.setDefaultCollection(defaultCollection);
	    cloudSolrServer.setZkClientTimeout(zkClientTimeout);
	    cloudSolrServer.setZkConnectTimeout(zkConnectTimeout);  
	    
		cloudSolrServer.connect();
		System.out.println("The cloud Server has been connected !!!!");
		
		ZkStateReader zkStateReader = cloudSolrServer.getZkStateReader();
		ClusterState cloudState  = zkStateReader.getClusterState();
		System.out.println(cloudState);
		
		//测试实例！
		System.out.println("测试添加index！！！");		
		//添加index
		SolrJEngine.addIndex(cloudSolrServer);
		
		System.out.println("测试查询query！！！！");
		SolrJEngine.search(cloudSolrServer, "id:*");
		
		System.out.println("测试删除！！！！");
//		test.deleteAllIndex(cloudSolrServer);
		
		System.out.println("删除所有文档后的查询结果：");
//		test.search(cloudSolrServer, "*:*");	
		 // release the resource 
	    cloudSolrServer.shutdown();
	}
}
