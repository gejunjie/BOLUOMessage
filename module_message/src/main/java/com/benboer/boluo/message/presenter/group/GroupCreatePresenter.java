package com.benboer.boluo.message.presenter.group;

import android.text.TextUtils;

import com.benboer.boluo.module_common.Factory;
import com.benboer.boluo.factory.R;
import com.benboer.boluo.module_common.base.mvp.data.DataSource;
import com.benboer.boluo.message.data.helper.GroupHelper;
import com.benboer.boluo.message.data.helper.UserHelper;
import com.benboer.boluo.message.model.api.group.GroupCreateModel;
import com.benboer.boluo.message.model.card.GroupCard;
import com.benboer.boluo.message.model.db.view.UserSampleModel;
import com.benboer.boluo.module_common.net.UploadHelper;
import com.benboer.boluo.module_common.base.mvp.presenter.BaseRecyclerPresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/10.
 */
public class GroupCreatePresenter extends BaseRecyclerPresenter<GroupCreateContract.ViewModel,
                                                                GroupCreateContract.View>
        implements GroupCreateContract.Presenter,
                   DataSource.Callback<GroupCard> {

    private Set<String> users = new HashSet<>();

    public GroupCreatePresenter(GroupCreateContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                List<UserSampleModel> sampleModels = UserHelper.getSampleContact();
                List<GroupCreateContract.ViewModel> models = new ArrayList<>();
                for (UserSampleModel sampleModel : sampleModels) {
                    GroupCreateContract.ViewModel viewModel = new GroupCreateContract.ViewModel();
                    viewModel.author = sampleModel;
                    models.add(viewModel);
                }

                refreshData(models);
            }
        });
    }

    @Override
    public void onDataLoaded(GroupCard groupCard) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                GroupCreateContract.View view =getView();
                if (view != null){
                    view.onCreateSucceed();
                }
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                GroupCreateContract.View view = getView();
                if ( getView() != null) {
                    view.showError(strRes);
                }
            }
        });
    }

    @Override
    public void create(final String name, final String desc, final String picture) {
        GroupCreateContract.View view = getView();
        view.showLoading();

        // 判断参数
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(desc) ||
                TextUtils.isEmpty(picture) || users.size() == 0) {
            view.showError(R.string.label_group_create_invalid);
            return;
        }

        // 上传图片
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                String url = uploadPicture(picture);
                if (TextUtils.isEmpty(url))
                    return;
                // 进行网络请求
                GroupCreateModel model = new GroupCreateModel(name, desc, url, users);
                GroupHelper.create(model, GroupCreatePresenter.this);
            }
        });
    }

    // 同步上传操作
    private String uploadPicture(String path) {
        String url = UploadHelper.uploadPortrait(path);
        if (TextUtils.isEmpty(url)) {
            // 切换到UI线程 提示信息
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    GroupCreateContract.View view = getView();
                    if (view != null) {
                        view.showError(R.string.data_upload_error);
                    }
                }
            });
        }
        return url;
    }

    @Override
    public void changeSelect(GroupCreateContract.ViewModel model, boolean isSelected) {
        if (isSelected)
            users.add(model.author.getId());
        else
            users.remove(model.author.getId());
    }
}
