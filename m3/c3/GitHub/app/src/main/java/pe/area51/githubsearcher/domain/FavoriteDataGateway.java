package pe.area51.githubsearcher.domain;

import android.support.annotation.NonNull;

import java.util.List;

public interface FavoriteDataGateway {

    @NonNull
    List<GitHubProject> getAllFavoriteProjects() throws DataGatewayException;

    boolean addFavorite(@NonNull String projectId) throws DataGatewayException;

    boolean removeFavorite(@NonNull String projectId) throws DataGatewayException;
}
