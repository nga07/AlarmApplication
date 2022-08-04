package com.ngadt.demo4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ngadt.demo4.databinding.ActivityAddBaothucBinding;
import com.ngadt.demo4.model.Time;

public class AddActivity extends AppCompatActivity {
    ActivityAddBaothucBinding binding;
    private String hour;
    private String minutes;
    private String name;
    private Time time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = ActivityAddBaothucBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.btnSave.setOnClickListener(view -> {
            hour = String.valueOf(binding.timePicker.getCurrentHour());
            minutes = String.valueOf(binding.timePicker.getCurrentMinute());
            Log.d("test", "onCreate: " + hour + ":" + minutes);
            name = binding.edtName.getText().toString();
            if (name.equals("")) {
                time = new Time(hour, minutes,1);
            } else {
                time = new Time(hour, minutes, name,1);
            }
            Intent returnIntent = new Intent();
            returnIntent.putExtra("time", time);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });
        binding.btnExit.setOnClickListener(view -> {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        });
    }
}
