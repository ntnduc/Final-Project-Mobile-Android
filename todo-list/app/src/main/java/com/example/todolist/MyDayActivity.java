package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MyDayActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private ArrayList<DataTask> dataTasks = new ArrayList<DataTask>();
    final LoadingDialog loadingDialog = new LoadingDialog(MyDayActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_day);
        recyclerView = findViewById(R.id.rc_myday);
        TaskMydayAdapter adapter = new TaskMydayAdapter(dataTasks);

       //loading data
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
//                    Log.d("data array", data.toString());
                    for (int i = 0; i<=data.length();i++){
                        JSONObject jsonObject = data.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String value = jsonObject.getString("value");
                        Boolean check = Boolean.parseBoolean(jsonObject.getString("check"));
                        Boolean mark = Boolean.parseBoolean(jsonObject.getString("mark"));
//                        String check = jsonObject.getString("check");
//                        String mark = jsonObject.getString("mark");
                        DataTask dataItem = new DataTask(id, value, check, mark);
                        dataTasks.add(dataItem);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("error convert", e.getMessage().toString());
                }
            }
        });

        //set list view

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

}