package test1.android.com.test1;

import android.content.Intent;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test1.android.com.test1.Rest.ApiClient;
import test1.android.com.test1.Rest.ApiService;
import test1.android.com.test1.Utils.SharedPrefManager;

public class AddPostActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    ApiService mApiService;
    private View focusView;
    public String post_id;

//    @BindView(R.id.btnSubmitPost)
//    Button btnSubmitPost;
    @BindView(R.id.editTitle)
    EditText editTitle;
    @BindView(R.id.editContent)
    EditText editContent;
    @BindView(R.id.postID) EditText postID;
//    @BindView(R.id.updPost) MenuItem savePost;
//    @BindView(R.id.delPost) MenuItem delPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        ButterKnife.bind(this);
        mApiService = ApiClient.getClient().create(ApiService.class);
        sharedPrefManager = new SharedPrefManager(this);  //Initialize SharedPrefManager

        Bundle extras = getIntent().getExtras(); //Get value from Prev intent

        if (extras != null) { // check has Extras value
//            final String post_id = extras.getString("id");
            post_id = extras.getString("id");
            String postTitle = extras.getString("post_title");
            String postContent = extras.getString("post_content");
            if (post_id != null && post_id.trim().length() > 0) {
                editTitle.setFocusable(false);
                postID.setText(post_id);
                editTitle.setText(postTitle);
                editContent.setText(postContent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_add_edit, menu);
//        MenuItem itemSave = menu.findItem(R.id.updPost);
        MenuItem itemDel = menu.findItem(R.id.delPost);
        MenuItem itemAdd = menu.findItem(R.id.addPost);
        itemDel.setVisible(false); //Hide Delete menu
        itemAdd.setVisible(false); //Hide Save menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updPost:
                if (post_id != null && post_id.trim().length() > 0) {
                    goUpdate();
                } else {
                    goSubmmit();
                }


        }
        return super.onOptionsItemSelected(item);
    }

    private void goUpdate() {
        if (!performValidation()) {
            return;
        } else {
            String mToken = "Bearer " + sharedPrefManager.getSPToken();
            mApiService.updatePost(mToken, postID.getText().toString(), editTitle.getText().toString(), editContent.getText().toString())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if (response.isSuccessful()) {
                                Log.i("Debug", "onResponse: Update post Success");
                                finish();
                            } else {
                                Log.i("debug", "onResponse: Update post failed");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                            Toast.makeText(AddPostActivity.this, "Please check nternet connection", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void goSubmmit() {
        if (!performValidation()) {
            return;
        } else {
            String mToken = "Bearer " + sharedPrefManager.getSPToken();
            mApiService.postAdd(mToken, editTitle.getText().toString(), editContent.getText().toString())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if (response.isSuccessful()) {
                                Log.i("Debug", "onResponse: Add post Success");
                                finish();
                            } else {
                                Log.i("debug", "onResponse: Add post failed");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                            Toast.makeText(AddPostActivity.this, "Please check nternet connection", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private boolean performValidation() {
        boolean isSuccessful = true;
        String titleP = editTitle.getText().toString();
        String contentP = editContent.getText().toString();

        editTitle.setError(null);
        editContent.setError(null);

        if (TextUtils.isEmpty(titleP)) {
            focusView = editTitle;
            isSuccessful = false;
        }

        if (TextUtils.isEmpty(contentP)) {
            focusView = editTitle;
            isSuccessful = false;
        }
        if (!isSuccessful) {
            focusView.requestFocus();
        }
        return isSuccessful;
    }
}
