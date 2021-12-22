package com.example.todolist;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskMydayAdapter extends RecyclerView.Adapter<TaskMydayAdapter.ViewHolder> {
    private ArrayList<DataTask> dataTasks;

    public TaskMydayAdapter(ArrayList<DataTask> dataTask){
        this.dataTasks = dataTask;
    }

    public void setDataTasks(ArrayList<DataTask> dataTasks) {
        this.dataTasks = dataTasks;
    }

    public ArrayList<DataTask> getDataTasks() {
        return dataTasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewListItem = inflater.inflate(R.layout.item_task, parent, false);
        return new ViewHolder(viewListItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataTask objectDataTask = dataTasks.get(position);
        String nameTask = objectDataTask.getValue();
        boolean isDone = objectDataTask.getCheck();
        boolean isMark = objectDataTask.getMark();

        holder.tvName.setText(nameTask);
        holder.radioDone.setChecked(isDone);
        if(isMark == true) {
            holder.btStar.setVisibility(View.GONE);
            holder.btStarMark.setVisibility(View.VISIBLE);
        }else{
            holder.btStarMark.setVisibility(View.GONE);
            holder.btStar.setVisibility(View.VISIBLE);
        }

        holder.btStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataTasks.get(position).setMark(true);
                holder.btStar.setVisibility(View.GONE);
                holder.btStarMark.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View listItemView;
        public RadioButton radioDone;
        public TextView tvName;
        public Button btStar, btStarMark;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listItemView = itemView;
            radioDone = listItemView.findViewById(R.id.radio_done);
            tvName = listItemView.findViewById(R.id.tv_task);
            btStar = listItemView.findViewById(R.id.bt_star);
            btStarMark = listItemView.findViewById(R.id.bt_star_mark);

            radioDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(!radioDone.isSelected()){
                       radioDone.setChecked(true);
                       radioDone.setSelected(true);
                   }else{
                       radioDone.setChecked(false);
                       radioDone.setSelected(false);
                   }
                }
            });

            btStarMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btStarMark.setVisibility(View.GONE);
                    btStar.setVisibility(View.VISIBLE);
                }
            });
        }
    }

}
