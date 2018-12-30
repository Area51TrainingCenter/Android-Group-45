package pe.area51.notepad.domain;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public interface NotesRepository {

    @NonNull
    List<Note> getAllNotes();

    @Nullable
    Note getNoteById(@NonNull final String noteId);

    boolean updateNote(@NonNull final Note note);

    boolean deleteNote(@NonNull final String noteId);

    @NonNull
    Note createNote(@NonNull final Note note);
}
