package com.dispatch.system.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(bindLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    protected void showLoading() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).showLoading();
        }
    }

    protected void hideLoading() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).hideLoading();
        }
    }

    protected abstract int bindLayout();

    protected void initView() {}
}
