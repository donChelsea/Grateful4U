package com.katsidzira.grateful4u.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.katsidzira.grateful4u.R;
import com.katsidzira.grateful4u.model.Note;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.katsidzira.grateful4u.view.MainActivity.DOC_ID;

public class ViewNoteActivity extends AppCompatActivity {
    protected TextView titleTv, descriptionTv, dateTv, moodTv;
    protected String title, description, date, mood;
    private String documentId;
    private FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        setTitle("View note");

        documentId = getIntent().getStringExtra(DOC_ID);
        DocumentReference docRef = firestoreDB.collection("Notebook").document(documentId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Note note = documentSnapshot.toObject(Note.class);
                title = note.getTitle();
                description = note.getDescription();
                mood = note.getMood();
                date = note.getDate();

                titleTv = findViewById(R.id.textview_title_detail);
                descriptionTv = findViewById(R.id.textview_description_detail);
                dateTv = findViewById(R.id.textview_date_detail);
                moodTv = findViewById(R.id.textview_mood_detail);

                titleTv.setText(title);
                descriptionTv.setText(description);
                dateTv.setText(date);
                moodTv.setText(mood);
            }
        });
    }
}