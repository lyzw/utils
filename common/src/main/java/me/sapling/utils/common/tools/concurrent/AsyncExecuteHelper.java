package me.sapling.utils.common.tools.concurrent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 异步执行辅助类
 * <div>
 * executeThenConsume示例：
 * <div>
 *         Executor executor = Executors.newSingleThreadExecutor();
 *         Function<Supplier<String>, Supplier<String>> wrapper1 = (f) -> {
 *             System.out.println("wrapper 1 ....");
 *             return () -> f.get() + " wrapper1 ";
 *         };
 *         Function<Supplier<String>, Supplier<String>> wrapper2 = (f) -> {
 *             System.out.println("wrapper 2 ....");
 *             return () -> f.get() + " wrapper2 ";
 *         };
 *         executeThenConsume(executor, () -> {
 *             System.out.println("inner.....");
 *             return "22";
 *         }, System.out::println, wrapper1, wrapper2);
 * </div>
 * </div>
 *
 * @author zhouwei
 * @since 2022/3/5
 */
public class AsyncExecuteHelper {

    public static <R> CompletableFuture<R> execute(Executor executor, Supplier<R> supplier) {
        return execute(executor, supplier, null);
    }

    public static <R> CompletableFuture<R> execute(Executor executor, Supplier<R> supplier, Function<Supplier<R>, Supplier<R>>... wrapper) {
        Supplier<R> finalSupplier = getFinalSupplier(supplier, wrapper);
        // 沿用主线程的日志上下文
        Map<String, String> logContextMap = MDC.getCopyOfContextMap();
        if (Objects.nonNull(logContextMap) && !logContextMap.isEmpty()) {
            Function<Supplier<R>, Supplier<R>> logWrapper = (f) -> {
                MDC.setContextMap(logContextMap);
                return f;
            };
            finalSupplier = logWrapper.apply(finalSupplier);
        }
        // 转换为最终的方法
        return CompletableFuture.supplyAsync(finalSupplier, executor);
    }

    public static <R> CompletableFuture<Void> executeThenConsume(Executor executor, Supplier<R> supplier, Consumer<R> consumer) {
        return executeThenConsume(executor, supplier, consumer, null);
    }

    public static <R> CompletableFuture<Void> allOf(Executor executor, List<FutureTask<R>> futureTasks) {
        return CompletableFuture.allOf(
                futureTasks.stream()
                        .map(f -> executeThenConsume(executor, f.supplier, f.consumer, f.wrapper))
                        .toArray(CompletableFuture[]::new)
        );
    }

    public static <R> void waitAllFinish(Executor executor, List<FutureTask<R>> futureTasks) {
        dealFutureException(
                () -> (Void) allOf(executor, futureTasks).join());
    }


    private static <T> T dealFutureException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (CompletionException ex) {
            if (ex.getCause() instanceof RuntimeException) {
                throw (RuntimeException) ex.getCause();
            } else {
                throw ex;
            }
        }
    }

    /**
     * 执行并消费执行结果
     *
     * @param executor 执行线程池
     * @param supplier 执行方法
     * @param consumer 消费方法
     * @param wrapper  包装方法列表，包装执行方法，前面的方法包含内层的方法
     * @param <R>      返回类型
     */
    public static <R> CompletableFuture<Void> executeThenConsume(Executor executor, Supplier<R> supplier, Consumer<R> consumer, Function<Supplier<R>, Supplier<R>>... wrapper) {
        if (Objects.isNull(consumer)) {
            return execute(executor, supplier, wrapper).thenAccept(f -> {
            });
        }
        return execute(executor, supplier, wrapper).thenAccept(consumer);
    }


    private static <R> Supplier<R> getFinalSupplier(Supplier<R> supplier, Function<Supplier<R>, Supplier<R>>... wrapper) {
        if (wrapper == null || wrapper.length == 0) {
            return supplier;
        }
        Supplier<R> finalSupplier = supplier;
        for (int index = wrapper.length - 1; index >= 0; index--) {
            Function<Supplier<R>, Supplier<R>> rrFunction = wrapper[index];
            Supplier<R> tempSupplier = finalSupplier;
            finalSupplier = rrFunction.apply(tempSupplier);
        }
        return finalSupplier;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class FutureTask<R> {
        Supplier<R> supplier;
        Consumer<R> consumer;
        Function<Supplier<R>, Supplier<R>>[] wrapper;
    }


}

