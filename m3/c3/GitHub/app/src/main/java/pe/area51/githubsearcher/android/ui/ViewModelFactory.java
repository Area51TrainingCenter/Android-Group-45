package pe.area51.githubsearcher.android.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import pe.area51.githubsearcher.android.UseCaseFactory;
import pe.area51.githubsearcher.android.ui.search.ViewModelSearch;

public class ViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final UseCaseFactory useCaseFactory;

    public ViewModelFactory(@NonNull UseCaseFactory useCaseFactory) {
        this.useCaseFactory = useCaseFactory;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ViewModelSearch.class)) {
            return (T) new ViewModelSearch(
                    useCaseFactory.createSearchUseCase(),
                    useCaseFactory.createGetFavoritesUseCase(),
                    useCaseFactory.createSetFavoritesUseCase());
        }
        try {
            return modelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Unknown ViewModel class!");
        }
    }
}
