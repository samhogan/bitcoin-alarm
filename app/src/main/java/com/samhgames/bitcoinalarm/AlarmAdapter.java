package com.samhgames.bitcoinalarm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by samho on 1/2/2018.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>
{

    private java.util.List<Alarm> alarmList;
    private Context context;

    public AlarmAdapter(/*List<Alarm> _alarmList,*/ Context _context)
    {
        //alarmList = _alarmList;
        context = _context;
    }

    @Override
    public int getItemCount()
    {
        return 12;//alarmList.size();
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
        holder.timeTextView.setText("3:35 am");
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        //the text view that holds the time
        protected TextView timeTextView;

        public AlarmViewHolder(View itemView)
        {
            super(itemView);
            timeTextView = (TextView)itemView.findViewById(R.id.tv_time);
           // timeTextView.setText("testyo");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            Toast.makeText(context, "Why won't this work?", Toast.LENGTH_LONG).show();
            //Log.d("yo", "I have no idea");
            Intent intent = new Intent(context, SettingsActivity.class);
            context.startActivity(intent);
        }

        /*void bind(int listIndex)
        {
            ListItemNumberView.setText(listIndex + "");
        }*/

    }
}
