package com.benboer.boluo.factory.data.helper;

import com.benboer.boluo.factory.Factory;
import com.benboer.boluo.factory.R;
import com.benboer.boluo.factory.data.DataSource;
import com.benboer.boluo.factory.model.api.RspModel;
import com.benboer.boluo.factory.model.api.user.UserUpdateModel;
import com.benboer.boluo.factory.model.card.UserCard;
import com.benboer.boluo.factory.model.db.User;
import com.benboer.boluo.factory.model.db.User_Table;
import com.benboer.boluo.factory.model.db.view.UserSampleModel;
import com.benboer.boluo.factory.net.Network;
import com.benboer.boluo.factory.net.RemoteService;
import com.benboer.boluo.factory.persistence.Account;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/6.
 *
 * 用户更新
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
                    Factory.getUserCenter().dispatch(userCard);
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

    // 刷新联系人的操作
    public static void refreshContacts(){
        RemoteService service = Network.remote();
        Call<RspModel<List<UserCard>>> call = service.userContacts();
        call.enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel = response.body();
                if (rspModel.success()){
                    List<UserCard> cards = rspModel.getResult();
                    if (cards == null || cards.size() == 0) return;
                    UserCard[] cards1 = cards.toArray(new UserCard[0]);
                    // CollectionUtil.toArray(cards, UserCard.class);
                    Factory.getUserCenter().dispatch(cards1);
                }else {
                    Factory.decodeRspCode(rspModel, null);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {

            }
        });
    }

    // 搜索的方法
    public static Call userSearch(String name, final DataSource.Callback<List<UserCard>> callback) {
        RemoteService service = Network.remote();
        Call<RspModel<List<UserCard>>> call = service.userSearch(name);

        call.enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel = response.body();
                if (rspModel.success()) {
                    // 返回数据
                    callback.onDataLoaded(rspModel.getResult());
                } else {
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });

        return call;
    }


    /**
     * 关注网络请求
     * @param id
     * @param callback
     */
    public static void follow(String id, final DataSource.Callback<UserCard> callback){
        RemoteService service = Network.remote();
        Call<RspModel<UserCard>> call = service.userFollow(id);
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel == null) return;
                if (rspModel.success()){
                    UserCard userCard = rspModel.getResult();
                    Factory.getUserCenter().dispatch(userCard);
                    callback.onDataLoaded(userCard);
                }else{
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    /**
     * 搜索一个用户，优先网络查询
     * 没有用然后再从本地缓存拉取
     */
    public static User searchFirstOfNet(String id) {
        User user = findFromNet(id);
        if (user == null) {
            return findFromLocal(id);
        }
        return user;
    }

    /**
     * 从本地查询一个用户的信息
     */
    public static User findFromLocal(String id) {
        return SQLite.select()
                .from(User.class)
                .where(User_Table.id.eq(id))
                .querySingle();
    }

    /**
     * 搜索一个用户，优先本地缓存，
     * 没有用然后再从网络拉取
     */
    public static User search(String id) {
        User user = findFromLocal(id);
        if (user == null) {
            return findFromNet(id);
        }
        return user;
    }

    public static User findFromNet(String id) {

        RemoteService remoteService = Network.remote();
        try {
            Response<RspModel<UserCard>> response = remoteService.userFind(id).execute();
            UserCard card = response.body().getResult();
            if (card != null) {
                User user = card.build();
                Factory.getUserCenter().dispatch(card);
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取一个联系人列表
     * @return
     */
    public static List<UserSampleModel> getSampleContact() {
        //"select id = ??";
        //"select User_id = ??";
        return SQLite.select(User_Table.id.withTable().as("id"),
                User_Table.name.withTable().as("name"),
                User_Table.portrait.withTable().as("portrait"))
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name, true)
                .queryCustomList(UserSampleModel.class);
    }
}
