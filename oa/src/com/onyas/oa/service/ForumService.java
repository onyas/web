package com.onyas.oa.service;

import com.onyas.oa.base.BaseDao;
import com.onyas.oa.domain.Forum;

public interface ForumService extends BaseDao<Forum> {

	/**
	 * 上移，当在最上面时不能上移
	 * @param id
	 */
	void moveUp(long id);

	/**
	 * 下移，当在最下面是不能下移
	 * @param id
	 */
	void moveDown(long id);

}
