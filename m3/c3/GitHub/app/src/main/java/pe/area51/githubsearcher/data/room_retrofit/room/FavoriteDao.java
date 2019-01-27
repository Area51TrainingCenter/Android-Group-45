package pe.area51.githubsearcher.data.room_retrofit.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favoriteProjects")
    LiveData<List<Favorite>> subscribeToAllFavorites();

    @Query("SELECT * FROM favoriteProjects")
    List<Favorite> getAllFavorites();

    @Query("SELECT COUNT(*) FROM favoriteProjects WHERE gitHubProjectRemoteId = :gitHubProjectRemoteId")
    int countByGitHubProjectRemoteId(@NonNull final String gitHubProjectRemoteId);

    @Query("SELECT favoriteTimestamp FROM favoriteProjects WHERE gitHubProjectRemoteId = :gitHubProjectRemoteId")
    long getFavoriteTimestamp(@NonNull final String gitHubProjectRemoteId);

    @Insert
    long addFavorite(@NonNull final Favorite favorite);

    @Query("DELETE FROM favoriteProjects WHERE gitHubProjectRemoteId = :gitHubProjectRemoteId")
    int removeFavoriteByGitHubProjectRemoteId(@NonNull final String gitHubProjectRemoteId);

}
