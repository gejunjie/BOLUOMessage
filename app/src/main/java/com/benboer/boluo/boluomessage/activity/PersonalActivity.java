package com.benboer.boluo.boluomessage.activity;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.common.app.PresenterToolbarActivity;
import com.benboer.boluo.common.widget.PortraitView;
import com.benboer.boluo.factory.presenter.contact.PersonalContract;
import com.benboer.boluo.factory.presenter.contact.PersonalPresenter;

import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/15.
 */
public class PersonalActivity extends PresenterToolbarActivity<PersonalContract.Presenter>
        implements PersonalContract.View {

    private static final String BOUND_KEY_ID = "BOUND_KEY_ID";
    private String userId;

    @BindView(R.id.im_header)
    ImageView mHeader;
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;
    @BindView(R.id.txt_name)
    TextView mName;
    @BindView(R.id.txt_desc)
    TextView mDesc;
    @BindView(R.id.txt_follows)
    TextView mFollows;
    @BindView(R.id.txt_following)
    TextView mFollowing;
    @BindView(R.id.btn_say_hello)
    Button mSayHello;

    private MenuItem mFollowItem;
    private boolean mIsFollowUser = false;

    public static void show(Context context, String userId) {
        Intent intent = new Intent(context, PersonalActivity.class);
        intent.putExtra(BOUND_KEY_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected PersonalContract.Presenter initPresenter() {
        return new PersonalPresenter(this);
    }


}
