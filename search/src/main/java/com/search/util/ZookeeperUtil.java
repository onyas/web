package com.search.util;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class ZookeeperUtil {
	private static Logger logger = Logger.getLogger(ZookeeperUtil.class);

	/**
	 * 完整的zookeeper读写例子，供参考
	 * 
	 * @throws KeeperException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void index() throws KeeperException, InterruptedException,
			IOException {
		ZooKeeper zk = new ZooKeeper("localhost:" + "2181", 300000,
				new Watcher() {
					// 监控所有被触发的事件
					public void process(WatchedEvent event) {
						System.out.println("已经触发了" + event.getType() + "事件！");
					}
				});
		// 创建一个目录节点
		zk.create("/testRootPath", "testRootData".getBytes(),
				Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		// 创建一个子目录节点
		zk.create("/testRootPath/testChildPathOne", "testChildDataOne"
				.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out
				.println(new String(zk.getData("/testRootPath", false, null)));
		// 取出子目录节点列表
		System.out.println(zk.getChildren("/testRootPath", true));
		// 修改子目录节点数据
		zk.setData("/testRootPath/testChildPathOne", "modifyChildDataOne"
				.getBytes(), -1);
		System.out.println("目录节点状态：[" + zk.exists("/testRootPath", true) + "]");
		// 创建另外一个子目录节点
		zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo"
				.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(new String(zk.getData(
				"/testRootPath/testChildPathTwo", true, null)));
		// 删除子目录节点
		zk.delete("/testRootPath/testChildPathTwo", -1);
		zk.delete("/testRootPath/testChildPathOne", -1);
		// 删除父目录节点
		zk.delete("/testRootPath", -1);
		// 关闭连接
		zk.close();
	}

	/**
	 * 写数据
	 * 
	 * @throws IOException
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public static void write(String path, String data) throws IOException,
			KeeperException, InterruptedException {
		ZooKeeper zk = new ZooKeeper("localhost:" + "2181", 300000,
				new Watcher() {
					// 监控所有被触发的事件
					public void process(WatchedEvent event) {
						System.out.println("已经触发了" + event.getType() + "事件！");
					}
				});
		String[] paths = path.split("/");
		path = "/";
		if (!StringUtils.isEmpty(paths[1])) {
			path = path+ paths[1];
			if (zk.exists(path, true) != null) {
				path = path + "/";

			} else {
				// 创建一个目录节点
				zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE,
						CreateMode.PERSISTENT);
			}
		}
		for (int i = 2; i < paths.length; i++) {
			if (StringUtils.isEmpty(paths[i])) {
				continue;
			}
			// 判断节点是否存在
			path = path+ paths[i];
			if (zk.exists(path, true) != null) {
				path = path + "/";
				continue;
			} else {
				// 创建另外一个子目录节点
				zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE,
						CreateMode.PERSISTENT);
			}
		}

		// 关闭连接
		zk.close();
	}

	public static String read(String path) throws IOException, KeeperException,
			InterruptedException {
		String result = "";
		ZooKeeper zk = new ZooKeeper("localhost:" + "2181", 300000,
				new Watcher() {
					// 监控所有被触发的事件
					public void process(WatchedEvent event) {
						System.out.println("已经触发了" + event.getType() + "事件！");
					}
				});
		// 判断节点是否存在
		if (zk.exists(path, true) != null) {
			result = new String(zk.getData(path, false, null));
		} else {
			result = null;
		}
		// 关闭连接
		zk.close();
		return result;
	}

	public static void delete(String path) throws IOException, KeeperException,
			InterruptedException {
		ZooKeeper zk = new ZooKeeper("localhost:" + "2181", 300000,
				new Watcher() {
					// 监控所有被触发的事件
					public void process(WatchedEvent event) {
						System.out.println("已经触发了" + event.getType() + "事件！");
					}
				});

		// 删除子目录节点
		zk.delete(path, -1);
		// 删除父目录节点
		// 关闭连接
		zk.close();
	}

}
