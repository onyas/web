package com.search.request;

import org.springframework.beans.factory.annotation.Autowired;

import com.search.common.Constant;
import com.search.protocol.GeoIndexDO;
import com.search.service.SolrEngine;

public class GeoSearchDispatcher implements Dispatcher {
	String q = "q=*:*&wt=json&start=0&rows=1000&fq={!geofilt}&pt=%s&sfield=store&d=0.5&sort=geodist()%20asc&fl=distance:geodist(),*";// 搜索附近的人，通过fl同时返回距离

	@Autowired
	private SolrEngine solrEngine;

	public Object process(Object message) {
		GeoIndexDO geoIndex = (GeoIndexDO) message;
		String lanlng = geoIndex.getStore();
		q = String.format(q, lanlng);
		return solrEngine.search(Constant.GEOCORENAME, q);
	}

}
