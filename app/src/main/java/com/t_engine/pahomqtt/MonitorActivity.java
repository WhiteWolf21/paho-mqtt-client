package com.t_engine.pahomqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MonitorActivity extends AppCompatActivity {

    TextView time;
    ViewGroup submitLayout;
    MQTTHelper mqttHelper;
    private void startMQTT(){
        mqttHelper = new MQTTHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.d(" SEND MESSAGE --- ", "true");
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.d("MQTT:", mqttMessage.toString());
                time.setText(mqttMessage.toString());
            }


            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    private void sendMQTTMessage(String payload){
        mqttHelper.connectToPublish(payload);
    }

    private void subscribeMQTTMessage(String topic){
        mqttHelper.connectToSubscribe(topic);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        TextView backLink = findViewById(R.id.backLink);
        backLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        startMQTT();

        submitLayout = findViewById(R.id.submitLayout);

        Button startButton = findViewById(R.id.startButton);

        Button tempUp = findViewById(R.id.tempUp);
        Button tempDown = findViewById(R.id.tempDown);

        Button moisUp = findViewById(R.id.moisUp);
        Button moisDown = findViewById(R.id.moisDown);

        Button fanUp = findViewById(R.id.fanUp);
        Button fanDown = findViewById(R.id.fanDown);

        TextView productId = findViewById(R.id.productId);
        TextView content = findViewById(R.id.content);
        TextView timeTitle = findViewById(R.id.timeTitle);
        time = findViewById(R.id.time);

        ImageView image = findViewById(R.id.productImage);

        EditText temp = findViewById(R.id.temp);

        temp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (Double.parseDouble(temp.getText().toString()) <= 40.0){
                        temp.setText("40.0");
                    }
                    else if (Double.parseDouble(temp.getText().toString()) >= 100.0){
                        temp.setText("100.0");
                    }

                }
            }
        });

        EditText mois = findViewById(R.id.mois);

        mois.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (Double.parseDouble(mois.getText().toString()) <= 30.0){
                        mois.setText("30.0");
                    }
                    else if (Double.parseDouble(mois.getText().toString()) >= 80.0){
                        mois.setText("100.0");
                    }

                }
            }
        });

        EditText fan = findViewById(R.id.fan);

        fan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (Double.parseDouble(fan.getText().toString()) <= 0.0){
                        fan.setText("0.0");
                    }
                    else if (Double.parseDouble(fan.getText().toString()) >= 100.0){
                        fan.setText("100.0");
                    }

                }
            }
        });


        // Button

        tempUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (Double.parseDouble(temp.getText().toString()) <= 40.0){
                    temp.setText("40.0");
                }
                else if (Double.parseDouble(temp.getText().toString()) >= 100.0){
                    temp.setText("100.0");
                }
                else{
                    temp.setText(String.valueOf(Double.parseDouble(temp.getText().toString()) + 0.5));
                }
            }
        });

        tempDown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (Double.parseDouble(temp.getText().toString()) <= 40.0){
                    temp.setText("40.0");
                }
                else if (Double.parseDouble(temp.getText().toString()) >= 100.0){
                    temp.setText("100.0");
                }
                else{
                    temp.setText(String.valueOf(Double.parseDouble(temp.getText().toString()) - 0.5));
                }

            }
        });

        moisUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (Double.parseDouble(mois.getText().toString()) <= 30.0){
                    mois.setText("30.0");
                }
                else if (Double.parseDouble(mois.getText().toString()) >= 80.0){
                    mois.setText("100.0");
                }
                else {
                    mois.setText(String.valueOf(Double.parseDouble(mois.getText().toString()) + 1));
                }

            }
        });

        moisDown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (Double.parseDouble(mois.getText().toString()) <= 30.0){
                    mois.setText("30.0");
                }
                else if (Double.parseDouble(mois.getText().toString()) >= 80.0){
                    mois.setText("100.0");
                }
                else {
                    mois.setText(String.valueOf(Double.parseDouble(mois.getText().toString()) - 1));
                }

            }
        });

        fanUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (Double.parseDouble(fan.getText().toString()) <= 0.0){
                    fan.setText("0.0");
                }
                else if (Double.parseDouble(fan.getText().toString()) >= 100.0){
                    fan.setText("100.0");
                }
                else{
                    fan.setText(String.valueOf(Double.parseDouble(fan.getText().toString()) + 1));
                }

            }
        });

        fanDown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (Double.parseDouble(fan.getText().toString()) <= 0.0){
                    fan.setText("0.0");
                }
                else if (Double.parseDouble(fan.getText().toString()) >= 100.0){
                    fan.setText("100.0");
                }
                else{
                    fan.setText(String.valueOf(Double.parseDouble(fan.getText().toString()) - 1));
                }

            }
        });

        Context context = this;

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {

                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            MediaType mediaType = MediaType.parse("text/plain");
                            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                    .addFormDataPart("time",time.getText().toString().split(" ")[0])
                                    .addFormDataPart("temp",temp.getText().toString())
                                    .addFormDataPart("mois",mois.getText().toString())
                                    .addFormDataPart("fan",fan.getText().toString())
                                    .addFormDataPart("name",content.getText().toString())
                                    .addFormDataPart("id",productId.getText().toString())
                                    .build();
                            Request request = new Request.Builder()
                                    .url("http://103.92.29.90:4000/api/v1/execute")
                                    .method("POST", body)
                                    .build();
                            try {
                                Response response = client.newCall(request).execute();

                                if (response.code() == 200){

                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {

                                            timeTitle.setText("Drying Time Left:");
                                            submitLayout.removeView(startButton);
                                            ((MonitorActivity) context).subscribeMQTTMessage(content.getText().toString());

                                        }
                                    });

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
                            .addFormDataPart("products","previous")
                            .addFormDataPart("name",extras.getString("product_name"))
                            .build();
                    Request request = new Request.Builder()
                            .url("http://103.92.29.90:4000/api/v1/product")
                            .method("POST", body)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();

                        if (response.code() == 200){

                            String jsonData = response.body().string();
                            JSONObject Jobject = new JSONObject(jsonData);

                            Log.d("Jobject", Jobject.getString("product").toString());

                            if (!Jobject.getString("product").toString().equals("null")){

                                JSONObject finalJobject = Jobject.getJSONObject("product");

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        // Stuff that updates the UI
                                        try {
                                            timeTitle.setText("Estimated Drying Time:");
                                            time.setText(finalJobject.getString("previous_time") + " hours");
                                            content.setText(finalJobject.getString("previous_name"));
                                            productId.setText(finalJobject.getString("previous_id"));

                                            Picasso.with(ctx)
                                                    .load("http://103.92.29.90:4000/static/" + finalJobject.getString("previous_id") + ".png")
                                                    .into(image);

                                            temp.setText(finalJobject.getString("previous_temp"));
                                            mois.setText(finalJobject.getString("previous_mois"));
                                            fan.setText(finalJobject.getString("previous_fan"));

                                            timeTitle.setText("Drying Time Left:");
                                            submitLayout.removeView(startButton);
                                            ((MonitorActivity) context).subscribeMQTTMessage(content.getText().toString());

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                            }
                            else{

                                client = new OkHttpClient().newBuilder()
                                        .build();
                                mediaType = MediaType.parse("text/plain");
                                body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                        .addFormDataPart("products","product")
                                        .addFormDataPart("name",extras.getString("product_name"))
                                        .build();
                                request = new Request.Builder()
                                        .url("http://103.92.29.90:4000/api/v1/product")
                                        .method("POST", body)
                                        .build();

                                try {
                                    response = client.newCall(request).execute();

                                    if (response.code() == 200){

                                        jsonData = response.body().string();
                                        Jobject = new JSONObject(jsonData);

                                        JSONObject finalJobject = Jobject.getJSONObject("product");
                                        runOnUiThread(new Runnable() {

                                            @Override
                                            public void run() {

                                                // Stuff that updates the UI
                                                try {
                                                    timeTitle.setText("Estimated Drying Time:");
                                                    time.setText(finalJobject.getString("product_time") + " hours");
                                                    content.setText(finalJobject.getString("product_name"));
                                                    productId.setText(finalJobject.getString("product_id"));

                                                    Picasso.with(ctx)
                                                            .load("http://103.92.29.90:4000/static/" + finalJobject.getString("product_id") + ".png")
                                                            .into(image);

                                                    temp.setText(finalJobject.getString("product_temp"));
                                                    mois.setText(finalJobject.getString("product_mois"));
                                                    fan.setText(finalJobject.getString("product_fan"));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        });

                                    }

                                } catch (IOException | JSONException e) {
                                    Log.e("error", e.getMessage());
                                    e.printStackTrace();
                                }

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