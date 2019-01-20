package pe.area51.notepad.data.room;

import android.arch.persistence.room.Database;

@Database(entities = {Note.class}, version = 1)
public abstract class RoomDatabase extends android.arch.persistence.room.RoomDatabase {

    public abstract NoteDao getNoteDao();

}
