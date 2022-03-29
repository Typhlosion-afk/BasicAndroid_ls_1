package com.dore.dodh2_ls_1.ex1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.dore.dodh2_ls_1.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;

    private boolean isChecked = false;

    private String lCode = "en";

    private String strSettingTheme = "purple";

    private TextView mTxtNum;

    private Button mBtnToast;

    private Button mBtnCount;

    private Button mBtnStartAct;

    private CheckBox mCxbClickMe;

    private Button mBtnLanguage;

    private Button mBtnTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        setAppTheme();
        setLocale(getLocaleCode());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setData();
        handleAction();
    }

    private void setAppTheme() {
        if(getStrSettingTheme().equals("purple")){
            setTheme(R.style.ThemePurple);
        }else{
            setTheme(R.style.ThemeGreen);
        }
    }

    private void initView() {
        mTxtNum = this.findViewById(R.id.txt_num);
        mBtnCount = this.findViewById(R.id.btn_count);
        mBtnToast = this.findViewById(R.id.btn_toast);
        mCxbClickMe = this.findViewById(R.id.cbx_click_me);
        mBtnStartAct = this.findViewById(R.id.btn_start_act);
        mBtnLanguage = this.findViewById(R.id.btn_language);
        mBtnTheme = this.findViewById(R.id.btn_theme);
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

        mBtnTheme.setOnClickListener(v -> {
            if(strSettingTheme.equals("purple")){
                strSettingTheme = "green";
            }else {
                strSettingTheme = "purple";
            }

            saveThemeCode(strSettingTheme);

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

        return lCode;
    }

    private String getStrSettingTheme(){
        SharedPreferences shared = this.getPreferences(Context.MODE_PRIVATE);
        strSettingTheme = shared.getString(getString(R.string.theme_pre_key), "purple");

        return strSettingTheme;

    }

    @SuppressLint("ApplySharedPref")
    private void saveLocaleCode(String code){
        SharedPreferences shared = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(getString(R.string.language_pre_key), code);
        editor.commit();
        Log.d("TAG", "saveLocaleCode: saved " + lCode);
    }

    @SuppressLint("ApplySharedPref")
    private void saveThemeCode(String theme){
        SharedPreferences shared = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(getString(R.string.theme_pre_key), theme);
        editor.commit();
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