package com.benboer.boluo.boluomessage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.fragment.search.SearchGroupFragment;
import com.benboer.boluo.boluomessage.fragment.search.SearchUserFragment;
import com.benboer.boluo.common.app.ToolbarActivity;


/**
 * Created by BenBoerBoluojiushiwo on 2019/5/16.
 */
public class SearchActivity extends ToolbarActivity {

    private static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final int TYPE_USER = 1; // 搜索人
    public static final int TYPE_GROUP = 2; // 搜索群


    // 具体需要显示的类型
    private int type;
    private SearchCallback mSearchCallback;

    public static void show(Context context, int type) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        type = bundle.getInt(EXTRA_TYPE);
        return type == TYPE_USER || type == TYPE_GROUP;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        Fragment fragment = null;
        if (type == TYPE_USER){
            SearchUserFragment userFragment = new SearchUserFragment();
            fragment = userFragment;
            mSearchCallback = userFragment;
        }else if (type == TYPE_GROUP){
            SearchGroupFragment groupFragment = new SearchGroupFragment();
            fragment = groupFragment;
            mSearchCallback = groupFragment;
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    search("");
                    return true;
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void search(String query) {
        if (mSearchCallback == null) return;
        mSearchCallback.search(query);
    }


    /**
     * 搜索的Fragment必须继承的接口
     */
    public interface SearchCallback {
        void search(String content);
    }
}
