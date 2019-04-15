package com.jzx.personal;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jzx.personal.utils.RegUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,String.valueOf(RegUtils.isChnName("毛泽东")));

    }
}
