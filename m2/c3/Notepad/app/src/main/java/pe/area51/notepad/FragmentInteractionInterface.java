package pe.area51.notepad;

import android.support.annotation.NonNull;

public interface FragmentInteractionInterface {

    void showNoteContent(@NonNull final String noteId);

    void setTitle(@NonNull final String title);

}
