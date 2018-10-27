package com.ziggy192.coursesource.crawler;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class BaseThread extends Thread {
	public static int THREAD_LIMIT = 10;

	private static final Object LOCK = new Object();
	private static BaseThread instance;

	private ThreadPoolExecutor executor;
	private BaseThread() {
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_LIMIT);
	}


	public ThreadPoolExecutor getExecutor() {
		return executor;
	}



	public static BaseThread getInstance() {
		synchronized (LOCK) {
			if (instance == null) {
				instance = new BaseThread();

			}
		}
		return instance;
	}

	private boolean suspended = false;

	public boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(boolean isSuspended) {
		this.suspended = isSuspended;
	}



	public void suspendThread() {
		setSuspended(true);
		System.out.println("Suspended all threads");
	}


	//it's synchronized because it must be under the same monitor to notifyAll(), by synchronized it make THIS became the monitor
	// same as synchronized(BaseThread.getDefault()) { notifyAll();}
	public synchronized void resumeThread() {
		setSuspended(false);
		notifyAll();
		System.out.println("Resume all threads");
	}


}
