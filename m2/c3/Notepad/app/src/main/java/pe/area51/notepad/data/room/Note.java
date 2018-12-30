package pe.area51.notepad.data.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    public final long id;
    public final String title;
    public final String content;
    public final long creationTimestamp;

    public Note(long id, String title, String content, long creationTimestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationTimestamp = creationTimestamp;
    }

    @Ignore
    public Note(String title, String content, long creationTimestamp) {
        this.id = 0;
        this.title = title;
        this.content = content;
        this.creationTimestamp = creationTimestamp;
    }

    @Ignore
    public Note(long id) {
        this.id = id;
        this.title = "";
        this.content = "";
        this.creationTimestamp = 0;
    }

}
