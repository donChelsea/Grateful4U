package com.example.grateful4u;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grateful4u.model.Note;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.grateful4u.ViewMoodActivity.moodCalm;
import static com.example.grateful4u.ViewMoodActivity.moodConfused;
import static com.example.grateful4u.ViewMoodActivity.moodExcited;
import static com.example.grateful4u.ViewMoodActivity.moodHappy;
import static com.example.grateful4u.ViewMoodActivity.moodInspired;
import static com.example.grateful4u.ViewMoodActivity.moodInterested;
import static com.example.grateful4u.ViewMoodActivity.moodSad;

public class NewNoteActivity extends AppCompatActivity {
    protected EditText titleEt, descriptionEt;
    protected TextView moodTitleTv;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    Spinner spinner;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference notebookRef = db.collection("Notebook");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        setTitle("Create new note");

        spinner = findViewById(R.id.spinner_mood);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.mood_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        titleEt = findViewById(R.id.edittext_title);
        descriptionEt = findViewById(R.id.edittext_description);
        moodTitleTv = findViewById(R.id.textview_pick_mood);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = titleEt.getText().toString();
        String description = descriptionEt.getText().toString();
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        String mood = spinner.getSelectedItem().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "To finish that thought...", Toast.LENGTH_SHORT).show();
        } else {
            CollectionReference notebookRef = FirebaseFirestore.getInstance()
                    .collection("Notebook");
            notebookRef.add(new Note(title, description, date, mood));
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            finish();
        }

        switch ((int) spinner.getSelectedItemId()) {
            case 0:
                moodCalm = moodCalm + 1;
                return;
            case 1:
                moodExcited = moodExcited + 1;
                return;
            case 2:
                moodInterested = moodInterested + 1;
                return;
            case 3:
                moodConfused = moodConfused + 1;
                return;
            case 4:
                moodInspired = moodInspired + 1;
                return;
            case 5:
                moodHappy = moodHappy + 1;
                return;
            case 6:
                moodSad = moodSad + 1;
                return;
            default:
        }
    }

}
