package com.ngadt.demo4.fragments;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ngadt.demo4.AddActivity;
import com.ngadt.demo4.AlarmReceiver;
import com.ngadt.demo4.R;
import com.ngadt.demo4.SQLiteHelper;
import com.ngadt.demo4.UpdateActivity;
import com.ngadt.demo4.adapter.TimeAdapter;
import com.ngadt.demo4.databinding.FragmentBaothucBinding;
import com.ngadt.demo4.model.Time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BaothucFragments extends Fragment {
    FragmentBaothucBinding binding;
    List<Time> listBaoThuc;
    int LAUNCH_SECOND_ACTIVITY = 1;
    SQLiteHelper db;
    TimeAdapter timeAdapter;
    Time time;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    private int ALARM_SERVICE = 2;
    private int cancel;
    private int UPDATE_ACTIVITY = 3;
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBaothucBinding.inflate(inflater, container, false);
        listBaoThuc = new ArrayList<>();
        db = new SQLiteHelper(getContext());
        listBaoThuc = db.readAll();
        timeAdapter = new TimeAdapter(listBaoThuc);
        time = new Time();
        binding.timeContainer.setAdapter(timeAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.timeContainer.setLayoutManager(llm);

        timeAdapter.notifyDataSetChanged();
        //Chinh sua
        timeAdapter.setOnItemClockClickListener(new TimeAdapter.OnItemClockClickListener() {
            @Override
            public void OnItemClick(int pos) {
                Intent addIntent = new Intent(getContext(), UpdateActivity.class);
                addIntent.putExtra("updateData", listBaoThuc.get(pos));
                startActivityForResult(addIntent, UPDATE_ACTIVITY);
                position = pos;
                Log.d("test", "pos: " + position);
            }
        });
        //Xoa
        timeAdapter.setOnItemLongClockClickListener(new TimeAdapter.OnItemLongClockClickListener() {
            @Override
            public void OnItemLongClick(int pos) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Xóa").setMessage("Xóa báo thức")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //cap nhat view
                                int vitri = listBaoThuc.get(pos).getId();
                                listBaoThuc.remove(pos);
                                Log.d("test", "sizeDeelte " + listBaoThuc.size());
                                timeAdapter.notifyDataSetChanged();
                                //db
                                db.deleteCourse(String.valueOf(vitri));
                            }
                        }).setIcon(R.drawable.ic_delete)
                        .setNegativeButton("Hủy", null).show();

            }
        });
        //Bật báo thức
        timeAdapter.setOnItemSwitchListener(new TimeAdapter.OnItemSwitchListener() {
            @Override
            public void OnItemSwitch(int pos, boolean b) {
                Time times = listBaoThuc.get(pos);
                Intent intent = new Intent(getContext(), AlarmReceiver.class);
                Bundle bundle = new Bundle();

                // we call broadcast using pendingIntent
                pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);

                if (b == true) {
                    //set giao diện
                    listBaoThuc.get(pos).setChecked(1);

                    //    timeAdapter.notifyDataSetChanged();
                    Log.d("click", "OnItemSwitch: " + times.toString());

                    times.setChecked(1);
                    //put
                    bundle.putSerializable("AlarmObject", times);
                    intent.putExtra("AlarmBundle", bundle);
                    Log.d("click", "timeFrag: " + times.toString());
                    //db
                    db.updateTime(times);
                    Log.d("click", "true ");
                    alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                    Calendar calendar = Calendar.getInstance();

                    // calendar is called to get current time in hour and minute
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times.getHour()));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(times.getMinutes()));
                    Log.d("click", "OnItemSwitch: " + times.getHour() + " " + times.getMinutes());
                    // using intent i have class AlarmReceiver class which inherits
                    // BroadcastReceiver

                    long timeCalender = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                    if (System.currentTimeMillis() > timeCalender) {
                        // setting time as AM and PM
                        if (calendar.AM_PM == 0)
                            timeCalender = timeCalender + (1000 * 60 * 60 * 12);
                        else
                            timeCalender = timeCalender + (1000 * 60 * 60 * 24);
                    }
                    Log.d("click time", "OnItemSwitch: " + timeCalender);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeCalender, 10000, pendingIntent);

                } else {
                    alarmManager.cancel(pendingIntent);
                    //set giao diện
                    listBaoThuc.get(pos).setChecked(0);
                    timeAdapter.notifyDataSetChanged();
                    Log.d("click", "OnItemSwitch: " + times.toString());
                    //db
                    times.setChecked(0);
                    db.updateTime(times);

                    Log.d("click", "false ");
                }
            }
        });
        //Them
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(getContext(), AddActivity.class);
                startActivityForResult(addIntent, LAUNCH_SECOND_ACTIVITY);

            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                time = (Time) data.getExtras().getSerializable("time");
                Log.d("click", "Thành công" + time.toString());
                insertData();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("test", "Hủy");
            }
        }

        if (requestCode == UPDATE_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                Time timex = (Time) data.getExtras().getSerializable("update");
                Log.d("test", "Update Thành công" + timex.toString());
                listBaoThuc.get(position).setHour(timex.getHour());
                listBaoThuc.get(position).setMinutes(timex.getMinutes());
                listBaoThuc.get(position).setName(timex.getName());
                listBaoThuc.get(position).setChecked(1);
                Log.d("test", "cap nhat view" + listBaoThuc.get(position).toString());
                timeAdapter.notifyDataSetChanged();
                //cap nhap co so du lieu
                timex.setChecked(1);
                db.updateTime(timex);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("test", "Hủy");
            }
        }
    }

    private void insertData() {
        // if (!time.getHour().equals("") && !time.getMinutes().equals("")) {
        Log.d("test", "onCreateView: Khac null");
        listBaoThuc.add(time);
        Log.d("test", "size " + listBaoThuc.size());
        //cap nhat view
        timeAdapter.notifyDataSetChanged();
        //ghi xuong co so xuong lieu
        Time time_late = new Time(time.getHour(), time.getMinutes(), time.getName());
        Log.d("test", "onCreateView: " + time_late.toString());
        boolean insert = db.addNewTime(time_late);
        if (insert) {
            Log.d("test", "insertData: " + insert);
        } else {
            Log.d("test", "insertData: " + insert);
        }
        //   }

    }

    private void initData() {
        insertData();
    }
}
