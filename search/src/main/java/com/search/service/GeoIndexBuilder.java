package com.search.service;

import org.apache.commons.lang.Validate;

import com.alibaba.fastjson.JSONObject;

public class GeoIndexBuilder {

	private GeoIndexDO geoIndex;
	private JSONObject data;
	private JSONObject doc;
	private JSONObject fields;

	public GeoIndexBuilder(GeoIndexDO geoIndex) {
		Validate.notNull(geoIndex);
		Validate.notNull(geoIndex.getSid());
		Validate.notNull(geoIndex.getOption());
		this.geoIndex = geoIndex;
		data = new JSONObject();
		doc = new JSONObject();
		fields = new JSONObject();
	}

	public JSONObject createJSONOjbect() {
		fields.put("sid", geoIndex.getSid());
		fields.put("id", geoIndex.getId());
		fields.put("openid", geoIndex.getOpenid());
		fields.put("username", geoIndex.getUsername());
		fields.put("store", geoIndex.getStore());
		fields.put("buyer", geoIndex.getLastAcceess());
		fields.put("createtime", geoIndex.getCreatetime());
		doc.put("doc", fields);
		data.put(geoIndex.getOption(), doc);
		return data;
	}

}
