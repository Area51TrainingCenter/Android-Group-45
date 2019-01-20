package pe.area51.notepad.android;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import pe.area51.notepad.android.ui.content.ViewModelContent;
import pe.area51.notepad.android.ui.list.ViewModelList;
import pe.area51.notepad.domain.NotesRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final NotesRepository notesRepository;

    public ViewModelFactory(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ViewModelList.class)) {
            return (T) new ViewModelList(notesRepository);
        }
        if (modelClass.isAssignableFrom(ViewModelContent.class)) {
            return (T) new ViewModelContent(notesRepository);
        }
        throw new IllegalArgumentException("Unknown model class!");
    }
}
