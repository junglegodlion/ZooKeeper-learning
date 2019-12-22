package com.jungle.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class TestZookeeper {

	private String connectString = "192.168.1.18:2181";
	private int sessionTimeout = 2000;
	private ZooKeeper zkClient;

	/**
	 * 创建ZooKeeper客户端
	 * @throws IOException
	 */
	@Before
	public void init() throws IOException {

		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
//
//				System.out.println("---------start----------");
//				List<String> children;
//				try {
//					children = zkClient.getChildren("/", true);
//
//					for (String child : children) {
//						System.out.println(child);
//					}
//					System.out.println("---------end----------");
//				} catch (KeeperException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		});
	}

	/**
	 * 1.创建子节点
	 * @throws Exception
	 */
	@Test
	public void create() throws Exception {

		// 参数1：要创建的节点的路径； 参数2：节点数据 ； 参数3：节点权限 ；参数4：节点的类型
		String nodeCreated = zkClient.create("/atguigu", "jinlian".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		System.out.println(nodeCreated);
	}

	/**
	 * 2 获取子节点 并监控节点的变化
	 * @throws Exception
	 */
	@Test
	public void getChildren() throws Exception {

		//true 表示设置为监听
		List<String> children = zkClient.getChildren("/", true);

		for (String child : children) {
			System.out.println(child);
		}

		// 延时阻塞
		// 保持程序不停止
		// 目的:节点变换，及时反映
		Thread.sleep(Long.MAX_VALUE);
	}

	// 3.判断znode是否存在
	@Test
	public void exist() throws Exception {

		Stat stat = zkClient.exists("/eclipse", false);

		System.out.println(stat == null ? "not exist" : "exist");
	}

}
