package com.benboer.boluo.boluomessage.activity;

import android.widget.TextView;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.common.app.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.txt_test)
    TextView mTextView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTextView.setText("test");
    }
}
