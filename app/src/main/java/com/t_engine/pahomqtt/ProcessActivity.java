package com.t_engine.pahomqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProcessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        Button previousButton = findViewById(R.id.previousButton);
        Button newButton = findViewById(R.id.newButton);
        TextView logoutLink = findViewById(R.id.logoutLink);

        logoutLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductActivity.class);
                String type = "previous";
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductActivity.class);
                String type = "product";
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });

    }
}