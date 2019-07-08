package com.benboer.boluo.message.fragment.search;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.R2;
import com.benboer.boluo.message.fragment.user.PersonalFragment;
import com.benboer.boluo.message.fragment.PresenterFragment;
import com.benboer.boluo.message.model.card.UserCard;
import com.benboer.boluo.message.presenter.contact.FollowContract;
import com.benboer.boluo.message.presenter.contact.FollowPresenter;
import com.benboer.boluo.message.presenter.search.SearchContract;
import com.benboer.boluo.message.presenter.search.SearchUserPresenter;
import com.benboer.boluo.message.widget.EmptyView;
import com.benboer.boluo.message.widget.PortraitView;
import com.benboer.boluo.core.ui.recycler.RecyclerAdapter;
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
 *
 * 搜索用户的Fragment
 */
public class SearchUserFragment extends PresenterFragment<SearchContract.Presenter>
        implements SearchContract.UserView{

    @BindView(R2.id.empty)
    EmptyView mEmptyView;

    @BindView(R2.id.recycler)
    RecyclerView mRecycler;

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    private RecyclerAdapter<UserCard> mAdapter;

    @Override
    protected SearchContract.Presenter initPresenter() {
        return new SearchUserPresenter(this);
    }

    @Override
    public void onSearchDone(List<UserCard> userCards) {
        mAdapter.replace(userCards);
        mPlaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount() > 0);
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_search_user;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {

        //让onCreateOptionsMenu方法被调用
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        setHasOptionsMenu(true);

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String content) {
                mPresenter.onSearch(content);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String content) {
                if (TextUtils.isEmpty(content)) {
                    mPresenter.onSearch("");
                    return true;
                }
                return false;
            }
        });
    }


    class ViewHolder extends RecyclerAdapter.ViewHolder<UserCard> implements FollowContract.View {

        @BindView(R2.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R2.id.txt_name)
        TextView mName;

        @BindView(R2.id.im_follow)
        ImageView mFollow;

        private FollowContract.Presenter mPresenter;

        public ViewHolder(View itemView) {
            super(itemView);
            // 当前View和Presenter绑定
            new FollowPresenter(this);
        }

        @OnClick(R2.id.im_portrait)
        void onPortraitClick() {
            // 显示信息
            getSupportDelegate().start(PersonalFragment.newInstance(data.getId()));
        }

        @OnClick(R2.id.im_follow)
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
