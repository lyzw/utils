package me.sapling.utils.common.tools.concurrent;

import me.sapling.utils.common.tools.collection.ListUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * { DESCRIBE HERE }
 *
 * @author zhouwei
 * @since 2022/3/5
 */
public class AsyncExecuteHelper {

//    public static <T> T executeAsyncInOrderAndGetResult(ExecutorService executorService, ExecuteSupplier<T> supplier, Supplier<T>... suppliers) {
//        CompletableFuture<T> completableFuture = new CompletableFuture<>();
//        Arrays.stream(suppliers).forEach(supplier -> {
//            completableFuture.thenAccept(supplier, executorService);
//        });
//    }

    public static <T> T executeAllOfAndGet(ExecutorService executorService, ExecuteSupplier<T> endSupplier, Supplier<T>... suppliers) {
        List<CompletableFuture<T>> future = Arrays.stream(suppliers).map(CompletableFuture::supplyAsync).collect(Collectors.toList());
        ListUtil
        CompletableFuture.allOf(future)
    }

    public interface ExecuteSupplier<T> {

        T supply();
    }
}

