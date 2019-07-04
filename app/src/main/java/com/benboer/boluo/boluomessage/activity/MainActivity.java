package com.benboer.boluo.boluomessage.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.fragment.main.ActiveFragment;
import com.benboer.boluo.boluomessage.fragment.main.ContactFragment;
import com.benboer.boluo.boluomessage.fragment.main.GroupFragment;
import com.benboer.boluo.boluomessage.fragment.main.HomeFragment;
import com.benboer.boluo.boluomessage.tool.NavHelper;
import com.benboer.boluo.common.app.BaseActivity;
import com.benboer.boluo.widget.PortraitView;
import com.benboer.boluo.factory.persistence.Account;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnTabChangedListener<Integer> {

    @BindView(R.id.appbar)
    AppBarLayout mLayAppbar;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R.id.txt_title)
    TextView mTitle;

    @BindView(R.id.lay_container)
    FrameLayout mContainer;

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    @BindView(R.id.btn_action)
    FloatActionButton mAction;

    private NavHelper<Integer> mNavHelper;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();
        //手动的触发第一次点击
        Menu menu = mNavigation.getMenu();
        menu.performIdentifierAction(R.id.action_active, 0);
        mPortrait.setup(Glide.with(this), Account.getUser());
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        if (Account.isComplete()){
            return super.initArgs(bundle);
        }else{
//            UserActivity.show(this);
            return false;
        }

    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mNavHelper = new NavHelper<>(this, R.id.lay_container,
                getSupportFragmentManager(), this);

//        mNavHelper.addTab(R.id.action_active, new NavHelper.Tab<>(ActiveFragment.class, R.string.title_home))
//                .addTab(R.id.action_group, new NavHelper.Tab<>(GroupFragment.class, R.string.title_group))
//                .addTab(R.id.action_contact, new NavHelper.Tab<>(ContactFragment.class, R.string.title_contact))
//                .addTab(R.id.action_home, new NavHelper.Tab<>(HomeFragment.class, R.string.title_contact));


        mNavigation.setOnNavigationItemSelectedListener(this);

        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new CustomViewTarget<AppBarLayout, Drawable>(mLayAppbar) {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        this.view.setBackground(resource.getCurrent());
                    }

                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    @OnClick(R.id.im_search)
    void onSearchMenuClick(){
        int type = Objects.equals(mNavHelper.getCurrentTab().extra, R.string.title_group) ?
                SearchActivity.TYPE_GROUP : SearchActivity.TYPE_USER;
//        SearchActivity.show(this, type);
    }

    @OnClick(R.id.btn_action)
    void onActionClick(){
        if (Objects.equals(mNavHelper.getCurrentTab().extra, R.string.title_group)) {
//            GroupCreateActivity.show(this);
        } else {
            // 如果是其他，都打开添加用户的界面
//            SearchActivity.show(this, SearchActivity.TYPE_USER);
        }
    }


    @OnClick(R.id.im_portrait)
    void onPortraitClick() {
//        PersonalActivity.show(this, Account.getUserId());
    }

    @Override
    public void onTabChanged(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {
        mTitle.setText(newTab.extra);

        // 对浮动按钮进行隐藏与显示的动画
        float transY = 0;
        float rotation = 0;
        if (Objects.equals(newTab.extra, R.string.title_home)) {
            // 主界面时隐藏
            transY = Ui.dipToPx(getResources(), 76);
        } else {
            // transY 默认为0 则显示
            if (Objects.equals(newTab.extra, R.string.title_group)) {
                // 群
                mAction.setImageResource(R.drawable.ic_group_add);
                rotation = -360;
            } else {
                // 联系人
                mAction.setImageResource(R.drawable.ic_contact_add);
                rotation = 360;
            }
        }

        // 开始动画
        // 旋转，Y轴位移，弹性差值器，时间
        mAction.animate()
                .rotation(rotation)
                .translationY(transY)
                .setInterpolator(new AnticipateOvershootInterpolator(1))
                .setDuration(480)
                .start();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.e("NavigationItemSelected", "onNavigationItemSelected");
        return mNavHelper.performClickMenu(menuItem.getItemId());
    }

    public static void show(Context context){
        context.startActivity(new Intent(context, MainActivity.class));
    }


}
