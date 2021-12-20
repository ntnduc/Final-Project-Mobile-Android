package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
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
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MyDayActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button btnAddNew, btnSave;
    RadioButton radioMark;
    EditText edText;
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
                popupAddNew(view);
            }
        });
        

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


    public void popupAddNew(View view){

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.form_popup, null);

        btnSave = popupView.findViewById(R.id.btn_save);
        edText = popupView.findViewById(R.id.ed_work);
        radioMark = popupView.findViewById(R.id.radio_done);

//        radioMark.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!radioMark.isSelected()){
//                    radioMark.setChecked(true);
//                    radioMark.setSelected(true);
//                }else{
//                    radioMark.setChecked(false);
//                    radioMark.setSelected(false);
//                }
//            }
//        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edText.getText().toString();
                String id = String.valueOf(new Random().nextInt(10000));
                mark = radioMark.isChecked();
                //add new data
//                dataTasks.add(new DataTask("8", "test", false, true));
//                adapter.notifyDataSetChanged();
            }
        });

        //create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        //create the popup window
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView,width, height, focusable);

        // show the popup window
        // which view you pass
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);

        //dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                popupWindow.dismiss();
                return true;
            }
        });
    }

}