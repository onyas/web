package com.search.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.api.client.http.GenericUrl;

public class SolrEngine {

	private static final Logger log = LoggerFactory.getLogger(SolrEngine.class);

	private String host;

	public SolrEngine(String host) {
		this.host = host;
	}

	/**
	 * 对指定核更新索引(包括 新增，更新，删除索引)
	 * 
	 * @param coreName
	 *            核的名字
	 * @param data
	 *            数据 <br/>
	 *            新增/更新索引:<br/>
	 *            "{\"add\":{\"doc\":{\"id_key\":\"asdf\",\"title_text\":\"test1\",\"content_text\":\"test1\"}}}"
	 * <br/>
	 *            删除索引:"{\"delete\":{\"id\":\"addf\"}}"
	 * @return responseCode 200为成功，其它为失败
	 */
	public int updateIndex(String coreName, JSONObject data) {
		// 构造请求url
		StringBuffer requestUrl = new StringBuffer();
		requestUrl.append(host);
		requestUrl.append(coreName);
		requestUrl.append("/update/json?commit=true");

		int responseCode = 0;
		JSONObject result = new JSONObject();
		log.info("SolrEngine requestUrl::{},data::{}", requestUrl.toString(),
				data.toJSONString());
		try {
			System.out.println(requestUrl.toString());
			GenericUrl url = new GenericUrl(requestUrl.toString());
			result = HttpHelper.requestPostJSON(url, data, 5000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("responseCode==" + responseCode);
		System.out.println("sbResult==" + result.toJSONString());
		return responseCode;
	}

	/***
	 * 搜索
	 * 
	 * @param coreName
	 *            核的名字
	 * @param param
	 *            搜索参数
	 * @return 返回值可以是json或xml格式的数据，根据查询时指定的wt选项来定
	 * @throws IOException
	 */
	public JSONObject search(String coreName, String param) {
		// 构造请求url
		StringBuffer requestUrl = new StringBuffer();
		requestUrl.append(host);
		requestUrl.append(coreName);
		requestUrl.append("/select?");
		requestUrl.append(param);
		log.info("SolrEngine requestUrl::{}", requestUrl.toString());
		GenericUrl url = new GenericUrl(requestUrl.toString());
		JSONObject result = new JSONObject();
		try {
			result = HttpHelper.requestGetJSON(url, 6000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
