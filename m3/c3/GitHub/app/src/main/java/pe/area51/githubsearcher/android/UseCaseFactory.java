package pe.area51.githubsearcher.android;

import java.util.concurrent.Executor;

import pe.area51.githubsearcher.domain.FavoriteDataGateway;
import pe.area51.githubsearcher.domain.GitHubProjectDataGateway;
import pe.area51.githubsearcher.domain.use_cases.GetFavoritesUseCase;
import pe.area51.githubsearcher.domain.use_cases.SearchUseCase;
import pe.area51.githubsearcher.domain.use_cases.SetFavoritesUseCase;

public class UseCaseFactory {

    private final GitHubProjectDataGateway gitHubProjectDataGateway;
    private final FavoriteDataGateway favoriteDataGateway;
    private final Executor useCaseExecutor;
    private final Executor outputExecutor;

    public UseCaseFactory(GitHubProjectDataGateway gitHubProjectDataGateway, FavoriteDataGateway favoriteDataGateway, Executor useCaseExecutor, Executor outputExecutor) {
        this.gitHubProjectDataGateway = gitHubProjectDataGateway;
        this.favoriteDataGateway = favoriteDataGateway;
        this.useCaseExecutor = useCaseExecutor;
        this.outputExecutor = outputExecutor;
    }

    public SearchUseCase createSearchUseCase() {
        return new SearchUseCase(
                useCaseExecutor,
                outputExecutor,
                gitHubProjectDataGateway
        );
    }

    public GetFavoritesUseCase createGetFavoritesUseCase() {
        return new GetFavoritesUseCase(
                useCaseExecutor,
                outputExecutor,
                favoriteDataGateway
        );
    }

    public SetFavoritesUseCase createSetFavoritesUseCase() {
        return new SetFavoritesUseCase(
                useCaseExecutor,
                outputExecutor,
                favoriteDataGateway
        );
    }
}
