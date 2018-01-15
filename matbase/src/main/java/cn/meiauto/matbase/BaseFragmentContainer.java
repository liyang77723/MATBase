package cn.meiauto.matbase;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFragmentContainer extends BaseFragment {

    private static final String SAVE_FRAGMENT_CONTAINER_POS = "fragment_container_pos";

    protected List<BaseFragment> mFrags;
    private FragmentManager mFragmentManager;
    private int mCurrentPos;//此时界面正在显示的fragment位置

    @Override
    protected void onSaveState(Bundle outState) {
        super.onSaveState(outState);
        outState.putInt(SAVE_FRAGMENT_CONTAINER_POS, mCurrentPos);
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        if (savedInstanceState != null) {
            showFragment(mCurrentPos = savedInstanceState.getInt(SAVE_FRAGMENT_CONTAINER_POS));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFrags = new ArrayList<>();
        mFragmentManager = getChildFragmentManager();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract int getFrameLayoutId();

    @SuppressWarnings("unused")
    protected void addFragment(BaseFragment fragment) {
        mFrags.add(fragment);
        mFragmentManager.beginTransaction().add(getFrameLayoutId(), fragment, fragment.getClass().getSimpleName()).commitAllowingStateLoss();
    }

    protected void showFragment(int pos) {
        if (mFrags == null) {
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
