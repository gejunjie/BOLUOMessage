package com.benboer.boluo.factory.data.helper;

import com.benboer.boluo.factory.Factory;
import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.data.DataSource;
import com.benboer.boluo.factory.model.api.RspModel;
import com.benboer.boluo.factory.model.api.user.UserUpdateModel;
import com.benboer.boluo.factory.model.card.UserCard;
import com.benboer.boluo.factory.model.db.User;
import com.benboer.boluo.factory.net.Network;
import com.benboer.boluo.factory.net.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 *
 * 用户更新网络接口
 */
public class UserHelper {
    // 异步更新用户信息的操作
    public static void update(final UserUpdateModel model, final DataSource.Callback<UserCard> callback) {
        RemoteService service = Network.remote();
        Call<RspModel<UserCard>> call = service.updateUser(model);
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()){
                    UserCard userCard = rspModel.getResult();
                    User user = userCard.build();
                    user.save();
                    callback.onDataLoaded(userCard);
                }else {
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }
}
