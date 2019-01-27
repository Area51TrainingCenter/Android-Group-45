package pe.area51.githubsearcher.domain.use_cases;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

public abstract class AbstractUseCase<Input, Success, Failure> {

    private final Executor useCaseExecutor;
    private final Executor outputExecutor;

    public AbstractUseCase(@NonNull final Executor useCaseExecutor, @NonNull final Executor outputExecutor) {
        this.useCaseExecutor = useCaseExecutor;
        this.outputExecutor = outputExecutor;
    }

    abstract void onExecute(@NonNull final Input input, @NonNull final UseCaseOutput<Success, Failure> useCaseOutput);

    public void execute(@NonNull final Input input, @NonNull final UseCaseOutput<Success, Failure> useCaseOutput) {
        useCaseExecutor.execute(() -> onExecute(input, new DefaultUseCaseOutputExecutor<>(outputExecutor, useCaseOutput)));
    }

    private static class DefaultUseCaseOutputExecutor<Success, Error> implements UseCaseOutput<Success, Error> {

        @NonNull
        private final Executor outputExecutor;
        @NonNull
        private final UseCaseOutput<Success, Error> useCaseOutput;

        public DefaultUseCaseOutputExecutor(@NonNull final Executor outputExecutor, @NonNull final UseCaseOutput<Success, Error> useCaseOutput) {
            this.outputExecutor = outputExecutor;
            this.useCaseOutput = useCaseOutput;
        }

        @Override
        public void onSuccess(@NonNull Success success) {
            outputExecutor.execute(() -> useCaseOutput.onSuccess(success));
        }

        @Override
        public void onProgress() {
            outputExecutor.execute(useCaseOutput::onProgress);
        }

        @Override
        public void onError(@NonNull Error error) {
            outputExecutor.execute(() -> onError(error));
        }
    }

}
