package pe.area51.notepad;

import pe.area51.notepad.data.sqlite.DatabaseManager;
import pe.area51.notepad.data.sqlite.SqLiteDatabase;
import pe.area51.notepad.domain.NotesRepository;

public class Application extends android.app.Application {

    private NotesRepository notesRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        notesRepository = new SqLiteDatabase(new DatabaseManager(
                this,
                "notes" //La extensión no es necesaria. Este es el nombre del archivo de base de datos.
        ));
        //notesRepository = new RamDatabase(); //Base de datos RAM.
    }

    public NotesRepository getNotesRepository() {
        return notesRepository;
    }
}
