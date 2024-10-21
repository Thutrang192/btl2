package com.example.save;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    FloatingActionButton btnInsNote;
    ListView lvNotes;
    NoteAdapter adapter;
    ArrayList<Note> listNotes = new ArrayList<>();
    DBManager dbManager;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        getView();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // doc du lieu tu dataset va hien thi len listview
        dbManager = new DBManager(MainActivity2.this);
        dbManager.open();
        listNotes = dbManager.getAllNotes();
        adapter = new NoteAdapter(MainActivity2.this, R.layout.layout_note, listNotes);
        adapter.notifyDataSetChanged();
        lvNotes.setAdapter(adapter);

        btnInsNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, insertNote.class);
                startActivity(intent);
            }
        });

        //khoi tao ActivityResultLauncher
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // cap nhat danh sach ghi chu
                        listNotes.clear(); // xoa danh sach hien tai
                        listNotes.addAll(dbManager.getAllNotes());
                        adapter.notifyDataSetChanged(); // cap nhat thay doi
                    }
                }
        );

        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int id = listNotes.get(position).getId();
                Note selecItem = listNotes.get(position);
                Intent intent = new Intent(MainActivity2.this, UpDelActivity.class);
                intent.putExtra("NOTE_ID", id);
                intent.putExtra("NOTE_TITLE", selecItem.getTitle());
                intent.putExtra("NOTE_CONTENT", selecItem.getContent());

                activityResultLauncher.launch(intent);
            }
        });
    }

    private void getView() {
        btnInsNote = findViewById(R.id.btnInsNote);
        lvNotes = findViewById(R.id.lvNotes);
    }
}