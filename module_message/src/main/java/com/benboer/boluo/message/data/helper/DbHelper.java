package com.benboer.boluo.message.data.helper;

import android.text.TextUtils;

import com.benboer.boluo.db.db.AppDatabase;
import com.benboer.boluo.db.db.Group;
import com.benboer.boluo.db.db.GroupMember;
import com.benboer.boluo.db.db.Group_Table;
import com.benboer.boluo.db.db.Message;
import com.benboer.boluo.db.db.Session;
import com.benboer.boluo.db.db.User;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by BenBoerBoluojiushiwo on 2019/5/23.
 */
public final class DbHelper {

    private static final DbHelper instance;

    static {
        instance = new DbHelper();
    }

    private DbHelper(){

    }

    /**
     * 观察者的集合
     * Class<?>： 观察的表
     * Set<ChangedListener>：每一个表对应的观察者
     */
    private final Map<Class<?>, Set<ChangedListener>> changedListeners = new HashMap<>();

    /**
     * 添加一个监听器
     * @param clazz
     * @param changedListener
     * @param <Model>
     */
    public static <Model extends BaseModel> void addChangedListener(final Class<Model> clazz,
                                                                   ChangedListener<Model> changedListener){
        Set<ChangedListener> changedListenerSet = instance.getListeners(clazz);
        if (changedListenerSet == null){
            changedListenerSet = new HashSet<>();
            changedListenerSet.add(changedListener);
            instance.changedListeners.put(clazz, changedListenerSet);
        }
        changedListenerSet.add(changedListener);
    }

    /**
     * 移除某个监听器
     * @param clazz
     * @param changedListener
     * @param <Model>
     */
    public static <Model extends BaseModel> void removeListener(final Class<Model> clazz,
                                                                ChangedListener<Model> changedListener){
        Set<ChangedListener> changedListenerSet = instance.getListeners(clazz);
        if (instance.getListeners(clazz) != null) return;
        changedListenerSet.remove(changedListener);
    }

    /**
     * 获取某一个表的所有监听器
     *
     * @param clazz 表对应的Class信息
     * @param <Model>    范型
     * @return Set<ChangedListener>
     */
    private <Model extends BaseModel> Set<ChangedListener> getListeners(Class<Model> clazz) {
        if (changedListeners.containsKey(clazz)) {
            return changedListeners.get(clazz);
        }
        return null;
    }

    public static <Model extends BaseModel> void save(final Class<Model> clazz,
                                                      final Model... models) {
        if (clazz == null || models == null) return;
        DatabaseDefinition databaseDefinition = FlowManager.getDatabase(AppDatabase.class);
        //提交事务
        databaseDefinition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                // 执行
                ModelAdapter<Model> adapter = FlowManager.getModelAdapter(clazz);
                // 保存
                adapter.saveAll(Arrays.asList(models));
                // 唤起通知
                instance.notifySave(clazz, models);
            }
        }).build().execute();
    }

    public static <Model extends BaseModel> void delete(final Class<Model> clazz,
                                                        final Model... models){
        if (clazz == null || models == null) return;
        DatabaseDefinition databaseDefinition = FlowManager.getDatabase(AppDatabase.class);
        databaseDefinition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                ModelAdapter<Model> adapter = FlowManager.getModelAdapter(clazz);
                adapter.deleteAll(Arrays.asList(models));

            }
        }).build().execute();
    }

    private static <Model extends BaseModel> void notifySave(Class<Model> clazz,
                                                             Model... models){
        final Set<ChangedListener> changedListenerSet = instance.getListeners(clazz);
        if(changedListenerSet != null && changedListenerSet.size() > 0){
            for (ChangedListener<Model> listener : changedListenerSet){
                listener.onDataSave(models);
            }
        }
        if (GroupMember.class.equals(clazz)) {
            // 群成员变更，需要通知对应群信息更新
            updateGroup((GroupMember[]) models);
        } else if (Message.class.equals(clazz)) {
            // 消息变化，应该通知会话列表更新
            updateSession((Message[]) models);
        }
    }

    private static <Model extends BaseModel> void notiftDelete(Class<Model> clazz,
                                                               Model... models){
        final Set<ChangedListener> changedListenerSet = instance.getListeners(clazz);
        if (changedListenerSet != null && changedListenerSet.size() > 0){
            for (ChangedListener<Model> listener : changedListenerSet){
                listener.onDataDelete(models);
            }
        }
        if (GroupMember.class.equals(clazz)) {
            // 群成员变更，需要通知对应群信息更新
            updateGroup((GroupMember[]) models);
        } else if (Message.class.equals(clazz)) {
            // 消息变化，应该通知会话列表更新
            updateSession((Message[]) models);
        }
    }

    private static void updateGroup(GroupMember[] members) {
        final Set<String> groupIds = new HashSet<>();
        for (GroupMember member : members) {
            // 添加群Id
            groupIds.add(member.getGroup().getId());
        }

        // 异步的数据库查询，并异步的发起二次通知
        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
        definition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                // 找到需要通知的群
                List<Group> groups = SQLite.select()
                        .from(Group.class)
                        .where(Group_Table.id.in(groupIds))
                        .queryList();

                // 调用直接进行一次通知分发
                instance.notifySave(Group.class, groups.toArray(new Group[0]));

            }
        });
    }

    private static void updateSession(Message[] messages) {
        final Set<Session.Identify> identifies = new HashSet<>();
        for (Message message : messages){
            Session.Identify identify = Session.createSessionIdentify(message);
            identifies.add(identify);
        }
        DatabaseDefinition databaseDefinition = FlowManager.getDatabase(AppDatabase.class);
        databaseDefinition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                ModelAdapter<Session> adapter = FlowManager.getModelAdapter(Session.class);
                Session[] sessions = new Session[identifies.size()];

                int index = 0;
                for (Session.Identify identify : identifies) {
                    Session session = SessionHelper.findFromLocal(identify.id);

                    if (session == null) {
                        // 第一次聊天，创建一个和对方的一个会话
                        session = new Session(identify);
                    }
                    // 把会话，刷新到当前Message的最新状态
                    refreshToNow(session);
//                    session.refreshToNow();
                    // 数据存储
                    adapter.save(session);
                    // 添加到集合
                    sessions[index++] = session;
                }

                // 调用直接进行一次通知分发
                instance.notifySave(Session.class, sessions);
            }
        }).build().execute();
    }

    public interface ChangedListener<Data extends BaseModel>{

        void onDataSave(Data... list);

        void onDataDelete(Data... list);
    }

    private static void refreshToNow(Session session) {
        Message message;
        String id = session.getId();
        String picture = session.getPicture();
        String title = session.getTitle();
        if (session.getReceiverType() == Message.RECEIVER_TYPE_GROUP) {
            // 刷新当前对应的群的相关信息
            message = MessageHelper.findLastWithGroup(id);
            if (message == null) {
                // 如果没有基本信息
                if (TextUtils.isEmpty(picture)
                        || TextUtils.isEmpty(title)) {
                    // 查询群
                    Group group = GroupHelper.findFromLocal(id);
                    if (group != null) {
                        session.setPicture(group.getPicture());
                        session.setTitle(group.getName());
                    }
                }
                session.setMessage(null);
                session.setContent("");
                session.setModifyAt(new Date(System.currentTimeMillis()));
            } else {
                // 本地有最后一条聊天记录
                if (TextUtils.isEmpty(picture)
                        || TextUtils.isEmpty(title)) {
                    // 如果没有基本信息, 直接从Message中去load群信息
                    Group group = message.getGroup();
                    group.load();
                    session.setPicture(group.getPicture());
                    session.setTitle(group.getName());
                }
                session.setMessage(message);
                session.setContent(message.getSampleContent());
                session.setModifyAt(message.getCreateAt());
            }
        } else {
            // 和人聊天的
            message = MessageHelper.findLastWithUser(id);
            if (message == null) {
                // 消息已经删除完成了
                // 如果没有基本信息
                if (TextUtils.isEmpty(picture)
                        || TextUtils.isEmpty(title)) {
                    // 查询人
                    User user = UserHelper.findFromLocal(id);
                    if (user != null) {
                        session.setPicture(user.getPortrait());
                        session.setTitle(user.getName());
                    }
                }
                session.setMessage(null);
                session.setContent("");
                session.setModifyAt(new Date(System.currentTimeMillis()));
            } else {
                if (TextUtils.isEmpty(picture)
                        || TextUtils.isEmpty(title)) {
                    // 查询人
                    User other = message.getOther();
                    other.load(); // 懒加载问题
                    session.setPicture(other.getPortrait());
                    session.setTitle(other.getName());
                }
                session.setMessage(message);
                session.setContent(message.getSampleContent());
                session.setModifyAt(message.getCreateAt());
            }
        }
    }
}
