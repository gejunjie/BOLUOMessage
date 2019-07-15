package com.benboer.boluo.core.net.rx;

import android.content.Context;

import com.benboer.boluo.core.net.HttpMethod;
import com.benboer.boluo.core.net.RestCreator;
import com.benboer.boluo.core.ui.loader.FragmentLoader;
import com.benboer.boluo.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by BenBoerBoluojiushiwo on 2019/7/15.
 */
public class RxRestClient {

    //创建一个网络请求需要的参数
    private final String URL;
    private final WeakHashMap<String, Object> PARAMS;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private Context CONTEXT;

    public RxRestClient(String url,
                        WeakHashMap<String, Object> params,
                        RequestBody body,
                        File file,
                        Context context,
                        LoaderStyle loaderStyle) {
        this.URL = url;
        this.PARAMS = params;
        this.BODY = body;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = file;
        this.CONTEXT = context;
    }

    public static ClientBuilder builder() {
        return new ClientBuilder();
    }

    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;

        if (LOADER_STYLE != null) {
            FragmentLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = RestCreator.getRxRestService().upload(URL, body);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            default:
                break;
        }

        return observable;
    }

    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public final Observable<String> post() {
        if (BODY == null) {
            return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
        }
        return request(HttpMethod.POST_RAW);
    }

    public final Observable<String> put() {
        if (BODY == null) {
            return request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
        }
        return request(HttpMethod.PUT_RAW);
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> upload() {
        return request((HttpMethod.UPLOAD));
    }

    public final Observable<ResponseBody> download() {
        return RestCreator.getRxRestService().download(URL,PARAMS);
    }

    static class ClientBuilder {

        private final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
        private String mUrl = null;
        private RequestBody mBody = null;
        private Context mContext = null;
        private LoaderStyle mLoaderStyle = null;
        private File mFile = null;

        ClientBuilder() {
            PARAMS.clear();
        }

        public final ClientBuilder url(String url) {
            this.mUrl= url;
            return this;
        }

        public final ClientBuilder params(WeakHashMap<String, Object> params) {
            PARAMS.putAll(params);
            return this;
        }

        public final ClientBuilder params(String key, Object value) {
            PARAMS.put(key, value);
            return this;
        }

        public final ClientBuilder file(File file) {
            this.mFile = file;
            return this;
        }

        public final ClientBuilder file(String file) {
            this.mFile = new File(file);
            return this;
        }

        public final ClientBuilder raw(String raw) {
            this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
            return this;
        }

        public final ClientBuilder loader(Context context, LoaderStyle loaderStyle) {
            this.mContext = context;
            this.mLoaderStyle = loaderStyle;
            return this;
        }

        //默认使用的方法
        public final ClientBuilder loader(Context context) {
            this.mContext = context;
            this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
            return this;
        }

        public final RxRestClient build() {
            return new RxRestClient(mUrl, PARAMS, mBody, mFile, mContext, mLoaderStyle);
        }

    }

}
