package com.abysscat.usercenter.base.asynctask.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.abysscat.usercenter.base.asynctask.FutureService;

import lombok.extern.slf4j.Slf4j;

/**
 * 公用异步类封装
 */
@Slf4j
@Service("commonFutureService")
public class CommonFutureServiceImpl implements FutureService {

    @Value("${usercenter.executorService.common.coreSize:40}")
    private Integer coreSize;

    @Value("${usercenter.executorService.common.maxSize:80}")
    private Integer maxSize;

    @Value("${usercenter.executorService.common.queueCap:20}")
    private Integer queueCap;

    @Value("${usercenter.executorService.common.keepAliveSeconds:60}")
    private Integer keepAliveSeconds;

    @Value("${usercenter.executorService.common.threadNamePrefix:common-async-task-}")
    private String threadNamePrefix;

    private ThreadPoolTaskExecutor commonTaskExecutor;

    /**
     * 通用线程池
     */
    @PostConstruct
    public void commonTaskExecutor() {
        commonTaskExecutor = new ThreadPoolTaskExecutor();
        commonTaskExecutor.setCorePoolSize(coreSize);
        commonTaskExecutor.setMaxPoolSize(maxSize);
        commonTaskExecutor.setQueueCapacity(queueCap);
        commonTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        commonTaskExecutor.setThreadNamePrefix(threadNamePrefix);

        // 需要catch此异常，根据异常情况判断是否增大线程池
        commonTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 初始化
        commonTaskExecutor.initialize();
    }

    @Override
    public <T> CompletableFuture<T> doAsyncTask(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, commonTaskExecutor);
    }

    @Override
    public CompletableFuture<Void> runAsyncTask(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, commonTaskExecutor);
    }
}
