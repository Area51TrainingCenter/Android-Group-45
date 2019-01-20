package pe.area51.notesproviderclient;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textViewNotes = findViewById(R.id.textViewNotes);
        /*
         * Esta operación puede potencialmente bloquear al hilo. Recordar que el ContentProvider
         * puede tener cualquier fuente de datos internamente, por lo que esto NO debe realizarse
         * en el UI/Main Thread.
         */
        final Cursor cursor = getContentResolver().query(
                NotesContract.Note.URI,
                null,
                null,
                null,
                null
        );
        if (cursor == null) {
            return;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            stringBuilder.append(
                    cursor.getLong(cursor.getColumnIndex(NotesContract.Note.ID))
            );
            stringBuilder.append(", ");
            stringBuilder.append(
                    cursor.getString(cursor.getColumnIndex(NotesContract.Note.TITLE))
            );
            stringBuilder.append("\n");
        }
        textViewNotes.setText(stringBuilder.toString());
        cursor.close();
    }
}
