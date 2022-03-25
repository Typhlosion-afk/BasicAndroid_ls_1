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
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;

    private boolean isChecked = false;

    private String lCode = "en";

    private TextView mTxtNum;

    private Button mBtnToast;

    private Button mBtnCount;

    private Button mBtnStartAct;

    private CheckBox mCxbClickMe;

    private Button mBtnLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLocale(getLocaleCode());

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
        mBtnLanguage = this.findViewById(R.id.btn_language);
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

        mBtnLanguage.setOnClickListener(v -> {
            if(lCode.equals("en")){
                lCode = "vi";
            }else {
                lCode = "en";
            }
            saveLocaleCode(lCode);

            restartApp();
        });

    }

    private void restartApp(){
        PackageManager packageManager = this.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(this.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        this.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }

    private void setLocale(String languageCode){
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    private String getStrNum() {
        return "" + mCount;
    }

    private String getLocaleCode(){
        SharedPreferences shared = this.getPreferences(Context.MODE_PRIVATE);
        lCode = shared.getString(getString(R.string.language_pre_key), "en");

        Log.d("TAG", "getLocaleCode: " + lCode);
        return lCode;
    }

    @SuppressLint("ApplySharedPref")
    private void saveLocaleCode(String code){
        SharedPreferences shared = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(getString(R.string.language_pre_key), code);
        editor.commit();
        Log.d("TAG", "saveLocaleCode: saved " + lCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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