package com.benboer.boluo.message.fragment.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.common.ui.recycler.RecyclerAdapter;
import com.benboer.boluo.common.util.DateTimeUtil;
import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.R2;
import com.benboer.boluo.db.db.Session;
import com.benboer.boluo.message.fragment.message.ChatUserFragment;
import com.benboer.boluo.message.presenter.message.SessionContract;
import com.benboer.boluo.message.presenter.message.SessionPresenter;
import com.benboer.boluo.message.widget.EmptyView;
import com.benboer.boluo.message.widget.PortraitView;
import com.benboer.boluo.common.mvp.PresenterFragment;
import com.bumptech.glide.Glide;

import butterknife.BindView;

/**
 * Created by BenBoerBoluojiushiwo on 2019/3/28.
 *
 * 最近两天界面
 */
public class ActiveFragment extends PresenterFragment<SessionContract.Presenter>
        implements SessionContract.View{

    @BindView(R2.id.empty)
    EmptyView mEmptyView;

    @BindView(R2.id.recycler)
    RecyclerView mRecycler;

    // 适配器，User，可以直接从数据库查询数据
    private RecyclerAdapter<Session> mAdapter;

    public ActiveFragment() {

    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_active;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<Session>() {
            @Override
            protected int getItemViewType(int position, Session session) {
                return R.layout.cell_chat_list;
            }

            @Override
            protected ViewHolder<Session> onCreateViewHolder(View root, int viewType) {
                return new ActiveFragment.ViewHolder(root);
            }
        });

        // 点击事件监听
        mAdapter.setAdapterListener(new RecyclerAdapter.AdapterListenerImpl<Session>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Session session) {
                // 跳转到聊天界面
                getParentFragments().getSupportDelegate().start(ChatUserFragment.newInstance(session));
            }
        });

        // 初始化占位布局
        mEmptyView.bind(mRecycler);
        setPlaceHolderView(mEmptyView);
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        mPresenter.start();
    }

    @Override
    protected SessionContract.Presenter initPresenter() {
        return new SessionPresenter(this);
    }

    @Override
    public RecyclerAdapter<Session> getRecyclerAdapter() {
        return mAdapter;
    }


    @Override
    public void onAdapterDataChanged() {
        mPlaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount() > 0);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<Session> {
        @BindView(R2.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R2.id.txt_name)
        TextView mName;

        @BindView(R2.id.txt_content)
        TextView mContent;

        @BindView(R2.id.txt_time)
        TextView mTime;

        ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Session session) {
            mPortraitView.setup(Glide.with(ActiveFragment.this), session.getPicture());
            mName.setText(session.getTitle());
            mContent.setText(TextUtils.isEmpty(session.getContent()) ? "" : session.getContent());
            mTime.setText( session.getModifyAt() == null ?
                    "" : DateTimeUtil.getSampleDate(session.getModifyAt()));
        }
    }

//    // 再点一次退出程序时间设置
//    private static final long WAIT_TIME = 2000L;
//    private long TOUCH_TIME = 0;

//    @Override
//    public boolean onBackPressedSupport() {
//        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
//            mActivity.finish();
//        } else {
//            TOUCH_TIME = System.currentTimeMillis();
//            Toast.makeText(mActivity, "双击退出" + BoLuo.getApplicationContext().getString(com.benboer.boluo.core.R.string.app_name), Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }
}
