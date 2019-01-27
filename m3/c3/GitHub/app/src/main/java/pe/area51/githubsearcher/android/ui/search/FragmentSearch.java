package pe.area51.githubsearcher.android.ui.search;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pe.area51.githubsearcher.R;
import pe.area51.githubsearcher.android.ui.BaseFragment;
import pe.area51.githubsearcher.android.ui.Response;
import pe.area51.githubsearcher.databinding.FragmentSearchBinding;
import pe.area51.githubsearcher.domain.GitHubProject;
import pe.area51.githubsearcher.domain.use_cases.QueryUseCaseError;

public class FragmentSearch extends BaseFragment {

    private final static String SAVED_INSTANCE_STATE_KEY_SEARCH_OPTION = "searchOption";

    private final static int SEARCH_OPTION_USER_NAME = 1;
    private final static int SEARCH_OPTION_REPOSITORY_NAME = 2;

    private ViewModelSearch viewModelSearch;

    private GitHubProjectAdapter gitHubProjectAdapter;

    private FragmentSearchBinding fragmentSearchBinding;

    private int searchOption;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModelSearch = ViewModelProviders
                .of(this, viewModelFactory)
                .get(ViewModelSearch.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            searchOption = SEARCH_OPTION_USER_NAME;
        } else {
            searchOption = savedInstanceState.getInt(SAVED_INSTANCE_STATE_KEY_SEARCH_OPTION);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_KEY_SEARCH_OPTION, searchOption);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_search, menu);
        if (searchOption == SEARCH_OPTION_REPOSITORY_NAME) {
            menu.findItem(R.id.optionSearchByRepositoryName).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionSearchByRepositoryName:
                item.setChecked(true);
                fragmentSearchBinding
                        .toolbarEditTextSearch
                        .setHint(R.string.hint_search_repository_name);
                searchOption = SEARCH_OPTION_REPOSITORY_NAME;
                return true;
            case R.id.optionSearchByUserName:
                item.setChecked(true);
                fragmentSearchBinding
                        .toolbarEditTextSearch
                        .setHint(R.string.hint_search_user_name);
                searchOption = SEARCH_OPTION_USER_NAME;
                return true;
            default:
                return false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false);
        final LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext());
        fragmentSearchBinding.recyclerView.setLayoutManager(linearLayoutManager);
        gitHubProjectAdapter = new GitHubProjectAdapter(inflater, this::onItemClick, this::onFavoriteClick);
        viewModelSearch.getSearchResultResponse().observe(this, this::showResult);
        fragmentSearchBinding.recyclerView.setAdapter(gitHubProjectAdapter);
        fragmentSearchBinding.toolbarEditTextSearch.setOnEditorActionListener((v, actionId, event) -> {
            Log.d("FragmentSearch", "onEditorAction: " + event);
            if (event == null || event.getAction() == KeyEvent.ACTION_UP) {
                doSearch();
            }
            return true;
        });
        fragmentInteractionListener.setToolbar(fragmentSearchBinding.toolbar);
        if (searchOption == SEARCH_OPTION_REPOSITORY_NAME) {
            fragmentSearchBinding.toolbarEditTextSearch.setHint(R.string.hint_search_repository_name);
        }
        return fragmentSearchBinding.getRoot();
    }

    private void showResult(final Response<List<GitHubProject>, QueryUseCaseError> result) {
        switch (result.status) {
            case IN_PROGRESS:
                fragmentSearchBinding.setShowProgress(true);
                break;
            case SUCCESS:
                fragmentSearchBinding.setShowProgress(false);
                gitHubProjectAdapter.replaceElements(result.successData);
                break;
            case ERROR:
                fragmentSearchBinding.setShowProgress(false);
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

    private void doSearch() {
        final String searchText = fragmentSearchBinding
                .toolbarEditTextSearch
                .getText()
                .toString();
        switch (searchOption) {
            case SEARCH_OPTION_USER_NAME:
                viewModelSearch.searchProjectsByUserName(searchText);
                break;
            case SEARCH_OPTION_REPOSITORY_NAME:
                viewModelSearch.searchProjectsByName(searchText);
                break;
        }
    }

}
