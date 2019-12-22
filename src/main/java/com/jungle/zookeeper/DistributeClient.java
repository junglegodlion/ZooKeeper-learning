package com.jungle.zookeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class DistributeClient {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

		DistributeClient client = new DistributeClient();

		// 1 获取zookeeper集群连接
		client.getConnect();

		// 2 注册监听
		client.getChlidren();

		// 3 业务逻辑处理
		client.business();

	}

	/**
	 * 业务逻辑处理
	 * @throws InterruptedException
	 */
	private void business() throws InterruptedException {
		Thread.sleep(Long.MAX_VALUE);
	}


	/**
	 * 注册监听
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	private void getChlidren() throws KeeperException, InterruptedException {

		List<String> children = zkClient.getChildren("/servers", true);

		// 存储服务器节点主机名称集合
		ArrayList<String> hosts = new ArrayList<>();

		for (String child : children) {

			byte[] data = zkClient.getData("/servers/"+child, false, null);

			hosts.add(new String(data));
		}

		// 将所有在线主机名称打印到控制台
		System.out.println(hosts);

	}

	private String connectString = "192.168.1.18:2181";
	private int sessionTimeout = 2000;
	private ZooKeeper zkClient;

	/**
	 * 获取zookeeper集群连接
	 * @throws IOException
	 */
	private void getConnect() throws IOException {

		zkClient = new ZooKeeper(connectString , sessionTimeout , new Watcher() {

			@Override
			public void process(WatchedEvent event) {

				//动态监听节点变化
				try {
					getChlidren();
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

	}
}
