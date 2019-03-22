package com.benboer.boluo.boluomessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.benboer.boluo.common.app.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected int getContentLayoutId() {
        return 0;
    }
}
