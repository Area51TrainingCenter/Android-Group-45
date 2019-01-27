package pe.area51.githubsearcher.android;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import pe.area51.githubsearcher.R;
import pe.area51.githubsearcher.android.ui.FragmentInteractionListener;
import pe.area51.githubsearcher.android.ui.favorites.FragmentFavorites;
import pe.area51.githubsearcher.android.ui.search.FragmentSearch;
import pe.area51.githubsearcher.android.ui.settings.FragmentSettings;
import pe.area51.githubsearcher.databinding.ActivityMainBinding;

public class ActivityMain extends AppCompatActivity implements FragmentInteractionListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private ActivityMainBinding activityMainBinding;

    private final static String FRAGMENT_TAG_SEARCH = "fragmentSearch";
    private final static String FRAGMENT_TAG_FAVORITES = "fragmentFavorites";
    private final static String FRAGMENT_TAG_SETTINGS = "fragmentSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            final FragmentSearch fragmentSearch = new FragmentSearch();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragmentSearch)
                    .commit();
        }
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int currentId = activityMainBinding.bottomNavigation.getSelectedItemId();
        if (currentId == item.getItemId()) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.navToSearch:
                navigateToFragmentSearch();
                return true;
            case R.id.navToFavorites:
                navigateToFragmentFavorites();
                return true;
            case R.id.navToSettings:
                navigateToFragmentSettings();
                return true;
            default:
                return false;
        }
    }

    private void navigateToFragmentSearch() {
        navigateToFragment(FRAGMENT_TAG_SEARCH);
    }

    private void navigateToFragmentFavorites() {
        navigateToFragment(FRAGMENT_TAG_FAVORITES);
    }

    private void navigateToFragmentSettings() {
        navigateToFragment(FRAGMENT_TAG_SETTINGS);
    }

    private void navigateToFragment(final String tag) {
        final Fragment fragment = findOrCreateFragment(tag);
        replaceFragment(fragment, tag);
    }

    private Fragment findOrCreateFragment(final String tag) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            return fragment;
        }
        switch (tag) {
            case FRAGMENT_TAG_SEARCH:
                fragment = new FragmentSearch();
                break;
            case FRAGMENT_TAG_FAVORITES:
                fragment = new FragmentFavorites();
                break;
            case FRAGMENT_TAG_SETTINGS:
                fragment = new FragmentSettings();
                break;
            default:
                throw new RuntimeException("Unknown fragment tag!");
        }
        return fragment;
    }

    private void replaceFragment(final Fragment fragment, final String tag) {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                )
                .replace(R.id.fragmentContainer, fragment, tag)
                .commit();
    }
}
