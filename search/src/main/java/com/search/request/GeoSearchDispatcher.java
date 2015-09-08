package com.search.request;

import org.springframework.beans.factory.annotation.Autowired;

import com.search.common.Constant;
import com.search.protocol.GeoIndexDO;
import com.search.service.SolrEngine;

public class GeoSearchDispatcher implements Dispatcher {

	@Autowired
	private SolrEngine solrEngine;

	public Object process(Object message) {
		GeoIndexDO geoIndex = (GeoIndexDO) message;
		String method = geoIndex.getOption();
		String lanlng = geoIndex.getStore();
		String q = "q=*:*&wt=json&fq={!geofilt}&pt=39.991861,116.424724&sfield=store&d=0.5&sort=geodist()%20asc&fl=_dist_:geodist(),*";// 搜索附近的人，通过fl同时返回距离
		return solrEngine.search(Constant.GEOCORENAME, q);
	}

}
