package pe.area51.githubsearcher.android.ui.favorites;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pe.area51.githubsearcher.R;
import pe.area51.githubsearcher.android.ui.BaseFragment;
import pe.area51.githubsearcher.android.ui.Response;
import pe.area51.githubsearcher.android.ui.search.GitHubProjectAdapter;
import pe.area51.githubsearcher.android.ui.search.ViewModelSearch;
import pe.area51.githubsearcher.databinding.FragmentFavoritesBinding;
import pe.area51.githubsearcher.domain.GitHubProject;
import pe.area51.githubsearcher.domain.use_cases.QueryUseCaseError;

public class FragmentFavorites extends BaseFragment {

    private ViewModelSearch viewModelSearch;

    private FragmentFavoritesBinding fragmentFavoritesBinding;

    private GitHubProjectAdapter gitHubProjectAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModelSearch = ViewModelProviders
                .of(this, viewModelFactory)
                .get(ViewModelSearch.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentFavoritesBinding = FragmentFavoritesBinding.inflate(inflater, container, false);
        final LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext());
        fragmentFavoritesBinding.recyclerView.setLayoutManager(linearLayoutManager);
        gitHubProjectAdapter = new GitHubProjectAdapter(inflater, this::onItemClick, this::onFavoriteClick);
        viewModelSearch.getSearchResultResponse().observe(this, this::showResult);
        fragmentFavoritesBinding.recyclerView.setAdapter(gitHubProjectAdapter);
        fragmentInteractionListener.setTitle(R.string.title_favorites);
        fragmentInteractionListener.setToolbar(fragmentFavoritesBinding.toolbar);
        return fragmentFavoritesBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModelSearch.getFavorites();
    }

    private void showResult(final Response<List<GitHubProject>, QueryUseCaseError> result) {
        switch (result.status) {
            case IN_PROGRESS:
                fragmentFavoritesBinding.setShowProgress(true);
                break;
            case SUCCESS:
                fragmentFavoritesBinding.setShowProgress(false);
                gitHubProjectAdapter.replaceElements(result.successData);
                break;
            case ERROR:
                fragmentFavoritesBinding.setShowProgress(false);
                break;
        }
    }

    private void onItemClick(final GitHubProject gitHubProject) {
        final Uri webUri = Uri.parse(gitHubProject.getWebUrl());
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(webUri);
        startActivity(intent);
    }

    private void onFavoriteClick(final GitHubProject gitHubProject) {
        viewModelSearch.setFavorite(gitHubProject.getId(), !gitHubProject.isFavorite());
    }
}
