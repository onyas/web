package com.hdhd.search.test;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.hdhd.search.GeoIndexBuilder;
import com.hdhd.search.GeoIndexDO;
import com.hdhd.search.SolrEngine;

public class SolrEngineTest {

	private String coreName = "db";
	private SolrEngine solrEngine;

	@Before
	public void init() {
		solrEngine = new SolrEngine();
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
		String data = "{\"add\":{\"doc\":{\"id_key\":\"addf\",\"title_text\":\"test1\",\"content_text\":\"test1\"}}}";
		// solrEngine.updateIndex(coreName, data);
	}

	/***
	 * 更新指定ID的索引
	 */
	@Test
	public void testUpdateIndex() {
		// 更新索引格式
		String data = "{\"add\":{\"doc\":{\"id_key\":\"addf\",\"title_text\":\"test1\",\"content_text\":\"test2\"}}}";
		// solrEngine.updateIndex(coreName, data);
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
		String q = "q=*:*&wt=json&fq={!geofilt}&pt=39.991861,116.424724&sfield=store&d=0.5&sort=geodist()%20asc&fl=_dist_:geodist(),*";// 搜索附近的人，通过fl同时返回距离
		solrEngine.searchByHttpHelper(coreName, q);
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
		solrEngine.searchByHttpHelper(coreName, q);
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
		solrEngine.updateIndexByHttpHelper(coreName, data);
	}

}