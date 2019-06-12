package com.benboer.boluo.factory.data.helper;

import com.benboer.boluo.factory.Factory;
import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.data.DataSource;
import com.benboer.boluo.factory.model.api.RspModel;
import com.benboer.boluo.factory.model.api.group.GroupCreateModel;
import com.benboer.boluo.factory.model.card.GroupCard;
import com.benboer.boluo.factory.model.db.Group;
import com.benboer.boluo.factory.net.Network;
import com.benboer.boluo.factory.net.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/24.
 */
public class GroupHelper {

    // 群的创建
    public static void create(GroupCreateModel model, final DataSource.Callback<GroupCard> callback) {
        RemoteService service = Network.remote();
        service.groupCreate(model)
                .enqueue(new Callback<RspModel<GroupCard>>() {
                    @Override
                    public void onResponse(Call<RspModel<GroupCard>> call, Response<RspModel<GroupCard>> response) {
                        RspModel<GroupCard> rspModel = response.body();
                        if (rspModel.success()) {
                            GroupCard groupCard = rspModel.getResult();
                            // 唤起进行保存的操作
                            Factory.getGroupCenter().dispatch(groupCard);
                            // 返回数据
                            callback.onDataLoaded(groupCard);
                        } else {
                            Factory.decodeRspCode(rspModel, callback);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<GroupCard>> call, Throwable t) {
                        callback.onDataNotAvailable(R.string.data_network_error);
                    }
                });
    }

    public static Group find(String groupId) {
        // TODO 查询群的信息，先本地，后网络
        return null;
    }

    public static Group findFromLocal(String groupId) {
        // TODO 本地找群信息
        return null;
    }

    // 刷新我的群组列表
    public static void refreshGroups() {

    }
}
