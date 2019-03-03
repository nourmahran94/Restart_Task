package com.nour.restasrttask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    //Define Views
    EditText etEmail, etPassword;
    TextView tvRegister;
    Button btnLogin , btnLoginWithFaceBook ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        addListeners();

    }


    private void findViews() {
        etEmail = findViewById(R.id.et_Email);
        etPassword = findViewById(R.id.et_Password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_Register);
        btnLoginWithFaceBook = findViewById(R.id.btn_login_with_facebook);
    }

    private void addListeners() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(i);

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = etEmail.getText().toString();
                String Password = etPassword.getText().toString();
                if (validataLogin(Email, Password)) {

                    intCallBack(Email, Password);

                }
            }
        });
        btnLoginWithFaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithFaceBook();
            }
        });

    }

    private void loginWithFaceBook(){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UserModel> Call = apiInterface.facebookLogin("1", "FaceBook");
        Call.enqueue(new Callback<UserModel>() {

            @Override

            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                System.out.println("---------- on response");

                if (response.isSuccessful()) {
                    UserModel userModel = response.body();

                       Toast.makeText(LoginActivity.this , "Login Successfully" , Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(LoginActivity.this, "response body is empty", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                System.out.println("-------------error");

                Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();

            }

        });
    }

    private boolean validataLogin(String Email, String Password) {
        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void intCallBack(final String Email, final String Password) {


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UserModel> Call = apiInterface.userLogin(Email, Password);
        Call.enqueue(new Callback<UserModel>() {

            @Override

            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                System.out.println("---------- on response");

                if (response.isSuccessful()) {
                    UserModel userModel = response.body();

                    if (userModel.getClass().equals("true")) {
                        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                        i.putExtra("Email", Email);
                        startActivity(i);
                    } else {
                        Toast.makeText(LoginActivity.this, "The E-mail or password is incorrect", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Error Please Try Again", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                System.out.println("-------------error");

                Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();

            }

        });
    }
}

