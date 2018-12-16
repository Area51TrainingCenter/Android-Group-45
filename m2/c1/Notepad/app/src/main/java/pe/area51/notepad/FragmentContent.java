package pe.area51.notepad;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class FragmentContent extends Fragment {

    private static final String KEY_ARG_NOTE_ID = "note_id";
    private static final String KEY_ARG_NOTE_TITLE = "note_title";
    private static final String KEY_ARG_NOTE_CONTENT = "note_content";
    private static final String KEY_ARG_NOTE_CREATION_TIMESTAMP = "note_creation_timestamp";

    public static FragmentContent newInstance(final Note note) {
        final FragmentContent contentFragment = new FragmentContent();
        final Bundle arguments = new Bundle();
        arguments.putString(KEY_ARG_NOTE_ID, note.getId());
        arguments.putString(KEY_ARG_NOTE_TITLE, note.getTitle());
        arguments.putString(KEY_ARG_NOTE_CONTENT, note.getContent());
        arguments.putLong(KEY_ARG_NOTE_CREATION_TIMESTAMP, note.getCreationTimestamp());
        contentFragment.setArguments(arguments);
        return contentFragment;
    }

    private TextView textViewDate;
    private TextView textViewContent;

    private Note note;

    private FragmentInteractionInterface fragmentInteractionInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentInteractionInterface = (FragmentInteractionInterface) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle arguments = getArguments();
        checkArguments(arguments);
        note = new Note(
                arguments.getString(KEY_ARG_NOTE_ID),
                arguments.getString(KEY_ARG_NOTE_TITLE),
                arguments.getString(KEY_ARG_NOTE_CONTENT),
                arguments.getLong(KEY_ARG_NOTE_CREATION_TIMESTAMP)
        );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_content, container, false);
        textViewDate = view.findViewById(R.id.textViewNoteDate);
        textViewContent = view.findViewById(R.id.textViewNoteContent);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showNote();
        fragmentInteractionInterface.setTitle(note.getTitle());
    }

    private static void checkArguments(final Bundle arguments) {
        if (arguments != null &&
                arguments.containsKey(KEY_ARG_NOTE_ID) &&
                arguments.containsKey(KEY_ARG_NOTE_TITLE) &&
                arguments.containsKey(KEY_ARG_NOTE_CONTENT) &&
                arguments.containsKey(KEY_ARG_NOTE_CREATION_TIMESTAMP)) {
            return;
        }
        throw new RuntimeException("Fragment doesn't have needed arguments. Call newInstance() static creation method.");
    }

    private void showNote() {
        final Date noteDate = new Date(note.getCreationTimestamp());
        final DateFormat dateFormat = DateFormat.getInstance();
        textViewDate.setText(dateFormat.format(noteDate));
        textViewContent.setText(note.getContent());
    }
}
