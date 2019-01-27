package pe.area51.githubsearcher.android.executors;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

public class UiThreadExecutor implements Executor {

    private final Handler uiThreadHandler;

    public UiThreadExecutor() {
        uiThreadHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(@NonNull Runnable command) {
        uiThreadHandler.post(command);
    }
}
