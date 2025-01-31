package com.example.foodtrack.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Model.API.NguoiDungAPIModel;
import com.example.foodtrack.Model.NguoiDungModel;
import com.example.foodtrack.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    ImageView btn_back;
    TextView btnDangKiFormLogin;
    TextView btnQuenMatkhau;
    TextView btnLogin_TK;
    TextView edit_mail, edit_password;
    CheckBox cb_nho_mat_khau_login ;
    SharedPreferences sharedPreferences, sharedUsername, shareUserResponseLogin;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        //có cái này để lưu vào sharedPreferences, tới lúc chat thì biết đâu là currentUser
        currentUser = getIntent().getStringExtra("currentUsername");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edit_mail = findViewById(R.id.edtMail_login);
        edit_password = findViewById(R.id.edtPassword_login);
        btn_back =  findViewById(R.id.btn_back);
        btnDangKiFormLogin =findViewById(R.id.btnDangKi_formLogin);
        btnQuenMatkhau = findViewById(R.id.btnQuenMatKhau);
        btnLogin_TK = findViewById(R.id.btn_Login_TK);
        cb_nho_mat_khau_login = findViewById(R.id.cb_nho_mat_khau_login);


        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);

        sharedUsername = getSharedPreferences("currentUser", MODE_PRIVATE);

        shareUserResponseLogin = getSharedPreferences("shareUserResponseLogin",MODE_PRIVATE);

        if(currentUser == null){
            currentUser = sharedUsername.getString("currentUser", "");
        }

        edit_mail.setText(sharedPreferences.getString("email",""));
        edit_password.setText(sharedPreferences.getString("password",""));
        cb_nho_mat_khau_login.setChecked(sharedPreferences.getBoolean("checked",false));


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent firstpage = new Intent(Login.this, first_page.class);
                startActivity(firstpage);
                finish();
            }
        });
        btnDangKiFormLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formDangKi = new Intent(Login.this, Register.class);
                startActivity(formDangKi);
                finish();
            }
        });
        btnQuenMatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formquenMK = new Intent(Login.this, forgot_password.class);
                startActivity(formquenMK);
            }
        });

        btnLogin_TK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = edit_mail.getText().toString().trim();
                String password = edit_password.getText().toString().trim();
                NguoiDungAPIModel userModel = new NguoiDungAPIModel();
                userModel.setEmail(email);
                userModel.setMatKhau(password);
                if(cb_nho_mat_khau_login.isChecked()){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putString("password",password);
                    editor.putBoolean("checked", true);
                    editor.apply();

                }

                //test chat nè
                SharedPreferences.Editor currentUserEditor = sharedUsername.edit();
                currentUserEditor.putString("currentUser", currentUser);
                currentUserEditor.apply();

//
//                Intent home = new Intent(Login.this, MainActivity.class);
//                startActivity(home);
//                finish();
                // comment để pass login

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Vui lòng nhập email và password trước khi nhấn đăng nhập", Toast.LENGTH_SHORT).show();
                } else {
                    // hàm login
                    GetUserToLogin(userModel);

                }

            }
        });
    }
    public void GetUserToLogin(NguoiDungAPIModel userModel) {
        APIService.API_SERVICE.GetUserToLogin(userModel).enqueue(new Callback<NguoiDungAPIModel>() {
            @Override
            public void onResponse(Call<NguoiDungAPIModel> call, Response<NguoiDungAPIModel> response) {
                if (response.code() == 200) { // Kiểm tra status code
                    NguoiDungAPIModel responseUserModel = response.body();
                    if (responseUserModel != null && "Đăng nhập thành công".equals(responseUserModel.getMessage())) {

                        SharedPreferences.Editor editorResponseLogin = shareUserResponseLogin.edit();
                        editorResponseLogin.putString("avatar",responseUserModel.getAvatar());
                        editorResponseLogin.putString("email",responseUserModel.getEmail());
                        editorResponseLogin.putString("ngaySinh", responseUserModel.getNgaySinh());
                        editorResponseLogin.putString("idUser",responseUserModel.getIdUser());
                        editorResponseLogin.putString("hoTenNguoiDung",responseUserModel.getHoTenNguoiDung());

                        editorResponseLogin.putString("diaChi",responseUserModel.getDiaChi());
                        editorResponseLogin.putString("sdt", responseUserModel.getSdt());
                        editorResponseLogin.putString("gioiTinh", responseUserModel.getGioiTinh());

                        editorResponseLogin.putString("idPhongChat", responseUserModel.getIdPhongChat());

                        editorResponseLogin.putInt("tichDiem", responseUserModel.getTichDiem());



                        editorResponseLogin.apply();


                        Intent home = new Intent(Login.this, MainActivity.class);
                        startActivity(home);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Đăng nhập thất bại với mã lỗi " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NguoiDungAPIModel> call, Throwable t) {
                Toast.makeText(Login.this, "Đăng nhập thất bại, thử lại bằng email và password", Toast.LENGTH_LONG).show();
            }
        });
    }





}