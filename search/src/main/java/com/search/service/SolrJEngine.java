package com.search.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.cloud.ClusterState;
import org.apache.solr.common.cloud.ZkStateReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.search.util.ConfigProperties;

public class SolrJEngine {
	private static final Logger log = LoggerFactory
			.getLogger(SolrJEngine.class);

	private static CloudSolrServer cloudSolrServer;

	public static synchronized CloudSolrServer getCloudSolrServer(
			final String zkHost) {
		if (cloudSolrServer == null) {
			try {
				cloudSolrServer = new CloudSolrServer(zkHost);
				cloudSolrServer.setIdField("sid");// CloudSolrServer根据该字段来路由
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cloudSolrServer;
	}

	/***
	 * 添加索引
	 * 
	 * @param solrServer
	 */
	public static void addIndex() {
		try {
			SolrInputDocument doc1 = new SolrInputDocument();

			// 在schema.xml中有相应的field,并且要有相应的uniquekey
			doc1.addField("id", UUID.randomUUID());
			doc1.addField("titel", "test8");
			doc1.addField("content", "test8");

			SolrInputDocument doc2 = new SolrInputDocument();
			doc2.addField("id", UUID.randomUUID());
			doc2.addField("name", "刘俊1");

			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			docs.add(doc1);
			docs.add(doc2);

			cloudSolrServer.add(docs);
			cloudSolrServer.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 更新地理位置
	 * 
	 * @param solrServer
	 */
	public static void updateLocation() {
		try {
			SolrInputDocument doc1 = new SolrInputDocument();
			Map<String, String> partialUpdate = new HashMap<String, String>();
			partialUpdate.put("set", "39.991861,116.424724");
			doc1.addField("sid", "1273221244685312");
			doc1.addField("store", partialUpdate);
			cloudSolrServer.add(doc1);
			cloudSolrServer.commit();
			log.info("update success sid::{} store::{}");
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据查询参数进行搜索
	 * 
	 * @param solrServer
	 * @param query
	 *            查询参数,solr语法
	 */
	public static void geoSearch() {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*:*").setStart(0).setRows(100)
				.addFilterQuery("{!geofilt}")
				.setParam("pt", "39.991861,116.424724")
				.setParam("sfield", "store").setParam("d", "1")
				.setFields("distance:geodist()", "*")
				.addSort("geodist()", SolrQuery.ORDER.asc);
		solrQuery.setParam("shards.tolerant", true);
		try {
			QueryResponse response = cloudSolrServer.query(solrQuery,
					METHOD.POST);
			SolrDocumentList docs = response.getResults();

			log.info("文档个数：" + docs.getNumFound());
			log.info("查询时间：" + response.getQTime());

			for (SolrDocument doc : docs) {
				String username = (String) doc.getFieldValue("username");
				String id = (String) doc.getFieldValue("id");
				String sid = (String) doc.getFieldValue("sid");
				String mobile = (String) doc.getFieldValue("mobile");
				Double distance = (Double) doc.getFieldValue("distance");
				String store = (String) doc.getFieldValue("store");
				Integer radius = (Integer) doc.getFieldValue("radius");
				log.info(
						"id::{} sid::{} username::{} mobile::{} distance::{} store::{} radius::{}",
						id, sid, username, mobile, distance * 1000, store,
						radius);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			log.info("Unknowned Exception!!!!");
			e.printStackTrace();
		}
	}

	/***
	 * 把solr中搜索到的删除掉
	 * 
	 * @param solrServer
	 * @param query
	 *            查询参数,solr语法
	 */
	public static void deleteIndexByQuery(SolrServer solrServer, String query) {
		try {
			solrServer.deleteByQuery("*:*");// delete everything!
			solrServer.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			log.info("Unknowned Exception !!!!");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO 修改相应的地址与端口
		final String defaultCollection = "db";
		final int zkClientTimeout = 20000;
		final int zkConnectTimeout = 1000;

		CloudSolrServer cloudSolrServer = getCloudSolrServer(ConfigProperties
				.getPropertyValue("ZK_CONNECT"));
		log.info("The Cloud SolrServer Instance has benn created!");

		cloudSolrServer.setDefaultCollection(defaultCollection);
		cloudSolrServer.setZkClientTimeout(zkClientTimeout);
		cloudSolrServer.setZkConnectTimeout(zkConnectTimeout);

		cloudSolrServer.connect();
		log.info("The cloud Server has been connected !!!!");

		ZkStateReader zkStateReader = cloudSolrServer.getZkStateReader();
		ClusterState cloudState = zkStateReader.getClusterState();
		log.info("cloudState::{}", cloudState);

		// 测试实例！
		log.info("测试添加index！！！");
		// 添加index
		SolrJEngine.addIndex();

		log.info("测试查询query！！！！");
		SolrJEngine.geoSearch();

		log.info("测试删除！！！！");
		// test.deleteAllIndex(cloudSolrServer);

		log.info("删除所有文档后的查询结果：");
		// test.search(cloudSolrServer, "*:*");
		// release the resource
		cloudSolrServer.shutdown();
	}

}
