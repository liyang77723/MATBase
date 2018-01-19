package cn.meiauto.matbase;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    protected AppCompatActivity mActivity;
    protected ViewGroup mRootView;

    protected abstract int getLayoutResId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutResId());

        afterSetContentView(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void afterSetContentView(Bundle savedInstanceState) {
        mActivity = this;
        mRootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        init(savedInstanceState);
    }

    @SuppressWarnings("unused")
    public void toast(Object obj) {
        String toastText = obj.toString();
        if (TextUtils.isEmpty(toastText)) {
            return;
        }
        Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG);
        TextView tv = toast.getView().findViewById(android.R.id.message);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        toast.show();
    }

    @SuppressWarnings("unused")
    protected View getContentView() {
        return findViewById(android.R.id.content);
    }

    @SuppressWarnings("unused")
    protected void hideActionbarTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    protected abstract void init(Bundle savedInstanceState);
}
