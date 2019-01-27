package pe.area51.githubsearcher.domain.use_cases;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import pe.area51.githubsearcher.domain.DataGatewayException;
import pe.area51.githubsearcher.domain.FavoriteDataGateway;

public class SetFavoritesUseCase extends AbstractUseCase<SetFavoritesUseCase.Input, Boolean, QueryUseCaseError> {

    @NonNull
    private final FavoriteDataGateway favoriteDataGateway;

    public SetFavoritesUseCase(@NonNull final Executor useCaseExecutor, @NonNull final Executor outputExecutor, @NonNull final FavoriteDataGateway gitHubProjectDataGateway) {
        super(useCaseExecutor, outputExecutor);
        this.favoriteDataGateway = gitHubProjectDataGateway;
    }

    @Override
    void onExecute(@NonNull Input input, @NonNull UseCaseOutput<Boolean, QueryUseCaseError> useCaseOutput) {
        useCaseOutput.onProgress();
        try {
            if (input.operation == Input.Operation.ADD) {
                useCaseOutput.onSuccess(favoriteDataGateway.addFavorite(input.projectId));
            } else {
                useCaseOutput.onSuccess(favoriteDataGateway.removeFavorite(input.projectId));
            }
        } catch (DataGatewayException e) {
            e.printStackTrace();
            useCaseOutput.onError(QueryUseCaseError.IO_ERROR);
        }
    }

    public static final class Input {

        @NonNull
        private final String projectId;
        @NonNull
        private final Operation operation;

        public Input(@NonNull String projectId, @NonNull Operation operation) {
            this.projectId = projectId;
            this.operation = operation;
        }

        public enum Operation {
            ADD,
            REMOVE
        }

    }

}
