package pe.area51.notepad.android.ui;

import android.content.Context;
import android.support.v4.app.Fragment;

import pe.area51.notepad.android.Application;
import pe.area51.notepad.android.ViewModelFactory;

public class FragmentBase extends Fragment {

    protected ViewModelFactory viewModelFactory;
    protected FragmentInteractionInterface fragmentInteractionInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentInteractionInterface = (FragmentInteractionInterface) context;
        final Application application =
                (Application) context.getApplicationContext();
        viewModelFactory =
                application.getViewModelFactory();

    }
}
