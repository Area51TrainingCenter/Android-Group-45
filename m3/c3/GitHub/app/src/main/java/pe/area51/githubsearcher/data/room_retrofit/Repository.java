package pe.area51.githubsearcher.data.room_retrofit;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pe.area51.githubsearcher.data.room_retrofit.retrofit.RetrofitGitHubClient;
import pe.area51.githubsearcher.data.room_retrofit.room.Favorite;
import pe.area51.githubsearcher.data.room_retrofit.room.RoomDatabase;
import pe.area51.githubsearcher.domain.DataGatewayException;
import pe.area51.githubsearcher.domain.FavoriteDataGateway;
import pe.area51.githubsearcher.domain.GitHubProject;
import pe.area51.githubsearcher.domain.GitHubProjectDataGateway;
import retrofit2.Call;
import retrofit2.Response;

public class Repository implements GitHubProjectDataGateway, FavoriteDataGateway {

    private final RetrofitGitHubClient retrofitGitHubClient;
    private final RoomDatabase roomDatabase;

    public Repository(RetrofitGitHubClient retrofitGitHubClient, RoomDatabase roomDatabase) {
        this.retrofitGitHubClient = retrofitGitHubClient;
        this.roomDatabase = roomDatabase;
    }

    @NonNull
    @Override
    public List<GitHubProject> findProjectsByProjectName(@NonNull String projectName) throws DataGatewayException {
        final Call<List<pe.area51.githubsearcher.data.room_retrofit.retrofit.model.GitHubProject>> call =
                retrofitGitHubClient.findProjectsByName(projectName);
        try {
            final Response<List<pe.area51.githubsearcher.data.room_retrofit.retrofit.model.GitHubProject>> serverResponse = call.execute();
            if (!serverResponse.isSuccessful()) {
                throw new DataGatewayException("Unknown error", DataGatewayException.ERROR_OTHER);
            }
            final List<pe.area51.githubsearcher.data.room_retrofit.retrofit.model.GitHubProject> responseBody = serverResponse.body();
            if (responseBody == null) {
                throw new DataGatewayException("Unknown error", DataGatewayException.ERROR_OTHER);
            }
            return mapList(responseBody, this::buildDomainGitHubProjectFromRemote);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataGatewayException(e, DataGatewayException.ERROR_IO);
        }
    }

    @NonNull
    @Override
    public List<GitHubProject> findProjectsByUserName(@NonNull String userName) throws DataGatewayException {
        final Call<List<pe.area51.githubsearcher.data.room_retrofit.retrofit.model.GitHubProject>> call =
                retrofitGitHubClient.findProjectsByUserName(userName);
        try {
            final Response<List<pe.area51.githubsearcher.data.room_retrofit.retrofit.model.GitHubProject>> serverResponse = call.execute();
            if (!serverResponse.isSuccessful()) {
                throw new DataGatewayException("Unknown user", DataGatewayException.ERROR_UNKNOWN_USER);
            }
            final List<pe.area51.githubsearcher.data.room_retrofit.retrofit.model.GitHubProject> responseBody = serverResponse.body();
            if (responseBody == null) {
                throw new DataGatewayException("Unknown error", DataGatewayException.ERROR_OTHER);
            }
            return mapList(responseBody, this::buildDomainGitHubProjectFromRemote);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataGatewayException(e, DataGatewayException.ERROR_IO);
        }
    }

    @NonNull
    @Override
    public List<GitHubProject> getAllFavoriteProjects() throws DataGatewayException {
        final List<Favorite> favorites = roomDatabase.getFavoriteDao().getAllFavorites();
        final List<GitHubProject> result = new ArrayList<>();
        for (final Favorite favorite : favorites) {
            final Call<pe.area51.githubsearcher.data.room_retrofit.retrofit.model.GitHubProject> gitHubServerProjectCall =
                    retrofitGitHubClient.findProjectById(favorite.gitHubProjectRemoteId);
            try {
                final Response<pe.area51.githubsearcher.data.room_retrofit.retrofit.model.GitHubProject> serverResponse = gitHubServerProjectCall.execute();
                if (!serverResponse.isSuccessful()) {
                    throw new DataGatewayException("Unknown user", DataGatewayException.ERROR_UNKNOWN_USER);
                }
                final pe.area51.githubsearcher.data.room_retrofit.retrofit.model.GitHubProject responseBody = serverResponse.body();
                if (responseBody == null) {
                    throw new DataGatewayException("Unknown error", DataGatewayException.ERROR_OTHER);
                }
                result.add(buildDomainGitHubProject(responseBody, true, favorite.favoriteTimestamp));
            } catch (IOException e) {
                e.printStackTrace();
                throw new DataGatewayException(e, DataGatewayException.ERROR_IO);
            }

        }
        return result;
    }

    @Override
    public boolean addFavorite(@NonNull String projectId) throws DataGatewayException {
        try {
            return roomDatabase.getFavoriteDao().addFavorite(new Favorite(
                    0,
                    projectId,
                    System.currentTimeMillis()
            )) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataGatewayException(e, DataGatewayException.ERROR_OTHER);
        }
    }

    @Override
    public boolean removeFavorite(@NonNull String projectId) throws DataGatewayException {
        try {
            return roomDatabase.getFavoriteDao().removeFavoriteByGitHubProjectRemoteId(projectId) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataGatewayException(e, DataGatewayException.ERROR_OTHER);
        }
    }

    private GitHubProject buildDomainGitHubProjectFromRemote(final pe.area51.githubsearcher.data.room_retrofit.retrofit.model.GitHubProject remoteGitHubProject) {
        final String gitHubRemoteProjectId = String.valueOf(remoteGitHubProject.id);
        final boolean isFavorite = isFavorite(gitHubRemoteProjectId);
        if (!isFavorite) {
            return buildDomainGitHubProject(remoteGitHubProject, false, 0);
        }
        return buildDomainGitHubProject(remoteGitHubProject, isFavorite(gitHubRemoteProjectId), getFavoriteTimestamp(gitHubRemoteProjectId));
    }

    private boolean isFavorite(final String projectId) {
        return roomDatabase.getFavoriteDao().countByGitHubProjectRemoteId(projectId) != 0;
    }

    private long getFavoriteTimestamp(final String projectId) {
        return roomDatabase.getFavoriteDao().getFavoriteTimestamp(projectId);
    }

    private static <I, O> List<O> mapList(List<I> inList, Function<I, O> function) {
        final List<O> outList = new ArrayList<>();
        for (final I element : inList) {
            outList.add(function.apply(element));
        }
        return outList;
    }

    private interface Function<I, O> {

        O apply(I input);

    }

    private final GitHubProject buildDomainGitHubProject(@NonNull final pe.area51.githubsearcher.data.room_retrofit.retrofit.model.GitHubProject retrofitGitHubProject,
                                                         boolean isFavorite, long favoriteTimestamp) {
        return new GitHubProject(
                String.valueOf(retrofitGitHubProject.id),
                retrofitGitHubProject.name,
                retrofitGitHubProject.description,
                retrofitGitHubProject.webUrl,
                retrofitGitHubProject.user.userName,
                gitHubServerDateToTimestamp(retrofitGitHubProject.creationDate),
                gitHubServerDateToTimestamp(retrofitGitHubProject.lastUpdateTimestamp),
                retrofitGitHubProject.popularity,
                isFavorite,
                favoriteTimestamp
        );
    }

    //TODO: Convert GitHub server date to timestamp.
    private long gitHubServerDateToTimestamp(final String gitHubDate) {
        return 0;
    }
}
