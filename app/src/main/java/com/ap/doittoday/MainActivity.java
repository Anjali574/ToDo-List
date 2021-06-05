package com.ap.doittoday;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ap.doittoday.Adapter.TodoAdapter;
import com.ap.doittoday.Model.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView tasksRecView;
    private TodoAdapter taskAdapter;

    private List<TodoModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        taskList= new ArrayList<>();
        tasksRecView = findViewById(R.id.tasksRecView);
        tasksRecView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter=new TodoAdapter(this);
        tasksRecView.setAdapter(taskAdapter);

        //dummy
        TodoModel task = new TodoModel();
        task.setTask("Dummy task");
        task.setId(1);
        task.setStatus(0);

        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);

        taskAdapter.setTasks(taskList);

    }
}