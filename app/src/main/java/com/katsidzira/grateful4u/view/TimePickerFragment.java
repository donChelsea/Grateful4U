package com.katsidzira.grateful4u.view;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.katsidzira.grateful4u.AlertReceiver;
import com.katsidzira.grateful4u.R;

import java.text.DateFormat;
import java.util.Calendar;


public class TimePickerFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {
    TimePicker timePicker;
    Button button;
    TextView textView;

    public TimePickerFragment() {
    }

    public static TimePickerFragment newInstance() {
        TimePickerFragment fragment = new TimePickerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_picker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        timePicker = view.findViewById(R.id.time_picker);
        button = view.findViewById(R.id.set_alarm_button);
        textView = view.findViewById(R.id.time_text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTimeSet(timePicker, timePicker.getHour(), timePicker.getMinute());
            }
        });

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        updateTimeText(calendar);
        startAlarm(calendar);
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void updateTimeText(Calendar c) {
        String timeText = "Next time to write is: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        textView.setText(timeText);
    }
}
