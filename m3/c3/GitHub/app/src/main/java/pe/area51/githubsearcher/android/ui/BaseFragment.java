package pe.area51.githubsearcher.android.ui;

import android.content.Context;
import android.support.v4.app.Fragment;

import pe.area51.githubsearcher.android.Application;

public class BaseFragment extends Fragment {

    protected FragmentInteractionListener fragmentInteractionListener;
    protected ViewModelFactory viewModelFactory;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Application application = (Application) context.getApplicationContext();
        fragmentInteractionListener = (FragmentInteractionListener) context;
        viewModelFactory = application.getViewModelFactory();
    }

}
