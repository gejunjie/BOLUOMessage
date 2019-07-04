package com.benboer.boluo.factory.fragment.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;

import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.R2;
import com.benboer.boluo.factory.fragment.message.ChatUserFragment;
import com.benboer.boluo.core.fragment.PresenterFragment;
import com.benboer.boluo.factory.model.db.User;
import com.benboer.boluo.factory.presenter.contact.PersonalContract;
import com.benboer.boluo.factory.presenter.contact.PersonalPresenter;
import com.benboer.boluo.widget.PortraitView;
import com.bumptech.glide.Glide;

import net.qiujuer.genius.res.Resource;
import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/3.
 */
public class PersonalFragment extends PresenterFragment<PersonalContract.Presenter>
        implements PersonalContract.View {

    @BindView(R2.id.im_header)
    ImageView mHeader;
    @BindView(R2.id.im_portrait)
    PortraitView mPortrait;
    @BindView(R2.id.txt_name)
    TextView mName;
    @BindView(R2.id.txt_desc)
    TextView mDesc;
    @BindView(R2.id.txt_follows)
    TextView mFollows;
    @BindView(R2.id.txt_following)
    TextView mFollowing;
    @BindView(R2.id.btn_say_hello)
    Button mSayHello;

    private MenuItem mFollowItem;
    private boolean mIsFollowUser = false;

    private static final String ARG_USER_ID = "USER_ID";
    private static String userId;
    /**
     *
     *
     * @param id
     */
    public static PersonalFragment newInstance(String id) {
        if (!TextUtils.isEmpty(id)){
            final Bundle args = new Bundle();
            args.putString(ARG_USER_ID, id);
            final PersonalFragment fragment = new PersonalFragment();
            fragment.setArguments(args);
            return fragment;
        }
       return null;
    }

    @Override
    public void onAttach(Context context) {
        final Bundle args = getArguments();
        if (args != null) {
            userId = args.getString(ARG_USER_ID);
        }
        super.onAttach(context);
    }

    @Override
    protected PersonalContract.Presenter initPresenter() {
        return new PersonalPresenter(this);
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {

    }

    @Override
    public String getUserId() {
        return userId;
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onLoadDone(User user) {
        mPortrait.setup(Glide.with(this), user);
        mName.setText(user.getName());
        mDesc.setText(user.getDesc());
        mFollows.setText(String.format(getString(R.string.label_follows), user.getFollows()));
        mFollowing.setText(String.format(getString(R.string.label_following), user.getFollowing()));
        hideLoading();
    }

    @Override
    public void allowSayHello(boolean isAllow) {
        mSayHello.setVisibility(isAllow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setFollowStatus(boolean isFollow) {
        mIsFollowUser = isFollow;
        changeFollowItemStatus();
    }

    /**
     * 更改关注菜单状态
     */
    private void changeFollowItemStatus() {
        if (mFollowItem == null)
            return;

        // 根据状态设置颜色
        Drawable drawable = mIsFollowUser ? getResources()
                .getDrawable(R.drawable.ic_favorite) :
                getResources().getDrawable(R.drawable.ic_favorite_border);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Resource.Color.WHITE);
        mFollowItem.setIcon(drawable);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.personal, menu);
        mFollowItem = menu.findItem(R.id.action_follow);
        changeFollowItemStatus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_follow) {
//            mPresenter
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R2.id.btn_say_hello)
    void onSayHelloClick() {
        // 发起聊天的点击
        User user = mPresenter.getUserPersonal();
        if (user == null)
            return;
        getSupportDelegate().startWithPop(ChatUserFragment.newInstance(user));
    }
}
