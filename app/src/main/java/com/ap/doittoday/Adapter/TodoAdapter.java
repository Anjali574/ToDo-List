package com.ap.doittoday.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.ap.doittoday.MainActivity;
import com.ap.doittoday.Model.TodoModel;
import com.ap.doittoday.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<TodoModel> todolist;
    private MainActivity activity;

    public TodoAdapter(MainActivity activity){
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }
    //return position of the element about which we will work on
    public void onBindViewHolder(ViewHolder holder,int position){
        TodoModel item = todolist.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
    }

    public int getItemCount() {
        return todolist.size();
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    public void setTasks(List<TodoModel> todolist ){
        this.todolist = todolist;
        notifyDataSetChanged();
    }


    public  static  class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        //constructor
        ViewHolder(View view){
            super(view);
            task=view.findViewById(R.id.todocheckbox);
        }
    }
}
