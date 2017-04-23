package org.bianqi.zookeeper1;

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

public class SimpleZkClient {
	private static final String connectString = "192.168.154.130:2181,192.168.154.131:2181,192.168.154.130:2181";
	private static final int sessionTimeout = 2000;
	ZooKeeper zkClient = null;

	@Before
	public void init() throws IOException {
		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			public void process(WatchedEvent event) {
				System.out.println(event.getType() + "==================" + event.getPath());
				try {
					zkClient.getChildren("/", true);
				} catch (Exception e) {

				}
			}
		});
	}

	/**
	 * 增删改查
	 */
	
	@Test
	public void testCreate() throws KeeperException, InterruptedException {
		String nodeCreate = zkClient.create("/bianqi", "hahah".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}
	
	
	@Test
	public void testChildern() throws KeeperException, InterruptedException{
		List<String> children = zkClient.getChildren("/", true);
		
		for (String string : children) {
			System.out.println(string);
		}
		Thread.sleep(Long.MAX_VALUE);
	}
	
	@Test
	public void testExist() throws KeeperException, InterruptedException{
		Stat exists = zkClient.exists("/bianqi", false);
		System.out.println(exists == null ? "not exist":"exist");
	}
	
	@Test
	public void getData() throws KeeperException, InterruptedException{
		byte[] data = zkClient.getData("/bianqi", false, null);
		System.out.println(new String(data));
	}
	
	@Test
	public void deleteZnode() throws InterruptedException, KeeperException{
//		zkClient.delete("/bianqi", -1);
		zkClient.setData("/bianqi", "xxxx".getBytes(), -1);
	}
	
	
}








