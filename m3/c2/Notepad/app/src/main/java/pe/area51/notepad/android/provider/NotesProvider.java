package pe.area51.notepad.android.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import pe.area51.notepad.android.Application;
import pe.area51.notepad.domain.Note;
import pe.area51.notepad.domain.NotesRepository;

public class NotesProvider extends ContentProvider {

    private static final int URI_MATCHER_ALL_NOTES = 100;

    private NotesRepository notesRepository;
    private UriMatcher uriMatcher;

    @Override
    public boolean onCreate() {
        final Application application = ((Application) getContext().getApplicationContext());
        notesRepository = application.getNotesRepository();
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(
                NotesContract.URI.getAuthority(),
                NotesContract.Note.URI.getPath(),
                URI_MATCHER_ALL_NOTES
        );
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int uriMatch = uriMatcher.match(uri);
        if (uriMatch == UriMatcher.NO_MATCH) {
            return null;
        }
        final MatrixCursor matrixCursor = new MatrixCursor(new String[]{
                NotesContract.Note.ID,
                NotesContract.Note.TITLE,
                NotesContract.Note.CONTENT,
                NotesContract.Note.CREATION_TIMESTAMP
        });
        final List<Note> notes = notesRepository.getAllNotes();
        for (final Note note : notes) {
            matrixCursor.addRow(new Object[]{
                    note.getId(),
                    note.getTitle(),
                    note.getContent(),
                    note.getCreationTimestamp()
            });
        }
        return matrixCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
