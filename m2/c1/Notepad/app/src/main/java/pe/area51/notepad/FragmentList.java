package pe.area51.notepad;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

public class FragmentList extends Fragment {

    public static final String TAG = "ListFragment";

    private FragmentInteractionInterface fragmentInteractionInterface;

    private ArrayAdapter<Note> notesArrayAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentInteractionInterface = (FragmentInteractionInterface) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        final ListView listViewElements = view.findViewById(R.id.listView);
        notesArrayAdapter = new NoteAdapter(getActivity());
        notesArrayAdapter.addAll(createTestNotes(300));
        listViewElements.setAdapter(notesArrayAdapter);
        listViewElements.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Note note = notesArrayAdapter.getItem(position);
                fragmentInteractionInterface.showNoteContent(note);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentInteractionInterface.setTitle(getString(R.string.note_list_title));
    }

    private List<Note> createTestNotes(final int size) {
        final List<Note> notes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            notes.add(new Note(
                    String.valueOf(i),
                    "Note Title " + i,
                    getString(R.string.lorem_ipsum),
                    System.currentTimeMillis()
            ));
        }
        return notes;
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
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
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
