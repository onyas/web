package com.search.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.search.common.Constant;
import com.search.protocol.GeoIndexDO;
import com.search.service.SolrEngine;

public class GeoSearchDispatcher implements Dispatcher {
	private static final Logger log = LoggerFactory
			.getLogger(GeoSearchDispatcher.class);
	String q = "q=*:*&wt=json&start=0&rows=1000&fq={!geofilt}&pt=%s&sfield=store&d=0.5&sort=geodist()%%20asc&fl=distance:geodist(),*";// 搜索附近的人，通过fl同时返回距离

	private SolrEngine solrEngine;

	public Object process(Object message) {
		GeoIndexDO geoIndex = (GeoIndexDO) message;
		String lanlng = geoIndex.getStore();
		String param = String.format(q, lanlng);
		log.info("GeoSearchDispatcher param::{}",param);
		return solrEngine.search(Constant.GEOCORENAME, param);
	}

	public void setSolrEngine(SolrEngine solrEngine) {
		this.solrEngine = solrEngine;
	}

}
