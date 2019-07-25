package com.benboer.boluo.message.fragment.message;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.common.ui.adapter.TextWatcherAdapter;
import com.benboer.boluo.common.ui.recycler.RecyclerAdapter;
import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.R2;
import com.benboer.boluo.db.db.Message;
import com.benboer.boluo.db.db.User;
import com.benboer.boluo.message.fragment.panel.PanelFragment;
import com.benboer.boluo.message.presenter.message.ChatContract;
import com.benboer.boluo.message.widget.PortraitView;
import com.benboer.boluo.message.widget.face.Face;
import com.benboer.boluo.common.mvp.PresenterFragment;
import com.benboer.boluo.common.persistence.Account;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.widget.Loading;
import net.qiujuer.widget.airpanel.AirPanel;
import net.qiujuer.widget.airpanel.Util;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/6.
 *
 * 联系人聊天界面
 */
public abstract class ChatFragment<InitModel>
        extends PresenterFragment<ChatContract.Presenter>
        implements AppBarLayout.OnOffsetChangedListener,
        ChatContract.View<InitModel>,
        PanelFragment.PanelCallback {

    protected String mReceiverId;
    protected Adapter mAdapter;

    public static final String ARG_RECEIVER_ID = "SESSION_ID";

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @BindView(R2.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R2.id.appbar)
    AppBarLayout mAppBarLayout;

    @BindView(R2.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingLayout;

    @BindView(R2.id.edit_content)
    EditText mContent;

    @BindView(R2.id.btn_submit)
    View mSubmit;

    // 控制顶部面板与软键盘过度的Boss控件
    private AirPanel.Boss mPanelBoss;
    private PanelFragment mPanelFragment;

    protected ChatFragment() {
    }

    // 得到顶部布局的资源Id
    @LayoutRes
    protected abstract int getHeaderLayoutId();

    @Override
    public Object setLayout() {
        return R.layout.fragment_chat_common;
    }

    @Override
    protected void setStubView(View root) {
        ViewStub stub = root.findViewById(R.id.view_stub_header);
        stub.setLayoutResource(getHeaderLayoutId());
        stub.inflate();
    }


    @Override
    public void onAttach(Context context) {
        final Bundle args = getArguments();
        if (args != null) {
            mReceiverId = args.getString(ARG_RECEIVER_ID);
        }
        super.onAttach(context);
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        // 初始化面板操作
        mPanelBoss = (AirPanel.Boss) root.findViewById(R.id.lay_content);
        mPanelBoss.setup(new AirPanel.PanelListener() {
            @Override
            public void requestHideSoftKeyboard() {
                // 请求隐藏软键盘
                Util.hideKeyboard(mContent);
            }
        });
        mPanelFragment = (PanelFragment) getChildFragmentManager().findFragmentById(R.id.frag_panel);
        mPanelFragment.setup(this);

        initToolbar();
        initAppbar();
        initEditContent();

        // RecyclerView基本设置
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    protected void initToolbar() {
        Toolbar toolbar = mToolbar;
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportDelegate().pop();
            }
        });
    }

    //  给界面的Appbar设置一个监听，得到关闭与打开的时候的进度
    private void initAppbar() {
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    // 初始化输入框监听
    private void initEditContent() {
        mContent.addTextChangedListener(new TextWatcherAdapter() {

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString().trim();
                boolean needSendMsg = !TextUtils.isEmpty(content);
                // 设置状态，改变对应的Icon
                mSubmit.setActivated(needSendMsg);
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
    }

    @OnClick(R2.id.btn_face)
    void onFaceClick() {
        mPanelBoss.openPanel();
        mPanelFragment.showFace();
    }

    @OnClick(R2.id.btn_record)
    void onRecordClick() {
        mPanelBoss.openPanel();
        mPanelFragment.showRecord();
    }

    @OnClick(R2.id.btn_submit)
    void onSubmitClick() {
        if (mSubmit.isActivated()) {
            // 发送
            String content = mContent.getText().toString();
            mContent.setText("");
            mPresenter.pushText(content);
        } else {
            onMoreClick();
        }
    }

    private void onMoreClick() {
        mPanelBoss.openPanel();
        mPanelFragment.showGallery();
    }

    @Override
    public RecyclerAdapter<Message> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {

    }

    @Override
    public EditText getInputEditText() {
        return mContent;
    }

    // 内容的适配器
    private class Adapter extends RecyclerAdapter<Message> {

        @Override
        protected int getItemViewType(int position, Message message) {
            // 我发送的在右边，收到的在左边
            boolean isRight = Objects.equals(message.getSender().getId(), Account.getUserId());

            switch (message.getType()) {
                // 文字内容
                case Message.TYPE_STR:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;

                // 语音内容
                case Message.TYPE_AUDIO:
                    return isRight ? R.layout.cell_chat_audio_right : R.layout.cell_chat_audio_left;

                // 图片内容
                case Message.TYPE_PIC:
                    return isRight ? R.layout.cell_chat_pic_right : R.layout.cell_chat_pic_left;

                // 其他内容：文件
                default:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;
            }
        }

        @Override
        protected ViewHolder<Message> onCreateViewHolder(View root, int viewType) {
            switch (viewType) {
                // 左右都是同一个
                case R2.layout.cell_chat_text_right:
                case R2.layout.cell_chat_text_left:
                    return new TextHolder(root);

                case R2.layout.cell_chat_audio_right:
                case R2.layout.cell_chat_audio_left:
                    return new AudioHolder(root);

                case R2.layout.cell_chat_pic_right:
                case R2.layout.cell_chat_pic_left:
                    return new PicHolder(root);

                // 默认情况下，返回的就是Text类型的Holder进行处理
                // 文件的一些实现
                default:
                    return new TextHolder(root);
            }
        }
    }


    // Holder的基类
    class BaseHolder extends RecyclerAdapter.ViewHolder<Message> {
        @BindView(R2.id.im_portrait)
        PortraitView mPortrait;

        // 允许为空，左边没有，右边有
        @Nullable
        @BindView(R2.id.loading)
        Loading mLoading;

        public BaseHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            User sender = message.getSender();
            // 进行数据加载
            sender.load();
            // 头像加载
            mPortrait.setup(Glide.with(ChatFragment.this), sender);

            if (mLoading != null) {
                // 当前布局应该是在右边
                int status = message.getStatus();
                if (status == Message.STATUS_DONE) {
                    // 正常状态, 隐藏Loading
                    mLoading.stop();
                    mLoading.setVisibility(View.GONE);
                } else if (status == Message.STATUS_CREATED) {
                    // 正在发送中的状态
                    mLoading.setVisibility(View.VISIBLE);
                    mLoading.setProgress(0);
                    mLoading.setForegroundColor(UiCompat.getColor(getResources(), R.color.colorAccent));
                    mLoading.start();
                } else if (status == Message.STATUS_FAILED) {
                    // 发送失败状态, 允许重新发送
                    mLoading.setVisibility(View.VISIBLE);
                    mLoading.stop();
                    mLoading.setProgress(1);
                    mLoading.setForegroundColor(UiCompat.getColor(getResources(), R.color.alertImportant));
                }

                // 当状态是错误状态时才允许点击
                mPortrait.setEnabled(status == Message.STATUS_FAILED);
            }
        }

        @OnClick(R2.id.im_portrait)
        void onRePushClick() {
            // 重新发送
            if (mLoading != null && mPresenter.rePush(data)) {
                // 必须是右边的才有可能需要重新发送
                // 状态改变需要重新刷新界面当前的信息
                updateData(data);
            }

        }
    }

    // 文字的Holder
    class TextHolder extends BaseHolder {
        @BindView(R2.id.txt_content)
        TextView mContent;

        public TextHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            super.onBind(message);
            Spannable spannable = new SpannableString(message.getContent());

            // 解析表情
            Face.decode(mContent, spannable, (int) Ui.dipToPx(getResources(), 20));

            // 把内容设置到布局上
            mContent.setText(message.getContent());

        }
    }

    // 语音的Holder
    class AudioHolder extends BaseHolder {

        public AudioHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            super.onBind(message);
            // TODO
        }
    }

    // 图片的Holder
    class PicHolder extends BaseHolder {

        @BindView(R2.id.im_image)
        ImageView mContent;

        public PicHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            super.onBind(message);
            // 当是图片类型的时候，Content中就是具体的地址
            String content = message.getContent();

            Glide.with(ChatFragment.this)
                    .load(content)
                    .fitCenter()
                    .into(mContent);
        }
    }

}
