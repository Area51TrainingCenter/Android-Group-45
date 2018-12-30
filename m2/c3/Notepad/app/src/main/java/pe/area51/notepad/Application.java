package pe.area51.notepad;

import android.arch.persistence.room.Room;

import io.requery.android.database.sqlite.RequerySQLiteOpenHelperFactory;
import pe.area51.notepad.data.room.RoomDatabase;
import pe.area51.notepad.data.room.RoomNotesRepository;
import pe.area51.notepad.domain.NotesRepository;

public class Application extends android.app.Application {

    private NotesRepository notesRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        /*notesRepository = new SqLiteDatabase(new DatabaseManager(
                this,
                "notes" //La extensión no es necesaria. Este es el nombre del archivo de base de datos.
        ));*/
        //notesRepository = new RamDatabase(); //Base de datos RAM.
        final RoomDatabase roomDatabase = Room
                .databaseBuilder(this, RoomDatabase.class, "room_notes")
                /*
                 * Permitir por ahora las consultas desde el Main Thread
                 * (Room no lo permite por defecto).
                 */
                .allowMainThreadQueries()
                /*
                 * Con esto usaremos la versión de SQLite de Requery, no la del framework base.
                 * De esta forma tendremos la misma versión de SQLite en todas las plataformas.
                 */
                .openHelperFactory(new RequerySQLiteOpenHelperFactory())
                .build();
        notesRepository = new RoomNotesRepository(roomDatabase);
    }

    public NotesRepository getNotesRepository() {
        return notesRepository;
    }
}
