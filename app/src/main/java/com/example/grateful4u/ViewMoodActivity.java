package com.example.grateful4u;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.grateful4u.model.Note;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ViewMoodActivity extends AppCompatActivity {
    private PieChart pieChart;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference notebookRef = db.collection("Notebook");
    ArrayList<Note> notesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mood);

        notesArray = new ArrayList<>();

        pieChart = findViewById(R.id.pie_chart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        notebookRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (Note note : queryDocumentSnapshots.toObjects(Note.class)) {
                    notesArray.add(note);
                }
            }
        });

        yValues.add(new PieEntry(25f, "Happy"));
        yValues.add(new PieEntry(15f, "Calm"));
        yValues.add(new PieEntry(15f, "Interested"));
        yValues.add(new PieEntry(50f, "Inspired"));
        yValues.add(new PieEntry(10f, "Sad"));
        yValues.add(new PieEntry(5f, "Confused"));
        yValues.add(new PieEntry(15, "Excited"));

        yValues.forEach(new Consumer<PieEntry>() {
            @Override
            public void accept(PieEntry pieEntry) {
                Log.d("yvalues", "values: " + pieEntry.getLabel());
            }
        });

        PieDataSet dataSet = new PieDataSet(yValues, "Mood");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);

        pieChart.setData(pieData);

    }
}

