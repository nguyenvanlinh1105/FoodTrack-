package com.example.foodtrack.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionProvider;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Model.UserModel;
import com.example.foodtrack.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private EditText edt_hoTen_signin,edt_Mail_signin,edt_SDT_signin,edt_Password_signin,edt_confirmPassword_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InitView();
        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent firstpage = new Intent(Register.this, first_page.class);
                startActivity(firstpage);
                finish();
            }
        });
        TextView btnDangNhap_formRegister = findViewById(R.id.btnDangNhap_formRegister);
        btnDangNhap_formRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(Register.this, Login.class);
                startActivity(login);
                finish();
            }
        });
        TextView  btnDangKi = findViewById(R.id.btn_DangKi_TK);
        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoTen = edt_hoTen_signin.getText().toString().trim();
              //  String username = edt_UserName_signin.getText().toString();
                String email = edt_Mail_signin.getText().toString().trim();
                String sdt = edt_SDT_signin.getText().toString().trim();
                String password = edt_Password_signin.getText().toString().trim();

                String gioiTinh = "Nam";

                Log.d("MatKhau",password);

                UserModel userModel = new UserModel();
                userModel.setHoTenNguoiDung(hoTen);
                userModel.setEmail(email);
                userModel.setSdt(sdt);
                userModel.setMatKhau(password);
                userModel.setGioiTinh(gioiTinh);
                PostUserToSingin(userModel);
            }
        });

    }

    private void InitView(){
        edt_hoTen_signin = findViewById(R.id.edt_hoTen_signin);
        edt_Mail_signin = findViewById(R.id.edt_Mail_signin);
        edt_SDT_signin = findViewById(R.id.edt_SDT_signin);
        edt_Password_signin = findViewById(R.id.edt_Password_signin);
        edt_confirmPassword_signin = findViewById(R.id.edt_confirmPassword_signin);


    }

    private void PostUserToSingin (UserModel userModel){
        APIService.API_SERVICE.PostUserToSingin(userModel).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Intent login = new Intent(Register.this, Login.class);
                startActivity(login);
                finish();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(Register.this, "Đăng kí thất bại, thử lại.", Toast.LENGTH_LONG).show();
            }
        });
    }
}