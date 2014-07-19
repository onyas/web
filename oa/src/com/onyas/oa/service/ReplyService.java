package com.onyas.oa.service;

import java.util.List;

import com.onyas.oa.base.BaseDao;
import com.onyas.oa.domain.Reply;
import com.onyas.oa.domain.Topic;

public interface ReplyService extends BaseDao<Reply> {

	/** 查询指定主题中所有的回复列表，排序：最前面的是最早的回贴*/
	List<Reply> findByTopic(Topic topic);

}
