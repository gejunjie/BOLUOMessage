package com.benboer.boluo.module_common.util.callback;

import androidx.annotation.NonNull;

public interface IGlobalCallback<T> {

    void executeCallback(@NonNull T args);

}
