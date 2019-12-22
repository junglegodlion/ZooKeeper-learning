package com.jungle.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class DistributeServer {

	public static void main(String[] args) throws Exception {

		DistributeServer server = new DistributeServer();

		// 1 连接zookeeper集群
		server.getConnect();

		// 2 注册节点
		server.regist(args[0]);

		// 3 业务逻辑处理
		server.business();
	}


	/**
	 * 业务逻辑处理
	 * @throws InterruptedException
	 */
	private void business() throws InterruptedException {

		//为了进程不结束
		Thread.sleep(Long.MAX_VALUE);
	}


	/**
	 * 注册节点
	 * @param hostname
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	private void regist(String hostname) throws KeeperException, InterruptedException {

		String path = zkClient.create("/servers/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

		System.out.println(hostname +"is online ");

	}

	private String connectString = "192.168.1.18:2181";
	private int sessionTimeout = 2000;
	private ZooKeeper zkClient;

	/**
	 * 连接zookeeper集群
	 * @throws IOException
	 */
	private void getConnect() throws IOException {

		zkClient = new ZooKeeper(connectString , sessionTimeout , new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub

			}
		});
	}
}
