package org.bianqi.zookeeper1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
/**
 * 上下线感知
 * <p>Title: DistributedClient</p>
 * <p>Description: </p>
 * <p>School: qiqihar university</p> 
 * @author	BQ
 * @date	2017年4月23日下午5:00:55
 * @version 1.0
 */
public class DistributedClient {

	private static final String connectString = "192.168.154.130:2181,192.168.154.131:2181,192.168.154.132:2181";
	private static final int sessionTimeout = 2000;
	private static final String PARENT_NODE = "/servers";
	private volatile List<String> serverList;
	ZooKeeper zkClient = null;

	/**
	 * 创建连接
	 * <p>Title: connect</p>
	 * <p>Description: </p>
	 * @throws IOException
	 */
	public void connect() throws IOException {
		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			public void process(WatchedEvent event) {
				try {
				} catch (Exception e) {
					try {
						getServerList();
					} catch (KeeperException e1) {
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	public void getServerList() throws KeeperException, InterruptedException {
		List<String> children = zkClient.getChildren(PARENT_NODE, true);
		ArrayList<String> servers = new ArrayList<String>();
		for (String child : children) {
			byte[] data = zkClient.getData(PARENT_NODE + "/" + child, false, null);
			servers.add(new String(data));
		}
		serverList = servers;
		System.out.println(serverList);
	}

	public void handleBusiness(String hostname) throws InterruptedException{
		System.out.println("client start working ");
		Thread.sleep(Long.MAX_VALUE);
	}
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		// 获取连接zookeeper连接
		DistributedClient client = new DistributedClient();
		client.connect();
		client.getServerList();
		client.handleBusiness("xxxx");
		// 获取server的子节点信息 获取服务器信息列表

	}
}
