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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button btnMyDay, btnImportant, btnMyTask, btnWork;
    ArrayList<DataTask> dataTasks;

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
                dataTasks = new ArrayList<DataTask>();
                Intent intent = new Intent(MainActivity.this, MyDayActivity.class);
                startActivity(intent);
            }
        });

    }

    //getData
    private void getData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://192.168.2.116:5000/todo").build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("onFailded", e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
               String responseString = response.body().string();
               Log.d("Response", responseString);
                try {
                    JSONArray data = new JSONArray(responseString);
                    Log.d("data array", data.toString());
                    for (int i = 0; i<=data.length();i++){
                        JSONObject jsonObject = data.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String value = jsonObject.getString("value");
                        Boolean check =Boolean.parseBoolean(jsonObject.getString("check"));
                        Boolean mark = Boolean.parseBoolean(jsonObject.getString("mark"));

                        DataTask dataItem = new DataTask(id, value, check, mark);
                        dataTasks.add(dataItem);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}