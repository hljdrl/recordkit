package com.record.kit;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.record.kit.builder.PermissionCompat;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import arch.record.kit.IRecordScreen;
import arch.record.kit.MediaRecorderImpl;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_mr_start;
    private Button btn_mr_stop;
    private TextView tv_mr_result;
    private IRecordScreen recordScreen;
    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        IRecordScreen.install(MediaRecorderImpl.class);
        //
        btn_mr_start = findViewById(R.id.btn_mr_start);
        btn_mr_stop = findViewById(R.id.btn_mr_stop);
        tv_mr_result = findViewById(R.id.tv_mr_result);
        chronometer = findViewById(R.id.time);
        //
        recordScreen = IRecordScreen.getInstance();
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        btn_mr_start.setOnClickListener(this);
        btn_mr_stop.setOnClickListener(this);
        if(recordScreen.isRecording()){
            btn_mr_start.setEnabled(false);
            btn_mr_stop.setEnabled(true);
            tv_mr_result.setText(recordScreen.getRecordFile());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionCompat.check(this, null);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_mr_start) {
            if (!recordScreen.isRecording()) {
                if(!recordScreen.hasRecordPermission()){
                    recordScreen.startRecordPermission(this);
                    return;
                }
                if (!recordScreen.isPrepare()) {
                    File extFile = getExternalCacheDir();
                    StringBuilder name = new StringBuilder("record_screen_");
                    name.append(System.currentTimeMillis());
                    name.append(".mp4");
                    File saveFile = new File(extFile,name.toString());
                    recordScreen.prepare(saveFile.toString());
                    tv_mr_result.setText(saveFile.toString());
                }
                recordScreen.startRecord();
                btn_mr_stop.setEnabled(true);
                btn_mr_start.setEnabled(false);
            }
        } else if (id == R.id.btn_mr_stop) {
            if (recordScreen.isRecording()) {
                recordScreen.stopRecord();
                btn_mr_stop.setEnabled(false);
                btn_mr_start.setEnabled(true);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(recordScreen!=null){
            recordScreen.onActivityResult(requestCode,resultCode,data);
            //
            if(!recordScreen.isRecording()){
                if (!recordScreen.isPrepare()) {
                    File extFile = getExternalCacheDir();
                    StringBuilder name = new StringBuilder("record_screen_");
                    name.append(System.currentTimeMillis());
                    name.append(".mp4");
                    File saveFile = new File(extFile,name.toString());
                    recordScreen.prepare(saveFile.toString());
                    tv_mr_result.setText(saveFile.toString());
                }
                recordScreen.startRecord();
                btn_mr_stop.setEnabled(true);
                btn_mr_start.setEnabled(false);
            }
        }
    }
}