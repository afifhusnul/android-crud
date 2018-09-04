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
    //    @Override
//    public Request authenticate(Route route, Response response) throws IOException {
//        try {
//            if (responseCount(response) > 3) {
//                return null;
//            }
//            /*silahkan diterapkan request api refresh token secara synchronous pada tahap ini*/
//
//
//        } catch (Exception e) {
//        }
//
//        return  response.request()
//                .newBuilder()
//                .removeHeader("Authorization")
//                .addHeader("Authorization", "Bearer " + newToken)
//                .build();
//    }
//    private int responseCount(Response response) {
//        int result = 1;
//        while ((response = response.priorResponse()) != null) {
//            result++;
//        }
//        return result;
//    }
}
