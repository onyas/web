package com.onyas.oa.service;

import java.util.List;

import com.onyas.oa.base.BaseDao;
import com.onyas.oa.domain.Forum;
import com.onyas.oa.domain.PageBean;
import com.onyas.oa.domain.Topic;

public interface TopicService extends BaseDao<Topic> {

	/** 查询指定版块中的主题列表，排序:所有置顶贴都在最上面，然后把最新状态的主题显示到前面*/
	List<Topic> findByForum(Forum forum);

	/** 查询分面信息*/
	PageBean getPageBean(int pageNum, Forum forum);

}
