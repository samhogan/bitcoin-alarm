package com.samhgames.bitcoinalarm;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.samhgames.bitcoinalarm.data.DataContract;
import com.samhgames.bitcoinalarm.data.DbAccessor;
import com.samhgames.bitcoinalarm.data.DbHelper;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    RecyclerView alarmRecyclerView;
    FloatingActionButton fab;

    //the database containing a table of all alarms
    private DbAccessor db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintest);

        //database accesor
        db = new DbAccessor(this);

        //set the alarmmanager
        AlarmSetter.setManager(this);
        AlarmSetter.setManager(this);

        //Cursor cursor = getAllAlarms();

        //set up the recyclerview
        alarmRecyclerView = (RecyclerView)findViewById(R.id.rv_alarms);
        alarmRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        //llm.setOrientation(LinearLayoutManager.VERTICAL);
        alarmRecyclerView.setLayoutManager(llm);


        AlarmAdapter adapter = new AlarmAdapter(this, db);
        alarmRecyclerView.setAdapter(adapter);

        //the floating action button
        fab = (FloatingActionButton)findViewById(R.id.fab);


        //show and hide fab
        alarmRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });

        final Context mainContext = this;
        //fab click
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                Intent intent = new Intent(mainContext, AlarmSettingsActivity.class);
                startActivity(intent);
            }
        });


       // addNewAlarm(new Random().nextInt(300));


    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //load the data each time this activity is returned to
        ((AlarmAdapter)alarmRecyclerView.getAdapter()).setData(db.getAllAlarms());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            //intent.putExtra("DB", mDb);

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
