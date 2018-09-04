package test1.android.com.test1.Rest;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {

    private String authToken;
    AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
//        return null;

        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .header("Authorization", authToken);
        Request request = builder.build();
        return chain.proceed(request);
    }
}
