package pe.area51.notepad.data.room;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import pe.area51.notepad.domain.Note;
import pe.area51.notepad.domain.NotesRepository;

public class RoomNotesRepository implements NotesRepository {

    private final RoomDatabase roomDatabase;

    public RoomNotesRepository(RoomDatabase roomDatabase) {
        this.roomDatabase = roomDatabase;
    }

    @NonNull
    @Override
    public List<Note> getAllNotes() {
        final List<pe.area51.notepad.data.room.Note> roomNotes
                = roomDatabase.getNoteDao().getAll();
        final List<Note> domainNotes = new ArrayList<>();
        for (final pe.area51.notepad.data.room.Note roomNote : roomNotes) {
            domainNotes.add(createDomainNote(roomNote));
        }
        return domainNotes;
    }

    @Nullable
    @Override
    public Note getNoteById(@NonNull String noteId) {
        return createDomainNote(roomDatabase.getNoteDao().getById(Long.valueOf(noteId)));
    }

    @Override
    public boolean updateNote(@NonNull Note note) {
        return roomDatabase.getNoteDao().update(createRoomNote(note))
                != 0;
    }

    @Override
    public boolean deleteNote(@NonNull String noteId) {
        return roomDatabase.getNoteDao().delete(new pe.area51.notepad.data.room.Note(
                Long.valueOf(noteId)
        )) != 0;
    }

    @NonNull
    @Override
    public Note createNote(@NonNull Note note) {
        final long createdNoteId = roomDatabase.getNoteDao().insert(createRoomNote(note));
        return new Note(
                String.valueOf(createdNoteId),
                note.getTitle(),
                note.getContent(),
                note.getCreationTimestamp()
        );
    }

    private Note createDomainNote(final pe.area51.notepad.data.room.Note roomNote) {
        return new Note(
                String.valueOf(roomNote.id),
                roomNote.title,
                roomNote.content,
                roomNote.creationTimestamp
        );
    }

    private pe.area51.notepad.data.room.Note createRoomNote(final Note domainNote) {
        long id;
        try {
            id = Long.valueOf(domainNote.getId());
        } catch (NumberFormatException e) {
            id = 0;
        }
        return new pe.area51.notepad.data.room.Note(
                id,
                domainNote.getTitle(),
                domainNote.getContent(),
                domainNote.getCreationTimestamp()
        );
    }
}
