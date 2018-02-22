package com.samhgames.bitcoinalarm;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.samhgames.bitcoinalarm.data.AlarmInfo;
import com.samhgames.bitcoinalarm.data.DataContract;
import com.samhgames.bitcoinalarm.data.DbAccessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samho on 1/2/2018.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>
{

    private Context context;
    private ArrayList<AlarmInfo> alarmList;
    private DbAccessor db;

    public AlarmAdapter(Context _context, DbAccessor _db)
    {
        context = _context;
        db = _db;
    }

    //if a new alarm is added or something is changed, refresh the data
    public void setData(ArrayList<AlarmInfo> alarms)
    {
        alarmList = alarms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return alarmList.size();
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.alarm_list_item, parent, false);

        return new AlarmViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position)
    {
        //move the cursor the the next position
        //if(!cursor.moveToPosition(position))
        //    return;

        AlarmInfo info = alarmList.get(position);

        //time in minutes from midnight
        int minutes = info.getTime();

        //convert to a readable string
        String timeString = TimeConverter.getTimeText(minutes);


        holder.setInfo(info);

        holder.timeTextView.setText(timeString);
        holder.enabledSwitch.setChecked(info.isEnabled());
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        //the text view that holds the time
        protected TextView timeTextView;
        protected Switch enabledSwitch;

        private AlarmInfo info;


        public AlarmViewHolder(View itemView)
        {
            super(itemView);
            timeTextView = (TextView)itemView.findViewById(R.id.tv_time);
            enabledSwitch = (Switch)itemView.findViewById(R.id.enabledSwitch);

            enabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(info.isEnabled()!=isChecked)//so this is not called when set initially
                    {
                        info.setEnabled(isChecked);

                        //cancel or set pendingintent in alarmmanager
                        if(isChecked)
                            AlarmSetter.setAlarm(info, context);
                        else
                            AlarmSetter.cancelAlarm(info, context);


                        db.saveAlarm(info, false);//save to database

                    }
                }
            });

           // timeTextView.setText("testyo");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
           // Toast.makeText(context, "Why won't this work?", Toast.LENGTH_LONG).show();
            //Log.d("yo", "I have no idea");
            Intent intent = new Intent(context, AlarmSettingsActivity.class);
            intent.putExtra("ID", info.getId());


            //((AppCompatActivity)context).startActivityForResult(intent, 0);
            context.startActivity(intent);
        }

        public void setInfo(AlarmInfo info)
        {
            this.info = info;
        }


        /*void bind(int listIndex)
        {
            ListItemNumberView.setText(listIndex + "");
        }*/

    }
}
