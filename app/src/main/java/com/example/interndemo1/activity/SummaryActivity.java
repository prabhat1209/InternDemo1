package com.example.interndemo1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.interndemo1.R;
import com.example.interndemo1.background.FetchDataBackgroundTask;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        new FetchDataBackgroundTask(SummaryActivity.this).execute();

    }
}