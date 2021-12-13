package com.example.todolist;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskMydayAdapter extends RecyclerView.Adapter<TaskMydayAdapter.ViewHolder> {
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
        String nameTask = "Test "+ position;
        holder.tvName.setText(nameTask);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View listItemView;
        public RadioButton radioDone;
        public TextView tvName;
        public Button btStar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listItemView = itemView;
            radioDone = listItemView.findViewById(R.id.radio_done);
            tvName = listItemView.findViewById(R.id.tv_task);
            btStar = listItemView.findViewById(R.id.bt_star);


        }
    }
}
