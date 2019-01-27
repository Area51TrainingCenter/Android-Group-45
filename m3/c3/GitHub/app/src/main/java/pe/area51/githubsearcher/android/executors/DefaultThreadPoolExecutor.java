package pe.area51.githubsearcher.android.executors;

import android.support.annotation.NonNull;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DefaultThreadPoolExecutor implements Executor {

    private final AbstractExecutorService abstractExecutorService;

    public DefaultThreadPoolExecutor() {
        final int maximumPoolSize = Runtime.getRuntime().availableProcessors() * 2;
        abstractExecutorService = new ThreadPoolExecutor(
                1,
                maximumPoolSize,
                2,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(maximumPoolSize)
        );
    }

    @Override
    public void execute(@NonNull Runnable command) {
        abstractExecutorService.execute(command);
    }
}
