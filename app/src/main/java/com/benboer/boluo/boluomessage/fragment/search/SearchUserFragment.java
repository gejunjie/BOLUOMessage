package com.benboer.boluo.boluomessage.fragment.search;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.boluomessage.R;
import com.benboer.boluo.boluomessage.activity.PersonalActivity;
import com.benboer.boluo.boluomessage.activity.SearchActivity;
import com.benboer.boluo.common.app.PresenterFragment;
import com.benboer.boluo.common.widget.EmptyView;
import com.benboer.boluo.common.widget.PortraitView;
import com.benboer.boluo.common.widget.recycler.RecyclerAdapter;
import com.benboer.boluo.factory.model.card.UserCard;
import com.benboer.boluo.factory.presenter.contact.FollowContract;
import com.benboer.boluo.factory.presenter.contact.FollowPresenter;
import com.benboer.boluo.factory.presenter.search.SearchContract;
import com.benboer.boluo.factory.presenter.search.SearchUserPresenter;
import com.bumptech.glide.Glide;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.drawable.LoadingCircleDrawable;
import net.qiujuer.genius.ui.drawable.LoadingDrawable;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/20.
 */
public class SearchUserFragment extends PresenterFragment<SearchContract.Presenter>
        implements SearchContract.UserView, SearchActivity.SearchCallback {

    @BindView(R.id.empty)
    EmptyView mEmptyView;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private RecyclerAdapter<UserCard> mAdapter;

    @Override
    public void search(String content) {
        mPresenter.onSearch(content);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<UserCard>() {
            @Override
            protected int getItemViewType(int postion, UserCard data) {
                return R.layout.cell_search_list;
            }

            @Override
            protected ViewHolder<UserCard> onCreateViewHolder(View root, int viewType) {
                return new SearchUserFragment.ViewHolder(root);
            }
        });
        // 初始化占位布局
        mEmptyView.bind(mRecycler);
        setPlaceHolderView(mEmptyView);
    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        return new SearchUserPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_user;
    }

    @Override
    public void onSearchDone(List<UserCard> userCards) {
        mAdapter.replace(userCards);
        mPlaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount() > 0);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<UserCard> implements FollowContract.View{

        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.im_follow)
        ImageView mFollow;

        private FollowContract.Presenter mPresenter;


        public ViewHolder(View itemView) {
            super(itemView);
            // 当前View和Presenter绑定
            new FollowPresenter(this);
        }

        @OnClick(R.id.im_portrait)
        void onPortraitClick() {
            // 显示信息
            PersonalActivity.show(getContext(), data.getId());
        }

        @OnClick(R.id.im_follow)
        void onFollowClick() {
            // 发起关注
            mPresenter.follow(data.getId());
        }

        @Override
        protected void onBind(UserCard userCard) {
            mPortraitView.setup(Glide.with(SearchUserFragment.this), userCard);
            mName.setText(userCard.getName());
            mFollow.setEnabled(!userCard.isFollow());
        }

        @Override
        public void onFollowSuccess(UserCard userCard) {
            if (mFollow.getDrawable() instanceof LoadingDrawable) {
                ((LoadingDrawable) mFollow.getDrawable()).stop();
                // 设置为默认的
                mFollow.setImageResource(R.drawable.sel_opt_done_add);
            }
            // 发起更新
            updateData(userCard);
        }

        @Override
        public void showError(int str) {
            if (mFollow.getDrawable() instanceof LoadingDrawable) {
                // 失败则停止动画，并且显示一个圆圈
                LoadingDrawable drawable = (LoadingDrawable) mFollow.getDrawable();
                drawable.setProgress(1);
                drawable.stop();
            }
        }

        @Override
        public void showLoading() {
            int minSize = (int) Ui.dipToPx(getResources(), 22);
            int maxSize = (int) Ui.dipToPx(getResources(), 30);
            // 初始化一个圆形的动画的Drawable
            LoadingDrawable drawable = new LoadingCircleDrawable(minSize, maxSize);
            drawable.setBackgroundColor(0);

            int[] color = new int[]{UiCompat.getColor(getResources(), R.color.white_alpha_208)};
            drawable.setForegroundColor(color);
            mFollow.setImageDrawable(drawable);
            drawable.start();
        }

        @Override
        public void setPresenter(FollowContract.Presenter presenter) {
            mPresenter = presenter;
        }
    }

}
