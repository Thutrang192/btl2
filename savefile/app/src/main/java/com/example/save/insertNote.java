package com.example.save;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class insertNote extends AppCompatActivity {

    Button btnSelectDate, btnLuu;
    EditText edtTitle, edtContent;
    TextView tvCreateDate;
    final Calendar calendar = Calendar.getInstance();
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insert_note);
        getView();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbManager = new DBManager(insertNote.this);
        dbManager.open(); // mo ket noi toi csdl


        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(insertNote.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        tvCreateDate.setText(date + "/" + (month + 1) + "/" + year);
                    }
                }, calendar.get(Calendar.YEAR),
                   calendar.get(Calendar.MONTH),
                   calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // luu vao database
                dbManager.insNote(getNote());
                dbManager.close();

                Intent intent = new Intent(insertNote.this, MainActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // xoa tat ca cac activity phia tren
                startActivity(intent);
            }
        });
    }


    private void getView() {
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnLuu = findViewById(R.id.btnLuu);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        tvCreateDate = findViewById(R.id.tvCreateDate);
    }

    private Note getNote() {
        return new Note(edtTitle.getText().toString(),
                tvCreateDate.getText().toString(),
                edtContent.getText().toString());
    }

}