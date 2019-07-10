package com.benboer.boluo.module_common;

import com.benboer.boluo.core.app.BoLuo;
import com.benboer.boluo.module_common.utils.DBFlowExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by BenBoerBoluojiushiwo on 2019/4/1.
 */
public class Factory {

    private static final Factory instance;

    private static Executor executor;

    // 全局的Gson
    private final Gson gson;

    static {
         instance = new Factory();
    }

//    /**
//     * 返回全局的Application
//     *
//     * @return Application
//     */
//    public static Application app() {
//        return Application.getInstance();
//    }

    private Factory(){
        executor = Executors.newFixedThreadPool(4);

        gson = new GsonBuilder()
                // 设置时间格式
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                // 设置一个过滤器，数据库级别的Model不进行Json转换
                .setExclusionStrategies(new DBFlowExclusionStrategy())
                .create();
    }

    /**
     * Factory 中的初始化
     */
    public static void setup() {
        // 初始化数据库
        FlowManager.init(new FlowConfig.Builder(BoLuo.getApplicationContext())
                .openDatabasesOnInit(true) // 数据库初始化的时候就开始打开
                .build());

    }

    public static void runOnAsync(Runnable runnable){
        executor.execute(runnable);
    }

    /**
     * 返回一个全局的Gson
     *
     * @return Gson
     */
    public static Gson getGson() {
        return instance.gson;
    }






    /**
     * 收到账户退出的消息需要进行账户退出重新登录
     */
    private void logout() {

    }

//    /**
//     * 处理推送来的消息
//     *
//     * @param str 消息
//     */
//    public static void dispatchPush(String str) {
//        // 首先检查登录状态
//        if (!Account.isLogin())
//            return;
//
//        PushModel model = PushModel.decode(str);
//        if (model == null)
//            return;
//
//        Log.e(TAG, model.toString());
//        // 对推送集合进行遍历
//        for (PushModel.Entity entity : model.getEntities()) {
//            switch (entity.type) {
//                case PushModel.ENTITY_TYPE_LOGOUT:
//                    instance.logout();
//                    // 退出情况下，直接返回，并且不可继续
//                    return;
//
//                case PushModel.ENTITY_TYPE_MESSAGE: {
//                    // 普通消息
//                    MessageCard card = getGson().fromJson(entity.content, MessageCard.class);
//                    getMessageCenter().dispatch(card);
//                    break;
//                }
//
//                case PushModel.ENTITY_TYPE_ADD_FRIEND: {
//                    // 好友添加
//                    UserCard card = getGson().fromJson(entity.content, UserCard.class);
//                    getUserCenter().dispatch(card);
//                    break;
//                }
//
//                case PushModel.ENTITY_TYPE_ADD_GROUP: {
//                    // 添加群
//                    GroupCard card = getGson().fromJson(entity.content, GroupCard.class);
//                    getGroupCenter().dispatch(card);
//                    break;
//                }
//
//                case PushModel.ENTITY_TYPE_ADD_GROUP_MEMBERS:
//                    break;
//                case PushModel.ENTITY_TYPE_MODIFY_GROUP_MEMBERS: {
//                    // 群成员变更, 回来的是一个群成员的列表
//                    Type type = new TypeToken<List<GroupMemberCard>>() {
//                    }.getType();
//                    List<GroupMemberCard> card = getGson().fromJson(entity.content, type);
//                    // 把数据集合丢到数据中心处理
//                    getGroupCenter().dispatch(card.toArray(new GroupMemberCard[0]));
//                    break;
//                }
//                case PushModel.ENTITY_TYPE_EXIT_GROUP_MEMBERS: {
//                    // TODO 成员退出的推送
//                }
//
//            }
//        }
//    }

//    /**
//     * 获取用户中心的实现类
//     * @return
//     */
//    public static UserCenter getUserCenter(){
//        return UserDispatcher.instance();
//    }
//
//    public static MessageCenter getMessageCenter(){
//        return MessageDispatcher.instance();
//    }
//
//    /**
//     * 获取一个群处理中心的实现类
//     *
//     * @return 群中心的规范接口
//     */
//    public static GroupCenter getGroupCenter() {
//        return GroupDispatcher.instance();
//    }

}
