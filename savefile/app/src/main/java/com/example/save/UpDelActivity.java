package com.example.save;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpDelActivity extends AppCompatActivity {

    TextView tvDate;
    EditText edtContent, edtTitle;
    Button btnDelete, btnEdit;
    private DBManager dbManager;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_up_del);
        getView();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // nhan gia tri tu intent
        id = getIntent().getIntExtra("NOTE_ID", -1);
        String title = getIntent().getStringExtra("NOTE_TITLE");
        String content = getIntent().getStringExtra("NOTE_CONTENT");

        // hien thi du lieu
        edtTitle.setText(title);
        edtContent.setText(content);

        dbManager = new DBManager(this);
        dbManager.open();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTitle = edtTitle.getText().toString();
                String newContent = edtContent.getText().toString();
                dbManager.updNote(id, newTitle, newContent);

                // tra lai ket qua cho ManiAcitivity2
                setResult(RESULT_OK);

                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDel();
            }
        });
    }

    private void getView() {
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        tvDate = findViewById(R.id.tvDate);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);
    }

    private void confirmDel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa ghi chú này");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dbManager.delNote(id);
                setResult(RESULT_OK); // dam bao cap nhat truoc khi xoa
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss(); // dong hop thoai
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}