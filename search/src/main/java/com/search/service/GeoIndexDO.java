package com.search.service;

import java.io.Serializable;
import java.util.Date;

public class GeoIndexDO implements Serializable {

	private static final long serialVersionUID = -6533271474450188855L;
	private String sid;
	private String id;
	private String openid;//
	private String username;//用户名
	private Date createtime;
	private String store;//叮当设置的经纬度
	private String lastAcceess;//上次下单的经纬度

	private String option;//是建索引还是删除索引

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getLastAcceess() {
		return lastAcceess;
	}

	public void setLastAcceess(String lastAcceess) {
		this.lastAcceess = lastAcceess;
	}

}
