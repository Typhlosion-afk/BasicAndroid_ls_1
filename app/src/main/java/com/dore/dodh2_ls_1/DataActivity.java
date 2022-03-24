package com.dore.dodh2_ls_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class DataActivity extends AppCompatActivity {

    private int mCount = 0;

    private TextView mTxtNum;

    private Button mBtnCount;

    private Button mBtnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);

        initData();
        initView();
        setData();
        handleAction();

    }

    private void initData(){
        if(getIntent() != null) {
            mCount = getIntent().getIntExtra("num", 0);
        }
    }

    private void initView() {
        mTxtNum = this.findViewById(R.id.txt_num);
        mBtnCount = this.findViewById(R.id.btn_count);
        mBtnClose = this.findViewById(R.id.btn_close);
    }

    private void setData() {
        mTxtNum.setText(getStrNum());
    }

    private String getStrNum() {
        return "" + mCount;
    }

    private void handleAction() {

        mBtnCount.setOnClickListener(v -> {
            mCount++;
            mTxtNum.setText(getStrNum());
        });

        mBtnClose.setOnClickListener(v -> {
            Log.d("TAG", "onStop: stop");
            Intent i = new Intent();
            i.putExtra("result", mCount);
            setResult(111, i);
            DataActivity.super.onBackPressed();
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("num", mCount);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCount = savedInstanceState.getInt("num", 0);

        setData();
    }
}