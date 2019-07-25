package com.benboer.boluo.common.util.callback;

import androidx.annotation.NonNull;

public interface IGlobalCallback<T> {

    void executeCallback(@NonNull T args);

}
