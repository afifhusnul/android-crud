package test1.android.com.test1.Rest;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Route;
import retrofit2.Response;

public class CustomAuthenticator implements Authenticator {

    @Nullable
    @Override
    public Request authenticate(Route route, okhttp3.Response response) throws IOException {
        return null;
    }
}
