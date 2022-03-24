package com.dore.dodh2_ls_1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;

    private boolean isChecked = false;

    private TextView mTxtNum;

    private Button mBtnToast;

    private Button mBtnCount;

    private Button mBtnStartAct;

    private CheckBox mCxbClickMe;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setData();
        handleAction();
    }

    private void initView() {
        mTxtNum = this.findViewById(R.id.txt_num);
        mBtnCount = this.findViewById(R.id.btn_count);
        mBtnToast = this.findViewById(R.id.btn_toast);
        mCxbClickMe = this.findViewById(R.id.cbx_click_me);
        mBtnStartAct = this.findViewById(R.id.btn_start_act);
    }

    private void setData() {
        mTxtNum.setText(getStrNum());
        mCxbClickMe.setChecked(isChecked);
    }

    private void handleAction() {
        mCxbClickMe.setOnClickListener(v -> isChecked = mCxbClickMe.isChecked());

        mBtnToast.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Number is " + mCount, Toast.LENGTH_LONG).show();
        });


        mBtnCount.setOnClickListener(v -> {
            mCount++;
            mTxtNum.setText(getStrNum());
        });

        mBtnStartAct.setOnClickListener(v -> {
            Intent startDataIntent = new Intent(MainActivity.this, DataActivity.class);
            startDataIntent.putExtra("num", mCount);
            numberResultLauncher.launch(startDataIntent);
        });

    }

    private String getStrNum() {
        return "" + mCount;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("num", mCount);
        outState.putBoolean("checked", isChecked);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isChecked = savedInstanceState.getBoolean("checked", false);
        mCount = savedInstanceState.getInt("num", 0);

        setData();
    }

    private final ActivityResultLauncher<Intent> numberResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Log.d("TAG", "res: " + result.getResultCode());

                if (result.getResultCode() == 111 && result.getData() != null) {
                    mCount = result.getData().getIntExtra("result", 0);
                    setData();
                }
            }
    );
}