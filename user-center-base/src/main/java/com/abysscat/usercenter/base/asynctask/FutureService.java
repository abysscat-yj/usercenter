package com.abysscat.usercenter.base.asynctask;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * 异步任务封装接口
 */
public interface FutureService {

    <T> CompletableFuture<T> doAsyncTask(Supplier<T> supplier);

    CompletableFuture<Void> runAsyncTask(Runnable runnable);
}
