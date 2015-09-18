package com.search.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.search.common.Constant;
import com.search.protocol.GeoIndexDO;
import com.search.service.SolrEngine;

public class GeoSearchDispatcher implements Dispatcher {
	private static final Logger log = LoggerFactory
			.getLogger(GeoSearchDispatcher.class);
	String q = "q=*:*&wt=json&start=0&rows=1000&fq={!geofilt}&pt=%s&sfield=store&d=%s&sort=geodist()%%20asc&fl=distance:geodist(),*";// 搜索附近的人，通过fl同时返回距离

	private SolrEngine solrEngine;

	public Object process(Object message) {
		GeoIndexDO geoIndex = (GeoIndexDO) message;
		if (geoIndex.getOption().equals("search")) {
			String param = String.format(q, geoIndex.getStore(),
					geoIndex.getRadius());
			log.info("GeoSearchDispatcher param::{}", param);
			return solrEngine.search(Constant.GEOCORENAME, param);
		} else if (geoIndex.getOption().equals("updateLocation")) {
			JSONObject jsondata = new JSONObject();
			JSONObject store = new JSONObject();
			store.put("set", geoIndex.getStore());
			jsondata.put("sid", geoIndex.getSid());
			jsondata.put("store", store);
			JSONObject data = new JSONObject();
			JSONObject doc = new JSONObject();
			doc.put("doc", jsondata);
			data.put("add", doc);
			log.info("data::{}", data.toJSONString());
			return solrEngine.updateIndex(Constant.GEOCORENAME, data);
		} else if (geoIndex.getOption().equals("add")) {
			return solrEngine.updateIndex(Constant.GEOCORENAME,
					geoIndex.getParam());
		}
		return null;
	}

	public void setSolrEngine(SolrEngine solrEngine) {
		this.solrEngine = solrEngine;
	}

}
