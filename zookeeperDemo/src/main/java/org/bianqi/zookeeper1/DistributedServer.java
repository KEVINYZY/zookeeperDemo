package org.bianqi.zookeeper1;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
/**
 * 上下线感知
 * <p>Title: DistributedServer</p>
 * <p>Description: </p>
 * <p>School: qiqihar university</p> 
 * @author	BQ
 * @date	2017年4月23日下午4:33:54
 * @version 1.0
 */
public class DistributedServer {
	private static final String connectString = "192.168.154.130:2181,192.168.154.131:2181,192.168.154.132:2181";
	private static final int sessionTimeout = 2000;
	private static final String PARENT_NODE ="/servers";
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
					zkClient.getChildren("/", true);
				} catch (Exception e) {

				}
			}
		});
	}
	/**
	 * 注册服务器信息
	 * <p>Title: registerServer</p>
	 * <p>Description: </p>
	 * @param hostname
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void registerServer(String hostname) throws KeeperException, InterruptedException{
		String path = zkClient.create(PARENT_NODE+"/server",hostname.getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(hostname+" is online ...."+path);
	}
	
	
	/**
	 * 处理业务
	 * <p>Title: handleBusiness</p>
	 * <p>Description: </p>
	 * @throws InterruptedException 
	 */
	public void handleBusiness(String hostname) throws InterruptedException{
		System.out.println(hostname+"server start working ....");
		Thread.sleep(Long.MAX_VALUE);
	}
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		DistributedServer server = new DistributedServer();
		server.connect();
		server.registerServer("bianqi");
		//业务
		server.handleBusiness("bianqi");
	}
}












