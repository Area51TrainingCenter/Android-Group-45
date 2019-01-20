package pe.area51.notesproviderclient;

import android.net.Uri;

public class NotesContract {

    public static final Uri URI = Uri.parse("content://pe.area51.notepad.NotesProvider");

    public final static class Note {

        public static final Uri URI = NotesContract.URI.buildUpon()
                .path("note")
                .build();

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String CONTENT = "content";
        public static final String CREATION_TIMESTAMP = "creationTimestamp";

    }

}
