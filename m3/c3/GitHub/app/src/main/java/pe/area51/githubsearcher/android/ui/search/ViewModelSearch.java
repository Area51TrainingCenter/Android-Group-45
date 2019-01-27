package pe.area51.githubsearcher.android.ui.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import pe.area51.githubsearcher.android.ui.Response;
import pe.area51.githubsearcher.domain.GitHubProject;
import pe.area51.githubsearcher.domain.use_cases.GetFavoritesUseCase;
import pe.area51.githubsearcher.domain.use_cases.QueryUseCaseError;
import pe.area51.githubsearcher.domain.use_cases.SearchUseCase;
import pe.area51.githubsearcher.domain.use_cases.SetFavoritesUseCase;
import pe.area51.githubsearcher.domain.use_cases.UseCaseOutput;

public class ViewModelSearch extends ViewModel {

    private final SearchUseCase searchUseCase;
    private final GetFavoritesUseCase getFavoritesUseCase;
    private final SetFavoritesUseCase setFavoritesUseCase;

    private final MutableLiveData<Response<List<GitHubProject>, QueryUseCaseError>> searchResultResponse;
    private final DefaultResponse defaultResponse;

    public ViewModelSearch(final SearchUseCase searchUseCase, final GetFavoritesUseCase getFavoritesUseCase, final SetFavoritesUseCase setFavoritesUseCase) {
        this.searchUseCase = searchUseCase;
        this.getFavoritesUseCase = getFavoritesUseCase;
        this.setFavoritesUseCase = setFavoritesUseCase;
        searchResultResponse = new MutableLiveData<>();
        defaultResponse = new DefaultResponse(searchResultResponse);
    }

    public void getFavorites() {
        getFavoritesUseCase.execute(
                null,
                defaultResponse
        );
    }

    public void setFavorite(final String projectId, final boolean setToFavorite) {
        setFavoritesUseCase.execute(
                new SetFavoritesUseCase.Input(projectId, setToFavorite ? SetFavoritesUseCase.Input.Operation.ADD : SetFavoritesUseCase.Input.Operation.REMOVE),
                new UseCaseOutput<Boolean, QueryUseCaseError>() {
                    @Override
                    public void onProgress() {
                    }

                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {

                    }

                    @Override
                    public void onError(@NonNull QueryUseCaseError queryUseCaseError) {

                    }
                }
        );
    }

    public void searchProjectsByUserName(final String userName) {
        searchUseCase.execute(
                new SearchUseCase.Input(SearchUseCase.Input.Type.BY_USER_NAME, userName),
                defaultResponse
        );
    }

    public void searchProjectsByName(final String projectName) {
        searchUseCase.execute(
                new SearchUseCase.Input(SearchUseCase.Input.Type.BY_REPOSITORY_NAME, projectName),
                defaultResponse
        );
    }

    public LiveData<Response<List<GitHubProject>, QueryUseCaseError>> getSearchResultResponse() {
        return searchResultResponse;
    }

    private final static class DefaultResponse implements UseCaseOutput<List<GitHubProject>, QueryUseCaseError> {
        @NonNull
        private final MutableLiveData<Response<List<GitHubProject>, QueryUseCaseError>> response;

        public DefaultResponse(@NonNull MutableLiveData<Response<List<GitHubProject>, QueryUseCaseError>> response) {
            this.response = response;
        }

        @Override
        public void onProgress() {
            response.setValue(
                    new Response<>(Response.Status.IN_PROGRESS, null, null)
            );
        }

        @Override
        public void onSuccess(@NonNull List<GitHubProject> gitHubProjects) {
            response.setValue(
                    new Response<>(Response.Status.SUCCESS, gitHubProjects, null)
            );
        }

        @Override
        public void onError(@NonNull QueryUseCaseError error) {
            response.setValue(
                    new Response<>(Response.Status.ERROR, null, error)
            );
        }
    }
}
