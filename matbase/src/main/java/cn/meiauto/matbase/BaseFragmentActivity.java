package cn.meiauto.matbase;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFragmentActivity extends BaseActivity {

    private static final String SAVE_FRAGMENT_POS = "fragment_pos";

    protected List<BaseFragment> mFrags;
    private int mCurrentPos;//此时界面正在显示的fragment位置
    private FragmentManager mFragmentManager;

    protected abstract int getFrameLayoutId();


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.remove("android:support:fragments");
        outState.putInt(SAVE_FRAGMENT_POS, mCurrentPos);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void afterSetContentView(Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.popBackStack();
        mFrags = new ArrayList<>();
        super.afterSetContentView(savedInstanceState);
        if (savedInstanceState != null) {//恢复正确的fragment
            showFragment(mCurrentPos = savedInstanceState.getInt(SAVE_FRAGMENT_POS));
        }
    }

    @SuppressWarnings("unused")
    protected void addFragment(BaseFragment fragment) {
        mFrags.add(fragment);
        mFragmentManager.beginTransaction().add(getFrameLayoutId(), fragment, fragment.getClass().getSimpleName()).commit();
    }

    protected void showFragment(int pos) {
        if (null == mFrags) {
            return;
        }

        int size = mFrags.size();
        if (pos < size) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            for (int i = 0; i < size; i++) {
                transaction.hide(mFrags.get(i));
            }
            transaction.show(mFrags.get(pos));
            transaction.commitAllowingStateLoss();
            mCurrentPos = pos;
            mFrags.get(pos).onShow();
        } else {
            throw new RuntimeException("show fragment index > exist fragments(" + mFrags.size() + ")");
        }
    }
}
