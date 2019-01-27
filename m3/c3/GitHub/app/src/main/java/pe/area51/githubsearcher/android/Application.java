package pe.area51.githubsearcher.android;

import android.arch.persistence.room.Room;

import java.util.concurrent.Executor;

import io.requery.android.database.sqlite.RequerySQLiteOpenHelperFactory;
import pe.area51.githubsearcher.android.executors.DefaultThreadPoolExecutor;
import pe.area51.githubsearcher.android.executors.UiThreadExecutor;
import pe.area51.githubsearcher.android.ui.ViewModelFactory;
import pe.area51.githubsearcher.data.room_retrofit.Repository;
import pe.area51.githubsearcher.data.room_retrofit.retrofit.RetrofitGitHubClient;
import pe.area51.githubsearcher.data.room_retrofit.retrofit.RetrofitGitHubClientBuilder;
import pe.area51.githubsearcher.data.room_retrofit.room.RoomDatabase;

public class Application extends android.app.Application {

    private ViewModelFactory viewModelFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        //final FakeDatabase fakeDatabase = new FakeDatabase(100);
        final RetrofitGitHubClient retrofitGitHubClient = new RetrofitGitHubClientBuilder().build();
        final RoomDatabase favoriteRoomDatabase = Room.databaseBuilder(
                this,
                RoomDatabase.class,
                "favoriteDatabase"
        ).openHelperFactory(new RequerySQLiteOpenHelperFactory()).build();
        final Repository repository = new Repository(retrofitGitHubClient, favoriteRoomDatabase);
        final Executor uiExecutor = new UiThreadExecutor();
        final Executor useCaseExecutor = new DefaultThreadPoolExecutor();
        final UseCaseFactory useCaseFactory = new UseCaseFactory(
                repository,
                repository,
                useCaseExecutor,
                uiExecutor
        );
        viewModelFactory = new ViewModelFactory(useCaseFactory);
    }

    public ViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }
}
