package pe.area51.githubsearcher.android.ui;

import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;

public interface FragmentInteractionListener {

    void setToolbar(final Toolbar toolbar);

    void setTitle(@StringRes final int title);

}
