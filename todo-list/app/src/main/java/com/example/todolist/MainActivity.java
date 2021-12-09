package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnMyDay, btnImportant, btnMyTask, btnWork;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMyDay = findViewById(R.id.btn_myday);
        btnImportant = findViewById(R.id.btn_important);
        btnMyTask = findViewById(R.id.btn_task);
        btnWork = findViewById(R.id.btn_work);

        btnMyDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyDayActivity.class);
                startActivity(intent);
            }
        });

    }
}