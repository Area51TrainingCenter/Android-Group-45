package pe.area51.notepad.android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import pe.area51.notepad.R;
import pe.area51.notepad.android.ui.FragmentInteractionInterface;
import pe.area51.notepad.android.ui.content.FragmentContent;
import pe.area51.notepad.android.ui.list.FragmentList;

public class MainActivity extends AppCompatActivity implements FragmentInteractionInterface {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        final FragmentList fragmentList;
        if (savedInstanceState == null) {
            /*
            Creamos los fragments e iniciamos la transacción cuando este Activity se cree
            por primera vez.
             */
            fragmentList = new FragmentList();
            fragmentList.setRetainInstance(true);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragmentList)
                    .commit();
        }
        /*
        Si el Activity tiene un estado anterior, no es necesario crear los fragments o ejecutar nuevamente
        la transacción, puesto que en el proceso de recreación de estados, se recrea también el estado
        del FragmentManager.
        */
    }

    @Override
    public void showNoteContent(@NonNull String noteId) {
        final FragmentContent fragmentContent = FragmentContent.newInstance(noteId);
        fragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                )
                .replace(R.id.fragmentContainer, fragmentContent)
                .commit();
    }

    @Override
    public void setTitle(@NonNull String title) {
        super.setTitle(title);
    }

    @Override
    public void closeFragment() {
        onBackPressed();
    }
}
