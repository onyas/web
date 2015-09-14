package com.hdhd.search.test.util;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.search.util.ZookeeperUtil;

public class ZookeeperUtilTest {

	@Test
	public void testIndex() {
		fail("Not yet implemented");
	}

	@Test
	public void testWrite() {
		try {
			ZookeeperUtil.write("/aa/bb", "aabb");
			System.out.println("--");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}

	@Test
	public void testRead() {
		try {
			String reString=ZookeeperUtil.read("/live_nodes/192.168.2.197:8081_search");
			System.out.println(reString==null?"对应节点无数据":reString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDelete() {
		try {
			ZookeeperUtil.delete("/aa/bb");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}

}
