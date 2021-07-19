package com.ap.doittoday;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ap.doittoday.Adapter.TodoAdapter;
import com.ap.doittoday.Model.TodoModel;
import com.ap.doittoday.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{
    private DatabaseHandler db;
    private RecyclerView tasksRecView;
    private TodoAdapter taskAdapter;
    private FloatingActionButton fab;
    private List<TodoModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        tasksRecView = findViewById(R.id.tasksRecView);
        tasksRecView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TodoAdapter(db , MainActivity.this);
        tasksRecView.setAdapter(taskAdapter);

////////////////////// SWIPE FEATURES
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(taskAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecView);


        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);
        taskAdapter.notifyDataSetChanged();
    }
}
