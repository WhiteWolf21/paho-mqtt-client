package com.t_engine.pahomqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        TextView backLink = findViewById(R.id.backLink);
        final ListView list = findViewById(R.id.productList);

        backLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProcessActivity.class);
                startActivity(intent);
            }
        });

        Context ctx = this;

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                Bundle extras = getIntent().getExtras();

                try  {

                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    MediaType mediaType = MediaType.parse("text/plain");
                    RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("products",extras.getString("type"))
                            .build();
                    Request request = new Request.Builder()
                            .url("http://103.92.29.90:4000/api/v1/products")
                            .method("POST", body)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();

                        if (response.code() == 200){

                            String jsonData = response.body().string();
                            JSONObject Jobject = new JSONObject(jsonData);

                            final JSONArray myResponse = Jobject.getJSONArray("products");

                            ArrayList<SubjectData> arrayList = new ArrayList<SubjectData>();
                            JSONObject object = null;

                            for (int i = 0; i < myResponse.length(); i++){

                                object = myResponse.getJSONObject(i);

                                arrayList.add(new SubjectData(object.getString(extras.getString("type") + "_name"), object.getString(extras.getString("type") + "_time"), "http://103.92.29.90:4000/static/" + object.getString(extras.getString("type") + "_id") + ".png"));

                            }

                            if (myResponse.length() > 0){
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        // Stuff that updates the UI
                                        CustomAdapter customAdapter = new CustomAdapter(ctx, arrayList);
                                        list.setAdapter(customAdapter);

                                    }
                                });
                            }

                        }

                    } catch (IOException | JSONException e) {
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