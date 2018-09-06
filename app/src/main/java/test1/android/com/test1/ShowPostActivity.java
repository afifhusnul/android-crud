package test1.android.com.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test1.android.com.test1.Adapters.PostAdapter;
import test1.android.com.test1.Models.GetPost;
import test1.android.com.test1.Models.Post;
import test1.android.com.test1.Rest.ApiClient;
import test1.android.com.test1.Rest.ApiService;
import test1.android.com.test1.Utils.SharedPrefManager;

public class ShowPostActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    ApiService mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static ShowPostActivity showPost;

//    @BindView(R.id.btnAddPost)
//    Button addPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiService.class);
        sharedPrefManager = new SharedPrefManager(this);  //Initialize SharedPrefManager
        ButterKnife.bind(this);

        showPost = this;
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_add_edit, menu);
//        MenuItem itemSave = menu.findItem(R.id.updPost);
        MenuItem itemDel = menu.findItem(R.id.delPost);
        MenuItem itemSave = menu.findItem(R.id.updPost);
        itemDel.setVisible(false); //Hide Delete menu
        itemSave.setVisible(false); //Hide Save menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addPost:
                startActivity(new Intent(ShowPostActivity.this, AddPostActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        refresh();
    }

    private void refresh() {
        String mToken = "Bearer " + sharedPrefManager.getSPToken();
        String mID = sharedPrefManager.getSPID();
        Call<GetPost> masterCall = mApiInterface.getPostList(mToken, mID);
        masterCall.enqueue(new Callback<GetPost>() {
            @Override
            public void onResponse(Call<GetPost> call, Response<GetPost>
                    response) {
                List<Post> PostList = response.body().getData();
                Log.d("Retrofit Get", "Master Count: " +
                        String.valueOf(PostList.size()));
                mAdapter = new PostAdapter(PostList, ShowPostActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetPost> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }

    public void goDelete(String id) {
        String mToken = "Bearer " + sharedPrefManager.getSPToken();
        mApiInterface.deletePost(mToken, id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()) {
                            Log.i("Debug", "onResponse: Delete post Success");
//                            finish();
                            refresh();
                        } else {
                            Log.i("debug", "onResponse: Delete post failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(ShowPostActivity.this, "Please check nternet connection", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
