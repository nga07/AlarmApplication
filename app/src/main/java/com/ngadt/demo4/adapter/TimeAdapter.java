package com.ngadt.demo4.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ngadt.demo4.R;
import com.ngadt.demo4.model.Time;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {
    private List<Time> clocks;
    private TextView txt_time;
    private TextView txt_name;
    public SwitchCompat swith;
    private OnItemClockClickListener onItemClockClickListener;
    private OnItemLongClockClickListener onItemLongClockClickListener;
    private OnItemSwitchListener onItemSwitchListener;
    private boolean checked = false;

    public void setOnItemLongClockClickListener(OnItemLongClockClickListener onItemLongClockClickListener) {
        this.onItemLongClockClickListener = onItemLongClockClickListener;
    }

    public void setOnItemSwitchListener(OnItemSwitchListener onItemSwitchListener) {
        this.onItemSwitchListener = onItemSwitchListener;
    }

    public void setOnItemClockClickListener(OnItemClockClickListener onItemClockClickListener) {
        this.onItemClockClickListener = onItemClockClickListener;
    }

    // Pass in the contact array into the constructor
    public TimeAdapter(List<Time> mClocks) {
        clocks = mClocks;
    }

    //tao, tai su dung

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View timeView = inflater.inflate(R.layout.time_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(timeView);
        return viewHolder;
    }

    //du lieu
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Time clock = clocks.get(position);
        String str_hour = Integer.parseInt(clock.getHour()) >= 10 ? clock.getHour() : "0" + clock.getHour();
        String str_minute = Integer.parseInt(clock.getMinutes()) >= 10 ? clock.getMinutes() : "0" + clock.getMinutes();
        holder.txt_time.setText(str_hour + ":" + str_minute);
        holder.txt_name.setText(clock.getName());
        swith = holder.aSwitch;
        Log.d("test", "onBindViewHolder: " + clock.getChecked());
        if (clock.getChecked() == 1) {
            holder.aSwitch.setChecked(true);
        } else {
            holder.aSwitch.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return clocks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView txt_time;
        public TextView txt_name;
        public SwitchCompat aSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_name = itemView.findViewById(R.id.txt_name);
            aSwitch = itemView.findViewById(R.id.swith);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (onItemSwitchListener != null) {
                        onItemSwitchListener.OnItemSwitch(getAdapterPosition(), b);
                    }

                }
            });
        }

        @Override
        public void onClick(View view) {
            if (onItemClockClickListener != null) {
                onItemClockClickListener.OnItemClick(getAdapterPosition());
            }
        }


        @Override
        public boolean onLongClick(View view) {
            if (onItemLongClockClickListener != null) {
                onItemLongClockClickListener.OnItemLongClick(getAdapterPosition());
            }
            return false;
        }
    }

    public interface OnItemSwitchListener {
        public void OnItemSwitch(int pos, boolean b);
    }

    public interface OnItemClockClickListener {
        public void OnItemClick(int pos);
    }

    public interface OnItemLongClockClickListener {
        public void OnItemLongClick(int pos);
    }
}
