package pe.area51.notepad.data.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import pe.area51.notepad.domain.Note;
import pe.area51.notepad.domain.NotesRepository;

public class SqLiteDatabase implements NotesRepository {

    private final DatabaseManager databaseManager;

    public SqLiteDatabase(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @NonNull
    @Override
    public List<Note> getAllNotes() {
        final Cursor cursor = databaseManager.getReadableDatabase().rawQuery("SELECT * FROM notes", null);
        final List<Note> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            final long id = cursor.getLong(cursor.getColumnIndex("id"));
            final String title = cursor.getString(cursor.getColumnIndex("title"));
            final String content = cursor.getString(cursor.getColumnIndex("content"));
            final long creationTimestamp = cursor.getLong(cursor.getColumnIndex("creationTimestamp"));
            notes.add(new Note(
                    String.valueOf(id),
                    title,
                    content,
                    creationTimestamp
            ));
        }
        cursor.close();
        return notes;
    }

    @Nullable
    @Override
    public Note getNoteById(@NonNull String noteId) {
        final Cursor cursor = databaseManager.getReadableDatabase().query(
                "notes",
                null,
                "id=?",
                new String[]{noteId},
                null,
                null,
                null
        );
        Note note = null;
        if (cursor.moveToNext()) {
            final long id = cursor.getLong(cursor.getColumnIndex("id"));
            final String title = cursor.getString(cursor.getColumnIndex("title"));
            final String content = cursor.getString(cursor.getColumnIndex("content"));
            final long creationTimestamp = cursor.getLong(cursor.getColumnIndex("creationTimestamp"));
            note = new Note(
                    String.valueOf(id),
                    title,
                    content,
                    creationTimestamp
            );
        }
        cursor.close();
        return note;
    }

    @Override
    public boolean updateNote(@NonNull Note note) {
        throw new UnsupportedOperationException("Not implemented!");
    }

    @Override
    public boolean deleteNote(@NonNull String noteId) {
        throw new UnsupportedOperationException("Not implemented!");
    }

    @NonNull
    @Override
    public Note createNote(@NonNull Note note) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("title", note.getTitle());
        contentValues.put("content", note.getContent());
        contentValues.put("creationTimestamp", note.getCreationTimestamp());
        final long id = databaseManager.getWritableDatabase().insert(
                "notes",
                null,
                contentValues
        );
        return new Note(
                String.valueOf(id),
                note.getTitle(),
                note.getContent(),
                note.getCreationTimestamp()
        );
    }
}
