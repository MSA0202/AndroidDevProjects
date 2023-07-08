package com.example.covidwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    String name; String email; String password; String passwordconf;
    Button blogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //convert user input to strings
        EditText nam = (EditText)findViewById(R.id.inputName);
        EditText emal = (EditText)findViewById(R.id.inputEmail);
        EditText pwd = (EditText)findViewById(R.id.inputPassword);
        EditText pwdcnf = (EditText)findViewById(R.id.inputConfirmPassword);

        //php to send request
        String reg = "https://lamp.ms.wits.ac.za/~s2328024/RegInsert.php";

        blogin = (Button)findViewById(R.id.btnLogin);
        blogin.setOnClickListener(new View.OnClickListener() { //b1 displays
            @Override
            public void onClick(View v) {
                name = nam.getText().toString();
                email= emal.getText().toString();
                password = pwd.getText().toString();
                passwordconf = pwdcnf.getText().toString();

                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder()
                        .add("name", name)
                        .add("email", email)
                        .add("password", password)
                        .add("passwordconf",passwordconf)
                        .build();

                Request request = new Request.Builder()
                        .url(reg)
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String myResponse = response.body().string();

                            RegisterActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, myResponse, Toast.LENGTH_SHORT).show();
                                    if(myResponse.equals("Account has been registered. Please login.")){
                                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                    }

                                }
                            });
                        }
                    }
                });
            }
        });

        TextView btnHaveAccount = findViewById(R.id.AlreadyHaveAccount);
        btnHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }
}