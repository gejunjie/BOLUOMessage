package com.benboer.boluo.boluomessage.activity;

import android.content.Context;
import android.content.Intent;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.common.app.ToolbarActivity;


/**
 * Created by BenBoerBoluojiushiwo on 2019/5/16.
 */
public class SearchActivity extends ToolbarActivity {

    private static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final int TYPE_USER = 1; // 搜索人
    public static final int TYPE_GROUP = 2; // 搜索群

    public static void show(Context context, int type) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_search;
    }

    /**
     * 搜索的Fragment必须继承的接口
     */
    public interface SearchFragment {
        void search(String content);
    }
}
