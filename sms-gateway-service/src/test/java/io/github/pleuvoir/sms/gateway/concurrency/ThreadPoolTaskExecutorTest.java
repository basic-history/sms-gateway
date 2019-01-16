package io.github.pleuvoir.sms.gateway.concurrency;

import java.time.LocalDate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import io.github.pleuvoir.sms.gateway.BaseTest;

public class ThreadPoolTaskExecutorTest extends BaseTest{

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@Test
	public void test() {
		threadPoolTaskExecutor.execute(()->{
			System.out.println(Thread.currentThread().getName() + "=" + LocalDate.now());
		});
	}
}
