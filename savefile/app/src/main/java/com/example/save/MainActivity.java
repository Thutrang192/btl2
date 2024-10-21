package com.example.save;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnLuu, btnSelectDate;
    TextView tvCreataDate;
    EditText edtTitle, edtContent;

    final

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getViews() {
        btnLuu = findViewById(R.id.btnLuu);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        tvCreataDate = findViewById(R.id.tvCreateDate);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
    }

    private Note getNote() {
        return new Note(edtTitle.getText().toString(), tvCreataDate.getText().toString(), edtContent.getText().toString());
    }
}