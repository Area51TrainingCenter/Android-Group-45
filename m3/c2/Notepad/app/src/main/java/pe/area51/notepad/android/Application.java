package pe.area51.notepad.android;

import android.arch.persistence.room.Room;

import io.requery.android.database.sqlite.RequerySQLiteOpenHelperFactory;
import pe.area51.notepad.data.room.RoomDatabase;
import pe.area51.notepad.data.room.RoomNotesRepository;
import pe.area51.notepad.domain.NotesRepository;

public class Application extends android.app.Application {

    private NotesRepository notesRepository;
    private ViewModelFactory viewModelFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        initRoomRepository();
        viewModelFactory = new ViewModelFactory(notesRepository);
    }

    public NotesRepository getNotesRepository() {
        /*
         * Normalmente cuando se inicia el proceso de la aplicación (por ejemplo al seleccionar el
         * ícono del lanzador), se crea primero la clase Application, se llama su método "onCreate"
         * y luego se inicia el componente (por ejemplo el Activity). Si el primer componente en
         * iniciarse es un ContentProvider (por ejemplo en el caso que otra aplicación se comunique
         * con el ContentProvider y el proceso aún no se ha creado) entonces se creará primero la
         * clase Application pero NO se llamará a su método "onCreate". Esta es la razón de hacer
         * esta verificación.
         */
        if (notesRepository == null) {
            initRoomRepository();
        }
        return notesRepository;
    }

    public ViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }

    private void initRoomRepository() {
        final RoomDatabase roomDatabase = Room.databaseBuilder(
                this,
                RoomDatabase.class,
                "notes-room"
        ).openHelperFactory(new RequerySQLiteOpenHelperFactory())
                .build();
        notesRepository = new RoomNotesRepository(roomDatabase);
    }
}
