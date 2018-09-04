package test1.android.com.test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test1.android.com.test1.Adapters.MasterAdapter;
import test1.android.com.test1.Models.GetMasterStock;
import test1.android.com.test1.Models.MasterStock;
import test1.android.com.test1.Rest.ApiClient;
import test1.android.com.test1.Rest.ApiService;

public class GoActivity extends AppCompatActivity {

    ApiService mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static GoActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiService.class);
        ma=this;
        refresh();
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        refresh();
    }

    private void refresh() {
        Call<GetMasterStock> masterCall = mApiInterface.getMaster();
        masterCall.enqueue(new Callback<GetMasterStock>() {
            @Override
            public void onResponse(Call<GetMasterStock> call, Response<GetMasterStock>
                    response) {
//                List<MasterStock> MasterList = response.body()getListDataMaster();
                List<MasterStock> MasterList = response.body().getData();
                Log.d("Retrofit Get", "Master Count: " +
                        String.valueOf(MasterList.size()));
                mAdapter = new MasterAdapter(MasterList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetMasterStock> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }
}
