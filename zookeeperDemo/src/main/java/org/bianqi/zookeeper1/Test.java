package org.bianqi.zookeeper1;

/**
 * 守护线程 和 主线程  
 * 注: 主线程退出守护线程就退出
 *     守护线程退出主线程不一定退出
 * <p>Title: Test</p>
 * <p>Description: </p>
 * <p>School: qiqihar university</p> 
 * @author	BQ
 * @date	2017年4月23日下午5:11:40
 * @version 1.0
 */
public class Test {
	public static void main(String[] args) {
		System.out.println("开始了");
		Thread thread = new Thread(new Runnable() {
			public void run() {
				System.out.println("线程开始了");
				while(true){
					
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
}
