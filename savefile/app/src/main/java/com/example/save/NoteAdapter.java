package com.example.save;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<Note> {

    private Context context;
    private int layout;
    ArrayList<Note> lsNotes;

    public NoteAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Note> objects) {
        super(context, resource, objects);
        this.context = context;
        this.lsNotes = objects;
        this.layout = resource;
    }

    @Override
    public int getCount() {
        return lsNotes.size();
    }

    // ctrl+0
    @Nullable
    @Override
    public Note getItem(int position) {
        return lsNotes.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;
        if (currentView == null) {
            currentView = LayoutInflater.from(context).inflate(layout, parent, false);
        }

        Note note = getItem(position);
        // kiem tra note co rong khong
        if (note != null) {

            TextView tvTitle = currentView.findViewById(R.id.tvTitle);
            TextView tvDate = currentView.findViewById(R.id.tvDate);
            TextView tvContent = currentView.findViewById(R.id.tvContent);
            tvTitle.setText(note.getTitle());
            tvDate.setText(note.getCreateDate());
            tvContent.setText(note.getContent());
        }
        return currentView;
    }
}

