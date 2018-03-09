package cn.meiauto.matbase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {
    private static final String SAVE_HIDE_STATE = "save_fragment_hide_state";
    protected BaseActivity mActivity;
    protected boolean mPrepared;
    protected View mRootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        init(mRootView, savedInstanceState);
        setActionBar(mRootView);

        if (!restoreStateFromArguments()) {
            onFirstTimeLaunched();
        }

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPrepared = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPrepared = false;
    }

    protected abstract int getLayoutResId();

    //如果有标题栏的话，设置标题栏
    protected void setActionBar(View rootView) {
        Toolbar toolbar = rootView.findViewById(android.R.id.title);
        if (toolbar != null) {
            mActivity.setSupportActionBar(toolbar);
        }
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    protected abstract void init(View rootView, Bundle savedInstanceState);

    @SuppressWarnings("unused")
    public void toast(Object obj) {
        String toastText = obj.toString();
        if (TextUtils.isEmpty(toastText)) {
            return;
        }
        Toast toast = Toast.makeText(mActivity.getApplicationContext(), toastText, Toast.LENGTH_LONG);
        TextView tv = toast.getView().findViewById(android.R.id.message);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        toast.show();
    }

    public void onShow() {
    }

    /**
     * 保存fragment状态
     */
    Bundle savedState;

    public BaseFragment() {
        super();
        if (getArguments() == null) {
            setArguments(new Bundle());
        }
    }

    protected void onFirstTimeLaunched() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save VehicleState Here
        saveStateToArguments();
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        // Save VehicleState Here
//        saveStateToArguments();
//    }

    private void saveStateToArguments() {
        if (getView() != null) {
            savedState = saveState();
        }
        if (savedState != null) {
            Bundle b = getArguments();
            b.putBundle("internalSavedViewState8954201239547", savedState);
        }
    }

    private boolean restoreStateFromArguments() {
        Bundle b = getArguments();
        savedState = b.getBundle("internalSavedViewState8954201239547");
        if (savedState != null) {
            restoreState();
            return true;
        }
        return false;
    }

    private void restoreState() {
        if (savedState != null) {
            onRestoreState(savedState);
        }
    }

    protected void onRestoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            boolean isHidden = savedInstanceState.getBoolean(SAVE_HIDE_STATE);
            if (isHidden) {
                getFragmentManager().beginTransaction().hide(this).commit();
            } else {
                getFragmentManager().beginTransaction().show(this).commit();
            }
        }
    }

    private Bundle saveState() {
        Bundle state = new Bundle();
        onSaveState(state);
        return state;
    }

    protected void onSaveState(Bundle outState) {
        outState.putBoolean(SAVE_HIDE_STATE, isHidden());
    }
}
