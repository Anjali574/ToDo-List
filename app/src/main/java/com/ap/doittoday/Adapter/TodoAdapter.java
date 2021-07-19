package com.ap.doittoday.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ap.doittoday.AddNewTask;
import com.ap.doittoday.MainActivity;
import com.ap.doittoday.Model.TodoModel;
import com.ap.doittoday.R;
import com.ap.doittoday.Utils.DatabaseHandler;

import java.util.List;

//The adapter is the piece that will connect our data to our RecyclerView
// and determine the ViewHolder(s) which will need to be used to display that data.
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoModel> todoList;
    private DatabaseHandler db;
    private MainActivity activity;

    public TodoAdapter(DatabaseHandler db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }
    //basic set of method overrides that will need to be added to your adapter for it to work. These required methods are as follows:

    //onCreateViewHolder(ViewGroup parent, int viewType)- Used to initialize your ViewHolder(s).
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }
    //onBindViewHolder(RecyclerView.ViewHolder holder, int position)-This method is called for each ViewHolder to bind it to the adapter.
    // This is where we will pass our data to our ViewHolder.
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        db.openDatabase();
        final TodoModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatus(item.getId(), 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    //getItemCount()-This method returns the size of the collection that contains the items we want to display
    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public Context getContext() {
        return activity;
    }

    public void setTasks(List<TodoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        TodoModel item = todoList.get(position);
        db.deleteTask(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        TodoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todocheckbox);
        }
    }
}
