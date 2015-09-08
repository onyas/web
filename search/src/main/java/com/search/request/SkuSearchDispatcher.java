package com.search.request;

import org.springframework.beans.factory.annotation.Autowired;

import com.search.service.SolrEngine;

public class SkuSearchDispatcher implements Dispatcher {

	@Autowired
	private SolrEngine solrEngine;

	public Object process(Object message) {
		return null;
	}


}
