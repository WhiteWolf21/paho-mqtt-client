package com.t_engine.pahomqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        EditText confirmPassword = findViewById(R.id.confirmPassword);

        Button buttonRegister = findViewById(R.id.buttonRegister);
        TextView backLink = findViewById(R.id.backLink);
        TextView error = findViewById(R.id.error);

        backLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (username.getText().toString().equals("") || password.getText().toString().equals("") || confirmPassword.getText().toString().equals("")){
                    error.setText("Username / password / confirm password cannot be empty !!!");
                }
                else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    error.setText("Password and confirm password must be the same !!!");
                }
                else{

                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {

                                OkHttpClient client = new OkHttpClient().newBuilder()
                                        .build();
                                MediaType mediaType = MediaType.parse("text/plain");
                                RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                        .addFormDataPart("user_name",username.getText().toString())
                                        .addFormDataPart("user_password",password.getText().toString())
                                        .build();
                                Request request = new Request.Builder()
                                        .url("http://103.92.29.90:4000/api/v1/register")
                                        .method("POST", body)
                                        .build();
                                try {
                                    Response response = client.newCall(request).execute();

                                    if (response.code() == 200){
                                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Log.d("reponse", response.body().toString());
                                        String jsonData = response.body().string();
                                        JSONObject Jobject = new JSONObject(jsonData);
                                        error.setText(Jobject.get("message").toString());
                                    }

                                } catch (IOException e) {
                                    Log.e("error", e.getMessage());
                                    e.printStackTrace();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();

                }
            }
        });
    }
}