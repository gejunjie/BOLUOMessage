package com.benboer.boluo.message.presenter.user;

import android.text.TextUtils;

import com.benboer.boluo.factory.R;
import com.benboer.boluo.lib_db.db.User;
import com.benboer.boluo.message.data.helper.UserHelper;
import com.benboer.boluo.message.model.api.user.UserUpdateModel;
import com.benboer.boluo.message.model.card.UserCard;
import com.benboer.boluo.module_common.Factory;
import com.benboer.boluo.module_common.mvp.data.DataSource;
import com.benboer.boluo.module_common.mvp.presenter.BasePresenter;
import com.benboer.boluo.module_common.net.UploadHelper;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/8.
 */
public class UpdateInfoPresenter extends BasePresenter<UpdateInfoContract.View>
    implements UpdateInfoContract.Presenter, DataSource.Callback<UserCard> {

    public UpdateInfoPresenter(UpdateInfoContract.View view) {
        super(view);
    }

    @Override
    public void onDataLoaded(UserCard userCard) {
        final UpdateInfoContract.View view = getView();
        if (view == null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.updateSucceed();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final UpdateInfoContract.View view = getView();
        if (view == null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(strRes);
            }
        });
    }

    @Override
    public void update(final String photoFilePath, final String desc, final boolean isMan) {
        start();
        final UpdateInfoContract.View view = getView();
        if (TextUtils.isEmpty(photoFilePath) || TextUtils.isEmpty(desc)) {
            view.showError(R.string.data_account_update_invalid_parameter);
        } else {
            // 上传头像
            Factory.runOnAsync(new Runnable() {
                @Override
                public void run() {
                    String url = UploadHelper.uploadPortrait(photoFilePath);
                    if (TextUtils.isEmpty(url)) {
                        // 上传失败
                        Run.onUiAsync(new Action() {
                            @Override
                            public void call() {
                                view.showError(R.string.data_upload_error);
                            }
                        });
                    } else {
                        // 构建Model
                        UserUpdateModel model = new UserUpdateModel("", url, desc,
                                isMan ? User.SEX_MAN : User.SEX_WOMAN);
                        // 进行网络请求，上传
                        UserHelper.update(model, UpdateInfoPresenter.this);
                    }
                }
            });
        }
    }
}
