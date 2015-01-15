package com.search.engine;


import java.io.IOException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class SolrEngineTest {

	private String coreName="core_test1_shard1_replica1";
	private SolrEngine solrEngine;
	
	@Before
	public void init(){
		solrEngine = new SolrEngine();
	}
	
	/***
	 * PASS
	 * 测试核的状态，可以查看核是否存在，核中文档数，等还有其它一些配置信息
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCoreStatus() throws IOException {
		solrEngine.coreStatus(coreName);
	}

	/***
	 * PASS
	 * 建核
	 * @throws IOException
	 */
	@Test
	public void testCreateCore() throws IOException{
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("numShards", "1");
		param.put("replicationFactor", "1");
		param.put("collection.configName", "clusterconf");
		solrEngine.createCore(coreName, param);
	}
	
	/***
	 * PASS
	 * 删除核
	 */
	@Test
	public void testDeleteCore(){
		solrEngine.deleteCore(coreName);
	}
	
	/***
	 * PASS
	 * 新增索引
	 */
	@Test
	public void testAddIndex(){
		//新增索引格式
		String data = "{\"add\":{\"doc\":{\"id_key\":\"addf\",\"title_text\":\"test1\",\"content_text\":\"test1\"}}}";
		solrEngine.updateIndex(coreName,data);
	}
	
	/***
	 * 更新指定ID的索引
	 */
	@Test
	public void testUpdateIndex(){
		//更新索引格式
		String data = "{\"add\":{\"doc\":{\"id_key\":\"addf\",\"title_text\":\"test1\",\"content_text\":\"test2\"}}}";
		solrEngine.updateIndex(coreName,data);
	}
	
	/***
	 * PASS
	 * 删除指定ID的索引
	 */
	@Test
	public void testDeleteIndexById(){
		//删除索引格式
		String data = "{\"delete\":{\"id\":\"addf\"}}";
		solrEngine.updateIndex(coreName,data);
	}
	
	/***
	 * PASS
	 * 测试搜索
	 */
	@Test
	public void testSearch(){
		String q = "q=*:*&wt=json";
		solrEngine.search(coreName, q);
	}
	
}
