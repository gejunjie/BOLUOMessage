package com.benboer.boluo.message.fragment.main.bottom;

import android.widget.Toast;

import com.benboer.boluo.factory.R;
import com.benboer.boluo.module_common.app.BoLuo;
import com.benboer.boluo.module_common.mvp.PresenterFragment;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/1.
 */
public abstract class BottomItemFragment extends PresenterFragment {

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(mActivity, "双击退出" + BoLuo.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
