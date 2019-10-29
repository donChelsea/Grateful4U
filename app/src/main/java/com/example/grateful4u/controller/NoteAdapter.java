package com.example.grateful4u.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grateful4u.model.Note;
import com.example.grateful4u.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteHolder> {
    public OnNoteClickListenerInterface listenerInterface;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Note model) {
        holder.onBind(model);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_view, parent, false);
        return new NoteHolder(view);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        protected TextView titleTv, dateTv, descriptionTv, moodTv;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.textview_note_title);
            dateTv = itemView.findViewById(R.id.textview_note_date);
            descriptionTv = itemView.findViewById(R.id.textview_note_preview);
            moodTv = itemView.findViewById(R.id.textview_mood);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listenerInterface != null) {
                        listenerInterface.onNoteClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }

        public void onBind(Note note) {
            titleTv.setText(note.getTitle());
            dateTv.setText(note.getDate());
            descriptionTv.setText(note.getDescription());
            moodTv.setText(note.getMood());
        }


    }

    public interface OnNoteClickListenerInterface {
        void onNoteClick (DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnNoteClickListener(OnNoteClickListenerInterface listener) {
        this.listenerInterface = listener;
    }


}
