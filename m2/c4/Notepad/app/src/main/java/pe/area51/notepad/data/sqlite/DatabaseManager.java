package pe.area51.notepad.data.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseManager extends SQLiteOpenHelper {

    private final static int VERSION = 1;

    public DatabaseManager(@Nullable Context context, @Nullable String name) {
        super(context, name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE notes(id INTEGER PRIMARY KEY, title TEXT, content TEXT, creationTimestamp INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new IllegalStateException("This shouldn't be called!");
    }
}
