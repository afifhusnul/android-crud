package test1.android.com.test1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
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


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.loginUser)
    EditText loginUser;
    @BindView(R.id.loginPass)
    EditText loginPass;
    @BindView(R.id.BtnLogin)
    Button btnLogin;
    ProgressDialog loading;
    @BindView(R.id.showPassword)
    CheckBox showPassword;
    @BindView(R.id.newUser)
    TextView newUser;

    //    Call<AccessToken> call;
//    Context mContext;
    ApiService mApiService;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        ButterKnife.bind(this);
        mApiService = ApiClient.getClient().create(ApiService.class); // Initialize Api client helper
        sharedPrefManager = new SharedPrefManager(this);  //Initialize SharedPrefManager

        loginPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    loginPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    loginPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(LoginActivity.this, null, "Harap Tunggu...", true, false);
                requestLogin();
            }
        });

        newUser.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });


    }

    private void requestLogin() {
        mApiService.loginRequest(loginUser.getText().toString(), loginPass.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                String tokenType = jsonRESULTS.getString("token_type");
                                String accessToken = jsonRESULTS.getString("access_token");
                                if (tokenType.equals("Bearer")) {
                                    // Login Success then go to Main Page
                                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    // Session Login
                                    sharedPrefManager.saveToken(SharedPrefManager.SP_TOKEN, accessToken);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    loading.dismiss();
                                } else {
                                    // If Login failed
                                    String error_message = jsonRESULTS.getString("message");
                                    Toast.makeText(LoginActivity.this, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }
}