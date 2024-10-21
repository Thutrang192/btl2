package com.example.save;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class save_file extends AppCompatActivity {

    EditText edtUser, edtPass;
    CheckBox chkSave;
    Button btnDangNhap;
    public static final String FName = "account.xml";
    SharedPreferences preferences;
    public  static  final int MODE = Activity.MODE_PRIVATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_save_file);

        getView();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // lấy ra đối tượng sharepreference để thực hiện đọc ghi dữ liệu
        preferences = getSharedPreferences(FName, MODE);

        // đọc thông tin tài khoản đã lưu (nếu có)
        readAccount();

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAccount();
            }
        });
    }

    protected void getView() {
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        chkSave = findViewById(R.id.chkSave);
        btnDangNhap = findViewById(R.id.btnDangNhap);
    }

    public void saveAccount() {
        // lấy ra đối tượng editor để ghi dữ liệu vào file
        if (chkSave.isChecked()) {
            SharedPreferences.Editor editor = preferences.edit();

            // put dữ liệu vào file xml
            editor.putBoolean("save", chkSave.isChecked());
            editor.putString("username", edtUser.getText().toString());
            editor.putString("pass", edtPass.getText().toString());

            // hoàn thành việc ghi dữ liệu
            editor.commit();
        }
    }

    private void readAccount() {
        // kiem tra xem co luu tai khoan khong
        boolean isSave = preferences.getBoolean("save", false);

        if (isSave) {
            // neu da luu --> doc va hien thi
            // llay chuoi da duoc luu voi khoa la username, neu khong tim thay gia tri null duoc tra ve
            String name = preferences.getString("username", null);
            String pass = preferences.getString("pass", null);

            if (name != null && pass != null) {
                edtUser.setText(name);
                edtPass.setText(pass);
            }

        } else {
            chkSave.setChecked(false);
        }
    }
}