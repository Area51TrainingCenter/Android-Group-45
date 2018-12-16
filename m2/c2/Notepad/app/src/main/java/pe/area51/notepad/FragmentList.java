package pe.area51.notepad;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.Random;

import pe.area51.notepad.domain.Note;
import pe.area51.notepad.domain.NotesRepository;

public class FragmentList extends Fragment {

    public static final String TAG = "ListFragment";

    private FragmentInteractionInterface fragmentInteractionInterface;

    private ArrayAdapter<Note> notesArrayAdapter;
    private NotesRepository notesRepository;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentInteractionInterface = (FragmentInteractionInterface) context;
        final Application application = (Application) context.getApplicationContext();
        notesRepository = application.getNotesRepository();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionCreateNote:
                createNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        final ListView listViewElements = view.findViewById(R.id.listView);
        notesArrayAdapter = new NoteAdapter(getActivity());
        listViewElements.setAdapter(notesArrayAdapter);
        notesArrayAdapter.addAll(notesRepository.getAllNotes());
        listViewElements.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Note note = notesArrayAdapter.getItem(position);
                fragmentInteractionInterface.showNoteContent(note.getId());
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentInteractionInterface.setTitle(getString(R.string.note_list_title));
    }

    private void createNote() {
        final Random random = new Random();
        final int randomInt = random.nextInt();
        final Note note = new Note(
                "Title " + randomInt,
                getString(R.string.lorem_ipsum),
                System.currentTimeMillis()
        );
        final Note createdNote = notesRepository.createNote(note);
        notesArrayAdapter.add(createdNote);
    }

    private static class NoteAdapter extends ArrayAdapter<Note> {

        private final LayoutInflater layoutInflater;
        private final ColorGenerator colorGenerator;

        public NoteAdapter(final Context context) {
            super(context, 0);
            layoutInflater = LayoutInflater.from(getContext());
            colorGenerator = ColorGenerator.MATERIAL;
        }

        private static class ViewHolder {
            private TextView titleTextView;
            private ImageView imageViewLetter;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "Position: " + position + "; convertView " + (convertView == null ? "== null" : "!= null"));
            final Note note = getItem(position);
            final View view;
            final ViewHolder viewHolder;
            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.element_note, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.titleTextView = view.findViewById(R.id.textViewNoteTitle);
                viewHolder.imageViewLetter = view.findViewById(R.id.imageViewLetter);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String noteTitle = note.getTitle();
            if (noteTitle.trim().length() == 0) {
                noteTitle = getContext().getString(R.string.element_untitled_note);
            }
            viewHolder.titleTextView.setText(noteTitle);
            viewHolder.imageViewLetter.setImageDrawable(
                    TextDrawable
                            .builder()
                            .buildRound(
                                    String.valueOf(noteTitle.charAt(0)),
                                    colorGenerator.getColor(note.getId())
                            )
            );
            return view;
        }
    }
}
