package com.codechiev.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class JobQueueUtil {
	//private static final Logger logger = LoggerFactory.getLogger(JobService.class);
	
	private BlockingQueue<IJob> jobQueue;
	
	public JobQueueUtil(){
		jobQueue = new LinkedBlockingQueue<IJob>();
	}
	
	@PostConstruct
	public void initSingleThread(){
		Consumer<IJob> c1 = new Consumer<IJob>(jobQueue);
		Thread thread = new Thread(c1);
		thread.setName("JobService-Thread");
		thread.start();
	}
	
	public void produce(IJob job){
		try {
			jobQueue.put(job);
		} catch (InterruptedException e) {
			
		}
	}
}

class Consumer<T extends IJob> implements Runnable {
	private final BlockingQueue<T> queue;

	Consumer(BlockingQueue<T> q) {
		queue = q;
	}

	public void run() {
		try {
			while (true) {
				T t=queue.take();
				t.doJob();
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
