package com.benboer.boluo.boluomessage.temp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benboer.boluo.core.fragment.SupportFragment;
import com.benboer.boluo.core.net.RestClient;
import com.benboer.boluo.core.net.callback.IError;
import com.benboer.boluo.core.net.callback.IFailure;
import com.benboer.boluo.core.net.callback.ISuccess;

/**
 * Created by BenBoerBoluojiushiwo on 2019/6/27.
 */
public class MainFragment extends SupportFragment {
    @Override
    public Object setLayout() {
        return null;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        RestClient.builder()
                .url("http://mock.fulingjie.com/mock/api/")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}
