package test1.android.com.test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test1.android.com.test1.Models.Logout;
import test1.android.com.test1.Models.User;
import test1.android.com.test1.Rest.ApiClient;
import test1.android.com.test1.Rest.ApiService;
import test1.android.com.test1.Utils.SharedPrefManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SharedPrefManager sharedPrefManager;

    @BindView(R.id.helloWorld)
    TextView helloWorld;

    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        mApiService = ApiClient.getClient().create(ApiService.class); // Initialize Api client helper
        sharedPrefManager = new SharedPrefManager(this);  //Initialize SharedPrefManager

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        if (sharedPrefManager.getSPToken() != null) {
            showUserDetail();
        } else {
            Intent i = new Intent(MainActivity.this, LoginActivity.class); // Run Login page
            startActivity(i);
            finish();
        }
    }

    private void logout() {
        String mToken = "Bearer " + sharedPrefManager.getSPToken();
        mApiService.logout(mToken).enqueue(new Callback<Logout>() {
            @Override
            public void onResponse(Call<Logout> call, Response<Logout> response) {
                if (response.isSuccessful()) {
                    sharedPrefManager.clearToken();
                    sharedPrefManager.clearSession();
//                    String errorMsg = response.body().getMessage();
//                    Snackbar.make(, "Replace with your own action", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Logout> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        });


    }

    private void showUserDetail() {
        String mToken = "Bearer " + sharedPrefManager.getSPToken();
        mApiService.userProfile(mToken).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
//                    Log.e("MainActivity_TAG", "response 33: "+response.body());
                    String userID = response.body().getId();
                    String userName = response.body().getUserName();
//                    String userEmail = response.body().getUserEmail();
                    helloWorld.setText(userName+" - ("+userID+")");
                    sharedPrefManager.saveID(SharedPrefManager.SP_ID, userID);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

//        mApiService.userDetail(sharedPrefManager.getSPToken());
//        System.out.println("Pasukan : " + mApiService.userDetail(sharedPrefManager.getSPToken()));
//        .enqueue(new Callback<ResponseBody>() {
//
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                try {
//                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                    String tokenType = jsonRESULTS.getString("token_type");
//                    String accessToken = jsonRESULTS.getString("access_token");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Log.e("debug", "onFailure: ERROR > " + t.toString());
//            }
//        }
//
//    });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        logout();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivity(new Intent(MainActivity.this, GoActivity.class));

        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this, ShowPostActivity.class));
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
