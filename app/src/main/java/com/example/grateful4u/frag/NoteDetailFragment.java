package com.example.grateful4u.frag;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.grateful4u.Note;
import com.example.grateful4u.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.grateful4u.MainActivity.DOC_ID;

public class NoteDetailFragment extends Fragment {
    protected TextView titleTv, descriptionTv, dateTv, moodTv;
    private FragmentListener listener;
    protected String title, description, date, mood;
    private String documentId;
    private FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();


    public interface FragmentListener {
        void sendToNextFragment(CharSequence data);
    }

    public static NoteDetailFragment newInstance(String docId) {
        NoteDetailFragment noteDetailFragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putString(DOC_ID, docId);
        noteDetailFragment.setArguments(args);
        return noteDetailFragment;
    }


    public NoteDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.note_detail_fragment, container, false);

        documentId = getArguments() != null ? getArguments().getString(DOC_ID) : null;
        DocumentReference docRef = firestoreDB.collection("Notebook").document(documentId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Note note = documentSnapshot.toObject(Note.class);
                title = note.getTitle();
                description = note.getDescription();
                mood = note.getMood();
                date = note.getDate();
                Log.d("frag", "frag: " + note.getDate());
            }
        });

        titleTv = v.findViewById(R.id.textview_title_detail);
        descriptionTv = v.findViewById(R.id.textview_description_detail);
        dateTv = v.findViewById(R.id.textview_date_detail);
        moodTv = v.findViewById(R.id.textview_mood_detail);

        titleTv.setText(title);
        descriptionTv.setText(description);
        dateTv.setText(date);
        moodTv.setText(mood);

        return v;

    }
}
