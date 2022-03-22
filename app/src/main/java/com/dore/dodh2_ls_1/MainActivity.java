package com.dore.dodh2_ls_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int mCount;

    private TextView mTxtNum;

    private Button mBtnToast;

    private Button mBtnCount;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCount = 0;

        initView();
        initAction();
    }

    private void initView() {
        mTxtNum = this.findViewById(R.id.txt_num);
        mBtnCount = this.findViewById(R.id.btn_count);
        mBtnToast = this.findViewById(R.id.btn_toast);
    }

    @SuppressLint("SetTextI18n")
    private void initAction() {
        mBtnToast.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Number is " + mCount, Toast.LENGTH_LONG).show();
        });

        mBtnCount.setOnClickListener(v -> {
            mCount++;
            mTxtNum.setText("" + mCount);
        });
    }
}