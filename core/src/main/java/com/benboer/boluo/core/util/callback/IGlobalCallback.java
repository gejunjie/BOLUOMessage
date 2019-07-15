package com.benboer.boluo.core.util.callback;

import androidx.annotation.NonNull;

public interface IGlobalCallback<T> {

    void executeCallback(@NonNull T args);

}
