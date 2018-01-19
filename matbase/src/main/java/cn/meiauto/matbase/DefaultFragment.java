package cn.meiauto.matbase;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DefaultFragment extends BaseFragment {

    private static final String ARG_TEXT = "text";

    public static DefaultFragment newInstance(String text) {
        DefaultFragment fragment = new DefaultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TEXT, text);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    protected void init(View rootView, Bundle savedInstanceState) {
        TextView textView = rootView.findViewById(android.R.id.text1);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setText(getArguments().getString(ARG_TEXT, "FRAGMENT"));
    }
}