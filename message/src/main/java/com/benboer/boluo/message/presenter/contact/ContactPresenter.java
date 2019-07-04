package com.benboer.boluo.message.presenter.contact;

import androidx.recyclerview.widget.DiffUtil;

import com.benboer.boluo.message.widget.recycler.RecyclerAdapter;
import com.benboer.boluo.message.base.data.DataSource;
import com.benboer.boluo.message.data.helper.UserHelper;
import com.benboer.boluo.message.data.user.ContactDataSource;
import com.benboer.boluo.message.data.user.ContactRepository;
import com.benboer.boluo.message.model.db.User;
import com.benboer.boluo.message.presenter.BaseSourcePresenter;
import com.benboer.boluo.message.utils.DiffUiDataCallback;

import java.util.List;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/14.
 */
public class ContactPresenter extends BaseSourcePresenter<User, User, ContactDataSource, ContactContract.View>
        implements ContactContract.Presenter, DataSource.SucceedCallback<List<User>> {

    public ContactPresenter(ContactContract.View view) {
        // 初始化数据仓库
        super(new ContactRepository(), view);
    }

    @Override
    public void start() {
        super.start();
        UserHelper.refreshContacts();
    }

    @Override
    public void onDataLoaded(List<User> users) {
        final ContactContract.View view = getView();
        if (view == null) return;
        RecyclerAdapter<User> adapter = view.getRecyclerAdapter();
        List<User> old = adapter.getItems();

        // 进行数据对比
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, users);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 调用基类方法进行界面刷新
        refreshData(result, users);
    }
}
//        UserHelper.refreshContacts();

//        // 加载本地数据库数据
//        SQLite.select()
//                .from(User.class)
//                .where(User_Table.isFollow.eq(true))
//                .and(User_Table.id.notEq(Account.getUserId()))
//                .orderBy(User_Table.name, true)
//                .limit(100)
//                .async()
//                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<User>() {
//                    @Override
//                    public void onListQueryResult(QueryTransaction transaction,
//                                                  @NonNull List<User> tResult) {
//
//                        getView().getRecyclerAdapter().replace(tResult);
//                        getView().onAdapterDataChanged();
//
//                    }
//                })
//                .execute();
//
//
//        // 加载网络数据
//        UserHelper.refreshContacts(new DataSource.Callback<List<UserCard>>() {
//            @Override
//            public void onDataNotAvailable(int strRes) {
//                // 网络失败，因为本地有数据，不管错误
//            }
//
//            @Override
//            public void onDataLoaded(final List<UserCard> userCards) {
//                // 转换为User
//                final List<User> users = new ArrayList<>();
//                for (UserCard userCard : userCards) {
//                    users.add(userCard.build());
//                }
//
//                // 丢到事物中保存数据库
//                DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
//                definition.beginTransactionAsync(new ITransaction() {
//                    @Override
//                    public void execute(DatabaseWrapper databaseWrapper) {
//                        FlowManager.getModelAdapter(User.class)
//                                .saveAll(users);
//                    }
//                }).build().execute();
//
//                // 网络的数据往往是新的，我们需要直接刷新到界面
//                List<User> old = getView().getRecyclerAdapter().getItems();
//                // 会导致数据顺序全部为新的数据集合
//                // getView().getRecyclerAdapter().replace(users);
//                diff(old, users);
//            }
//        });
//
//        // TODO 问题：
//        // 1.关注后虽然存储数据库，但是没有刷新联系人
//        // 2.如果刷新数据库，或者从网络刷新，最终刷新的时候是全局刷新
//        // 3.本地刷新和网络刷新，在添加到界面的时候会有可能冲突；导致数据显示异常
//        // 4.如何识别已经在数据库中有这样的数据了
//
//    }
//    private void diff(List<User> oldList, List<User> newList) {
//        // 进行数据对比
//        DiffUtil.Callback callback = new DiffUiDataCallback<>(oldList, newList);
//        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
//
//        // 在对比完成后进行数据的赋值
//        getView().getRecyclerAdapter().replace(newList);
//
//        // 尝试刷新界面
//        result.dispatchUpdatesTo(getView().getRecyclerAdapter());
//        getView().onAdapterDataChanged();
//    }
//}

