package com.hdhd.search.test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.search.service.GeoIndexBuilder;
import com.search.service.GeoIndexDO;
import com.search.service.SolrEngine;

public class SolrEngineTest {
	private static final Logger log = LoggerFactory
			.getLogger(SolrEngineTest.class);
	private String coreName = "db";
	private SolrEngine solrEngine;

	@Before
	public void init() {
		solrEngine = new SolrEngine("http://127.0.0.1:8081/search/");
	}

	/***
	 * PASS 测试核的状态，可以查看核是否存在，核中文档数，等还有其它一些配置信息
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCoreStatus() throws IOException {
		// solrEngine.coreStatus(coreName);
	}

	/***
	 * PASS 建核
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateCore() throws IOException {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("numShards", "1");
		param.put("replicationFactor", "1");
		param.put("collection.configName", "clusterconf");
		// solrEngine.createCore(coreName, param);
	}

	/***
	 * PASS 删除核
	 */
	@Test
	public void testDeleteCore() {
		// solrEngine.deleteCore(coreName);
	}

	/***
	 * PASS 新增索引
	 */
	@Test
	public void testAddIndex() {
		// 新增索引格式
		JSONObject data = new JSONObject();
		JSONObject doc = new JSONObject();
		JSONObject jsonData = new JSONObject();
		jsonData.put("sid", "999");
		jsonData.put("id", "999");
		jsonData.put("openid", "999");
		jsonData.put("username", "999");
		jsonData.put("mobile", 999);
		jsonData.put("createtime", new Date());
		jsonData.put("store", "39.991861,116.424724");
		doc.put("doc", jsonData);
		data.put("add", doc);
		int result = solrEngine.updateIndex(coreName, data);
		log.info("addIndex result::{}", result);
	}

	/***
	 * 更新指定ID的索引,可以更新部分字段的值,有add,set,inc三个指令,具体可参考指南中的Updating Parts of
	 * Documents
	 */
	@Test
	public void testAtomicUpdateIndex() {
		// 更新索引格式
		// {"add":{"doc":{"sid":"1274651996619776","store":{"set":"39.991861,116.424724"}}}}
		JSONObject jsondata = new JSONObject();
		JSONObject store = new JSONObject();
		store.put("set", "39.991861,116.424724");
		jsondata.put("sid", "1274651996619776");
		jsondata.put("store", store);
		JSONObject data = new JSONObject();
		JSONObject doc = new JSONObject();
		doc.put("doc", jsondata);
		data.put("add", doc);
		log.info("data::{}", data.toJSONString());
		solrEngine.updateIndex(coreName, data);
	}

	/***
	 * PASS 删除指定ID的索引
	 */
	@Test
	public void testDeleteIndexById() {
		// 删除索引格式
		String data = "{\"delete\":{\"id\":\"addf\"}}";
		// solrEngine.updateIndex(coreName, data);
	}

	/***
	 * PASS 测试搜索
	 */
	@Test
	public void testSearchDingdang() {
		// String q = "q=*:*&wt=json";
		// String q =
		// "q=*:*&wt=json&fq={!geofilt%20pt=39.991861,116.424724%20sfield=store%20d=5}";
		// String q =
		// "q=*:*&wt=json&fq={!geofilt}&pt=39.991861,116.424724&sfield=store&d=5&sort=geodist()%20asc";//
		// 搜索附近的人,通过距离排序
		// String q =
		// "q={!func}geodist()&wt=json&fq={!geofilt}&pt=39.991861,116.424724&sfield=store&d=5&sort=score+asc";//
		// 搜索附近的人,通过距离排序
		String q = "q=*:*&wt=json&start=0&rows=1000&fq={!geofilt}&pt=39.991861,116.424724&sfield=store&d=0.5&sort=geodist()%20asc&fl=distance:geodist(),*";// 搜索附近的人，通过fl同时返回距离
		JSONObject searchResult = solrEngine.search(coreName, q);
		if (searchResult.getIntValue("status") == 0) {
			JSONObject responseData = (JSONObject) searchResult
					.getJSONObject("response");
			int numFound = responseData.getIntValue("numFound");
			JSONArray docs = responseData.getJSONArray("docs");
			log.info("testSearchDingdang numFound" + numFound + " docs"
					+ docs.toJSONString());
			JSONArray dingdang = new JSONArray();
			if (docs != null && !docs.isEmpty()) {
				int radius;
				double distance;
				for (int i = 0; i < docs.size(); i++) {
					radius = ((JSONObject) docs.get(i)).getIntValue("radius");
					distance = ((JSONObject) docs.get(i))
							.getDoubleValue("distance");
					if (distance * 1000 < radius) {
						dingdang.add(docs.get(i));
					}
					log.info("radius::{} distance::{}", radius, distance);
				}
			}
			log.info("dingdang.size::{} docs.size::{}", dingdang.size(),
					docs.size());
		} else {
			// TODO error
		}
		System.out.println("test" + searchResult.toJSONString());
	}

	/***
	 * PASS 测试搜索
	 */
	@Test
	public void testSearchBuyer() {
		// String q = "q=*:*&wt=json";
		// String q =
		// "q=*:*&wt=json&fq={!geofilt%20pt=39.991861,116.424724%20sfield=store%20d=5}";
		String q = "q=*:*&wt=json&fq={!geofilt}&pt=39.991861,116.424724&sfield=buyer&d=5&sort=geodist()%20asc";// 搜索附近的人
		solrEngine.search(coreName, q);
	}

	/***
	 * PASS 更新指定ID的索引
	 */
	@Test
	public void testUpdateIndexByHttpHelper() {
		// 更新索引格式
		GeoIndexDO geoIndex = new GeoIndexDO();
		geoIndex.setSid("88888887");
		geoIndex.setId("888");
		geoIndex.setUsername("99999999");
		geoIndex.setOption("add");
		geoIndex.setLastAcceess("39.991861,116.424724");

		JSONObject data = new GeoIndexBuilder(geoIndex).createJSONOjbect();
		solrEngine.updateIndex(coreName, data);
	}

}