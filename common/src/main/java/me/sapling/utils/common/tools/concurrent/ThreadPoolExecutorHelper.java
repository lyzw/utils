package me.sapling.utils.common.tools.concurrent;

import lombok.extern.slf4j.Slf4j;
import me.sapling.utils.common.tools.reflect.FieldReflectUtil;

import java.util.concurrent.*;

/**
 * { DESCRIBE HERE }
 *
 * @author zhouwei
 * @since 2021/9/22
 */
@Slf4j
public class ThreadPoolExecutorHelper {

    /**
     * @param futureTask
     * @param <T>
     * @return
     * @throws NoSuchFieldException   throw exception when no such filed in the class
     * @throws IllegalAccessException no permission to access the filed
     */
    public static <T> Object getOrginalRunnableFromFutureTask(FutureTask<T> futureTask) throws NoSuchFieldException, IllegalAccessException {
        try {
            Object caller = FieldReflectUtil.readRecursionDeclaredField("callable", futureTask);
            Object task = FieldReflectUtil.readRecursionDeclaredField("task", caller);
            return FieldReflectUtil.readRecursionDeclaredField("delegate", task);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("获取future Task中的线程失败", e);
            throw e;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, NoSuchFieldException, IllegalAccessException {
        Callable<Integer> thread = () -> {
            System.out.println("111");
            return 1;
        };
        FutureTask<Integer> futureTask = new FutureTask<>(thread);
        Executor executor = Executors.newFixedThreadPool(1);
        executor.execute(futureTask);
//        getOrginalRunnableFromFutureTask(futureTask);
        System.out.println(futureTask.get());
    }
}
