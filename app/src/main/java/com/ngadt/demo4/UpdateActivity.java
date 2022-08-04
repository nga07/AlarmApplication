package com.ngadt.demo4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ngadt.demo4.databinding.ActivityAddBaothucBinding;
import com.ngadt.demo4.model.Time;

import java.io.Serializable;

public class UpdateActivity extends AppCompatActivity {
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
        //lay du lieu gui sang
        time = (Time) getIntent().getExtras().getSerializable("updateData");
        Log.d("test", "HOUR: " + time.getId());
        binding.timePicker.setCurrentHour(Integer.parseInt(time.getHour()));
        binding.timePicker.setCurrentMinute(Integer.parseInt(time.getMinutes()));
        binding.edtName.setText(time.getName());
        binding.btnSave.setOnClickListener(view -> {
            hour = String.valueOf(binding.timePicker.getCurrentHour());
            minutes = String.valueOf(binding.timePicker.getCurrentMinute());
            Log.d("test", "onUpdate: " + hour + ":" + minutes);
            name = binding.edtName.getText().toString();
            time.setHour(hour);
            time.setMinutes(minutes);
            time.setName(name);
            Log.d("test", "return: "+time.toString());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("update", time);
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
