package pe.area51.notepad;

import android.support.annotation.NonNull;

public interface FragmentInteractionInterface {

    void showNoteContent(@NonNull final Note note);

    void setTitle(@NonNull final String title);

}
