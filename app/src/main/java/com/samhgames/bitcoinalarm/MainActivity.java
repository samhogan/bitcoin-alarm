package com.samhgames.bitcoinalarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity
{
    RecyclerView alarmRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintest);

        alarmRecyclerView = (RecyclerView)findViewById(R.id.rv_alarms);
        alarmRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        //llm.setOrientation(LinearLayoutManager.VERTICAL);
        alarmRecyclerView.setLayoutManager(llm);


        AlarmAdapter adapter = new AlarmAdapter();
        alarmRecyclerView.setAdapter(adapter);

    }
}
