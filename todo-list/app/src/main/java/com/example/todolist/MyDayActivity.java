package com.example.todolist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class MyDayActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private EditText nameNewTask;
    private Button btnSave, btnCancle;

    RecyclerView recyclerView;
    Button btnAddNew;
    RadioButton radioMark;
    String name;
    String id;
    Boolean mark;

    private ArrayList<DataTask> dataTasks = new ArrayList<DataTask>();
    final LoadingDialog loadingDialog = new LoadingDialog(MyDayActivity.this);
    TaskMydayAdapter adapter = new TaskMydayAdapter(dataTasks);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_day);
        recyclerView = findViewById(R.id.rc_myday);
        btnAddNew = findViewById(R.id.btn_add_new);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupAddNew();
            }
        });

        //get data from backend
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
                    for (int i = 0; i<=data.length();i++){
                        JSONObject jsonObject = data.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String value = jsonObject.getString("value");
                        Boolean check = Boolean.parseBoolean(jsonObject.getString("check"));
                        Boolean mark = Boolean.parseBoolean(jsonObject.getString("mark"));
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


    //show dialog
    //dialog show create
    public void popupAddNew(){
        builder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.form_popup, null);

        //set button ui
        nameNewTask = contactPopupView.findViewById(R.id.ed_work);
        radioMark = contactPopupView.findViewById(R.id.r_mark);
        btnSave = contactPopupView.findViewById(R.id.btn_save);

        //create popup
        builder.setView(contactPopupView);
        dialog = builder.create();
        dialog.show();

        ////////////////
        radioMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!radioMark.isSelected()){
                    radioMark.setChecked(true);
                    radioMark.setSelected(true);
                }else{
                    radioMark.setChecked(false);
                    radioMark.setSelected(false);
                }
            }
        });
        
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Boolean check;
                name = nameNewTask.getText().toString();
                check = name.matches("")?true:false;
                if (check == true) {
                    Toast.makeText(MyDayActivity.this, "Bạn cần nhập tên công việc", Toast.LENGTH_SHORT).show();
                    return;
                }
                String id = String.valueOf(new Random().nextInt(10000));
                mark = radioMark.isChecked();
                //add new data
                dataTasks.add(new DataTask(id, name, false, mark));
                postData(new DataTask(id, name, false, mark));
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    //post data
    private void postData(DataTask dataTask) {
        String url = "http://192.168.2.116:5000/todo";

        JSONObject postData = new JSONObject();

        try {
            postData.put("value", dataTask.getValue());
            postData.put("check", dataTask.getCheck());
            postData.put("mark", dataTask.getMark());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, postData, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("oke", error.toString());
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

}