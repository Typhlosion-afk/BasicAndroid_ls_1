package com.dore.dodh2_ls_1.ui_control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dore.dodh2_ls_1.R;

public class MainActivity3 extends AppCompatActivity {

    private ImageView img;

    private TextView txtView;

    private SwitchCompat mSwitch;

    private SeekBar seekBar;

    private TextView textView;

    private Button btnClickMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        initView();
        handleSwitch();
        handleSeekbar();
        handleClickMe();

    }

    private void initView(){
        mSwitch = this.findViewById(R.id.my_switch);
        img = this.findViewById(R.id.image_view);
        seekBar = this.findViewById(R.id.seek_bar);
        textView = this.findViewById(R.id.text_percent);
        btnClickMe = this.findViewById(R.id.btn_click);

        setTextPercent(seekBar.getProgress());
        setWifiState(mSwitch.isChecked());
    }

    private void handleSeekbar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setTextPercent(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void handleClickMe(){
        btnClickMe.setOnClickListener(v -> {
            Log.d("TAG", "Clicked");
            Toast.makeText(MainActivity3.this, "Clicked", Toast.LENGTH_SHORT).show();
        });

        btnClickMe.setOnLongClickListener(v -> {
            Log.d("TAG", "Long Clicked");
            Toast.makeText(MainActivity3.this, "Long Clicked", Toast.LENGTH_SHORT).show();
            return true;
        });

    }

    private void setTextPercent(int per){
        String s = "Seek bar " + per + "%";
        textView.setText(s);
    }

    private void handleSwitch(){
        mSwitch.setOnClickListener(v -> {
            setWifiState(mSwitch.isChecked());
        });
    }

    private void setWifiState(boolean isWifiOn){
        if (isWifiOn) {
            img.setImageResource(R.drawable.ic_baseline_wifi_24);
        } else {
            img.setImageResource(R.drawable.ic_baseline_wifi_off_24);
        }
    }



}
