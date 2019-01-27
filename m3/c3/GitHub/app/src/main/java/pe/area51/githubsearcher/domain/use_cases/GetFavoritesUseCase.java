package pe.area51.githubsearcher.domain.use_cases;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Executor;

import pe.area51.githubsearcher.domain.DataGatewayException;
import pe.area51.githubsearcher.domain.FavoriteDataGateway;
import pe.area51.githubsearcher.domain.GitHubProject;

public class GetFavoritesUseCase extends AbstractUseCase<Void, List<GitHubProject>, QueryUseCaseError> {

    @NonNull
    private final FavoriteDataGateway favoriteDataGateway;

    public GetFavoritesUseCase(@NonNull final Executor useCaseExecutor, @NonNull final Executor outputExecutor, @NonNull final FavoriteDataGateway gitHubProjectDataGateway) {
        super(useCaseExecutor, outputExecutor);
        this.favoriteDataGateway = gitHubProjectDataGateway;
    }

    @Override
    void onExecute(@NonNull Void noInput, @NonNull UseCaseOutput<List<GitHubProject>, QueryUseCaseError> useCaseOutput) {
        useCaseOutput.onProgress();
        try {
            useCaseOutput.onSuccess(favoriteDataGateway.getAllFavoriteProjects());
        } catch (DataGatewayException e) {
            e.printStackTrace();
            useCaseOutput.onError(QueryUseCaseError.IO_ERROR);
        }
    }

}
