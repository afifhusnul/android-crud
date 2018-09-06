package test1.android.com.test1.Rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import test1.android.com.test1.Models.AccessToken;
import test1.android.com.test1.Models.GetMasterStock;
import test1.android.com.test1.Models.GetPost;
import test1.android.com.test1.Models.GetUser;
import test1.android.com.test1.Models.Logout;
import test1.android.com.test1.Models.User;

public interface ApiService {

    //Test Only
    @GET("stockmaster")
    Call<GetMasterStock> getMaster();

    // Signup Function
    @FormUrlEncoded
    @POST("auth/signup")
    Call<ResponseBody> signupRequest(@Field("name") String name,
                                     @Field("email") String email,
                                     @Field("password") String password,
                                     @Field("password_confirmation") String password_confirmation);

    // Login Function
    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseBody> loginRequest(@Field("email") String email, @Field("password") String password);


    //Logout Funtion
    @GET("auth/logout")
    Call<Logout> logout(@Header("Authorization") String token);

    // User detail Function
    @GET("user/profile")
    Call<User> userProfile(@Header("Authorization") String Token);

    //Show post
    @GET("post")
    Call<GetPost> getPost(@Header("Authorization") String Token);

    //Show own post
    @GET("postlist/{userId}")
    Call<GetPost> getPostList(@Header("Authorization") String Token, @Path("userId") String userId);

    //Add post
    @POST("addpost/add")
    @FormUrlEncoded
    Call<ResponseBody> postAdd(@Header("Authorization") String Token,
                              @Field("post_title") String postTitle,
                              @Field("post_content") String postContent);

    //Update post
    @PUT("updatepost/{id}")
    @FormUrlEncoded
    Call<ResponseBody> updatePost(@Header("Authorization") String Token,
                          @Path("id") String id, @Field("post_title") String postTitle,
                          @Field("post_content") String postContent);

    //Delete Post
    @DELETE("deletepost/{id}")
//    @FormUrlEncoded
    Call<ResponseBody> deletePost(@Header("Authorization") String Token, @Path("id") String id);

}
