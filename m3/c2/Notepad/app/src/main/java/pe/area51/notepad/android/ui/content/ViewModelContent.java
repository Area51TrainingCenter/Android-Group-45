package pe.area51.notepad.android.ui.content;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import pe.area51.notepad.domain.Note;
import pe.area51.notepad.domain.NotesRepository;

public class ViewModelContent extends ViewModel {

    private final NotesRepository notesRepository;
    private final MutableLiveData<Note> fetchNoteByIdResponse;

    public ViewModelContent(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
        fetchNoteByIdResponse = new MutableLiveData<>();
    }

    public void fetchNoteById(@NonNull final String noteId) {
        AsyncTask.execute(() -> fetchNoteByIdResponse.postValue(
                notesRepository.getNoteById(noteId)
        ));
    }

    public void deleteNoteById(@NonNull final String noteId) {
        AsyncTask.execute(() -> notesRepository.deleteNote(noteId));
    }

    public void updateNote(final Note note) {
        AsyncTask.execute(() -> notesRepository.updateNote(note));
    }

    public LiveData<Note> getFetchNoteByIdResponse() {
        return fetchNoteByIdResponse;
    }
}
