package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        //Loading
        final LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);
        btnMyTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Onclick my task", Toast.LENGTH_SHORT).show();
                loadingDialog.startLoading();
            }
        });

        btnMyDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getData();
                Intent intent = new Intent(MainActivity.this, MyDayActivity.class);
                startActivity(intent);
            }
        });

    }

    //getData
    private void getData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://127.0.0.1:5000/todo").build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("onFailture", e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseString = response.toString();
                Log.d("Response", responseString);
            }
        });
    }
}