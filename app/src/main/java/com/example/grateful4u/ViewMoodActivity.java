package com.example.grateful4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.grateful4u.model.Note;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
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
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mood);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.journal_item) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                } else if (id == R.id.mood_chart_item) {
                    startActivity(new Intent(getApplicationContext(), ViewMoodActivity.class));
                    return true;
                }
                return false;
            }
        });


        notesArray = new ArrayList<>();

        pieChart = findViewById(R.id.pie_chart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setCenterTextTypeface(Typeface.SANS_SERIF);
        pieChart.setEntryLabelTypeface(Typeface.SANS_SERIF);
        pieChart.animateY(3000, Easing.EaseOutBack);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }

}

